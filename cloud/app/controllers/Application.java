package controllers;

import models.*;
import views.html.*;
import play.Logger;
import play.data.Form;
import play.mvc.*;

public class Application extends Controller {

	public static Result getIndex() {
		return ok(index.render());
	}

	public static Result getLogin() {
		return ok(login.render());
	}
	
	public static Result login() {
		User user = Form.form(User.class).bindFromRequest().get();
		Logger.info("User registered" + System.lineSeparator() + user.username);
		user.save();
		return redirect(routes.Application.getIndex());
	}

	public static Result getCreateEvent() {
		return ok(createEvent.render());
	}

	public static Result createEvent() {
		Event event = Form.form(Event.class).bindFromRequest().get();
		Logger.info("event received:" + System.lineSeparator() + event.name + System.lineSeparator() + event.description + System.lineSeparator() + event.password);
		event.save();
		return redirect(routes.Application.getIndex());
	}
}
