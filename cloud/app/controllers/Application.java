package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result login() {
        return ok(login.render());
    }

    public static Result createEvent() {
        return ok(createEvent.render());
    }
    
    public static Result index() {
    	return ok(index.render());
    }

}
