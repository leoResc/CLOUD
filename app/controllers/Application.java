package controllers;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import models.*;
import views.html.*;
import play.Logger;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;

public class Application extends Controller {

	public static Result getSongs() {
		List<Song> songs = Song.find.all();
		return ok(Json.toJson(songs));
	}

	public static boolean songAlreadyLiked(long userID, long songID) {
		Likes likes = new Likes();

		if (likes.findLike(songID, userID) == null) {
			return false;
		}
		return true;

	}

	public static Result vote(long userID, long songID) {

		Song song = Song.find.byId(songID);
		Logger.info(song.title);

		Likes likes = new Likes();
		boolean songLiked = songAlreadyLiked(userID, songID);

		if (!songLiked) {
			likes.createLike(songID, userID);
		} else {
			Logger.info("Dislike");
			likes.deleteLike(songID, userID);
		}

		Logger.info(String.valueOf(song.likes));

		List<Song> songs = new Model.Finder(String.class, Song.class).all();
		Collections.sort(songs);
		for (Song s : songs) {
			Logger.info(String.valueOf(s.title + " -> " + s.likes));
		}

		return ok(Json.toJson(songs));
	}

	public static Result logout() {
		String username = session("user");

		if (!(username.equals("admin")) && (session("id") != null)) {
			try {
				User.find.ref(session("id")).delete();
			} catch (Exception e) {
				Logger.error("not existing user is trying to log out");
			}
		}
		session().clear();
		return redirect(routes.Application.getLogin());
	}

	public static Result getIndex() {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		}

		List<Song> songs = new Model.Finder(String.class, Song.class).all();
		Collections.sort(songs);
		return ok(index.render(songs, session("id")));
	}

	public static Result getLogin() {
		return ok(login.render(""));
	}

	// validates sign ins
	public static Result login() {
		User user = Form.form(User.class).bindFromRequest().get();
		String password = request().body().asFormUrlEncoded().get("password")[0];
		List<User> allUser = new Model.Finder(String.class, User.class).all();

		if (user.username.equals("admin")) {
			if (HashHelper
					.checkPassword(password,
							"$2a$10$KrIA0hwvvVelMHRa411sXO7eQENK.JXUNfSOG9FpR1RZVCTfeEPxC")) {
				session("user", "admin");
				return redirect(routes.Application.getIndex());
			} else
				return ok(login.render("Wrong username or password."));
		} else {
			for (User getUser : allUser) {
				if (getUser.username.equals(user.username)) {
					return ok(login.render("User exists already."));
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
						session("user",
								HashHelper.createPassword(user.username));
						session("id", String.valueOf(user.id));
						return redirect(routes.Application.getIndex());
					}
				}
			}
			return ok(login.render("Wrong password for current event."));
		}
	}

	// Returns view createEvent
	public static Result getCreateEvent() {

		String session = session("user");

		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (session.equals("admin")) {
				List<Playlist> playlists = Playlist.find.all();
				List<Event> allEvents = Event.find.all();
				return ok(event.render(playlists, allEvents));
			} else {
				return unauthorized(views.html.forbidden.render("UNAUTHORIZED"));
			}
		}
	}



	// Returns all events
	public static Result overview() {
		List<Event> events = new Model.Finder(String.class, Event.class).all();
		List<User> user = new Model.Finder(String.class, User.class).all();
		List<Song> songs = new Model.Finder(String.class, Song.class).all();
		return ok(overview.render(events, user, songs));
	}

	public static Result NotFound(String uri) {
		return badRequest(views.html.forbidden.render("BAD REQUEST"));
	}
}
