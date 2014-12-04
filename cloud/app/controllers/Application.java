package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.*;
import views.html.*;
import play.Logger;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;

public class Application extends Controller {
	
	public static Result vote(long songId) {
		
		Song s1;
		
		
		return redirect(routes.Application.getIndex());
	}

	public static Result getIndex() {
		
		Song s1 = new Song("Hit it","Chris Brown","Pop",3.5,2);
		Song s2 = new Song("Dying","Teddy West","Techno",3.2,10);
		Song s3 = new Song("Bangarang","Skrillex","Dubstep",4.5,7);
		
		ArrayList<Song> songs = new ArrayList();
		songs.add(s1);
		songs.add(s2);
		songs.add(s3);
		
		s1.save();
		s2.save();
		s3.save();
		
		return ok(index.render(songs));
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
			if (password.equals("cloud")) {
				return redirect(routes.Application.getIndex());
			} else
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
		event.save();
		return redirect(routes.Application.getIndex());
	}

	// Returns all events
	public static Result overview() {
		List<Event> events = new Model.Finder(String.class, Event.class).all();
		List<User> user = new Model.Finder(String.class, User.class).all();
		return ok(overview.render(events, user));
	}

	// Deletes a given event
	public static Result deleteEvent(long id) {
		return TODO;
	}
}
