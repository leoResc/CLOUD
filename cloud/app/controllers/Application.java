package controllers;

import models.Event;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result login() {
        return ok(login.render());
    }

    public static Result createEvent() {
    	Event event = new Event();
        return ok(createEvent.render(event));
    }
    
    public static Result index() {
    	return ok(index.render());
    }

}
