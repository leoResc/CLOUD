package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result login() {
        return ok(index.render());
    }

    public static Result adminCreateEvent() {
        return ok(createEvent.render());
    }

}
