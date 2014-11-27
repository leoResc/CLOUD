package controllers;

import models.Event;
import views.html.*;
import play.Logger;
import play.data.Form;
import play.mvc.*;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render());
	}

	public static Result login() {
		return ok(login.render());
	}

	public static Result createEvent() {
		return ok(createEvent.render());
	}

	public static Result addEvent() {
		Event event = Form.form(Event.class).bindFromRequest().get();
		Logger.info("event received:" + System.lineSeparator() + event.name + System.lineSeparator() + event.description + System.lineSeparator() + event.password);
		event.save();
		return ok(index.render());
	}
}
