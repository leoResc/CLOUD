package controllers;

import java.util.Date;
import java.util.List;

import models.*;
import views.html.*;
import play.Logger;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;

public class Application extends Controller {

	public static Result getIndex() {
		return ok(index.render());
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
			for (int i = 0; i < allUser.size(); i++) {
				if (allUser.get(i).username.equals("admin")
						&& allUser.get(i).password.equals(password)) {
					return redirect(routes.Application.getIndex());
				}
			}
			return ok(login.render("Wrong username or password."));
		} else {
			for (int i = 0; i < allUser.size(); i++) {
				if (allUser.get(i).username.equals(user.username)) {
					return ok(login.render("User exists already."));
				}
			}

			Date currentTime = new Date();
			List<Event> events = new Model.Finder(String.class, Event.class)
					.all();
			for (int i = 0; i < events.size(); i++) {
				if ((currentTime.after(events.get(i).begin))
						&& (currentTime.before(events.get(i).end))) {
					if (events.get(i).password.equals(password)) {
						user.save();
						return redirect(routes.Application.getIndex());
					}
				}
			}
			return ok(login.render("Wrong password for current event."));
		}
	}

	// Returns view createEvent
	public static Result getCreateEvent() {
		return ok(createEvent.render());
	}

	// Handles post, creates new event
	public static Result createEvent() {
		Event event = Form.form(Event.class).bindFromRequest().get();
		Logger.info("event received:" + System.lineSeparator() + event.name
				+ System.lineSeparator() + event.description
				+ System.lineSeparator() + event.password);
		event.save();
		return redirect(routes.Application.getIndex());
	}

	// Returns all events
	public static Result getEvents() {
		List<Event> events = new Model.Finder(String.class, Event.class).all();
		List<User> user = new Model.Finder(String.class, User.class).all();
		return ok(test.render(events, user));
	}

	// Deletes a given event
	public static Result deleteEvent(long id) {
		Logger.info("request to delete event: " + id);
		return TODO;
	}
}
