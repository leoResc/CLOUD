package controllers;

import java.util.Date;
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
			if (password.equals("ozeanien")) {
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

			Date currentTime = new Date();
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
				return ok(createEvent.render());
			} else {
				return unauthorized(views.html.forbidden.render());
			}
		}
	}

	// Handles post, creates new event
	public static Result createEvent() {
		Event event = Form.form(Event.class).bindFromRequest().get();
		event.save();
		return redirect(routes.Application.getIndex());
	}

	// Returns all events
	public static Result overview() {
		List<Event> events = new Model.Finder(String.class, Event.class).all();
		List<User> user = new Model.Finder(String.class, User.class).all();
		List<Song> songs = new Model.Finder(String.class, Song.class).all();
		return ok(overview.render(events, user, songs));
	}
}
