package controllers;

import java.util.Calendar;
import java.util.List;
import models.CurrentPlaylist;
import models.Event;
import models.HashHelper;
import models.Likes;
import models.Song;
import models.User;
import play.Logger;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {

	public static Result getLogin() {
		return ok(login.render());
	}

	public static Result getIndex() {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		}

		List<Song> songs = CurrentPlaylist.getCurrentPlaylist();
		Event event = Event.getCurrentEvent();
		String eventDescription = "";
		String eventTitle = "";

		if (event == null) {
			eventDescription = "No description available, because no event is currently activated ...";
		} else {
			eventDescription = event.description;
			eventTitle = event.name;
		}

		return ok(landing.render(songs, session("id"), session, eventTitle,
				eventDescription));
	}

	public static Result login() {
		User user = Form.form(User.class).bindFromRequest().get();
		String password = request().body().asFormUrlEncoded().get("password")[0];
		List<User> allUser = User.find.all();

		if (user.username.equals("admin")) {
			if (HashHelper
					.checkPassword(password,
							"$2a$10$KrIA0hwvvVelMHRa411sXO7eQENK.JXUNfSOG9FpR1RZVCTfeEPxC")) {
				session("user", "admin");
				return redirect(routes.Application.getIndex());
			} else
				flash("error", "Wrong username or password ...");
			return ok(login.render());
		} else {
			for (User getUser : allUser) {
				if (getUser.username.equals(user.username)) {
					flash("error", "The user " + getUser.username
							+ " exists already ...");
					return ok(login.render());
				}
			}

			Calendar currentTime = Calendar.getInstance();
			List<Event> events = new Model.Finder(String.class, Event.class)
					.all();
			for (Event getEvent : events) {
				if ((currentTime.after(getEvent.begin))
						&& (currentTime.before(getEvent.end))) {
					if (getEvent.password.equals(password)) {
						user.save();
						session("user", user.username);
						session("id", String.valueOf(user.id));
						return redirect(routes.Application.getIndex());
					}
				}
			}
			flash("error",
					"You selected the wrong password for current event ...");
			return ok(login.render());
		}
	}

	public static Result logout() {
		String username = session("user");

		if (username != null) {
			if (!(username.equals("admin")) && (session("id") != null)) {
				try {
					User.find.ref(session("id")).delete();
				} catch (Exception e) {
					Logger.error("not existing user is trying to log out");
				}
			}
		}
		session().clear();
		flash("info", "Thanks for visiting CLOUD. Have a nice day, bye ...");
		return redirect(routes.Application.getLogin());
	}

	public static Result vote(long userID, long songID) {

		Likes likes = new Likes();
		boolean songLiked = Song.songAlreadyLiked(userID, songID);

		if (!songLiked) {
			likes.createLike(songID, userID);
		} else {
			likes.deleteLike(songID, userID);
		}
		List<Song> songs = CurrentPlaylist.getCurrentPlaylist();
		return ok(Json.toJson(songs));
	}

	public static Result NotFound(String uri) {
		return badRequest(forbidden.render("BAD REQUEST"));
	}
}
