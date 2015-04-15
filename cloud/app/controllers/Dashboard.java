package controllers;

import play.mvc.Result;
import play.mvc.Controller;
import views.html.*;

public class Dashboard extends Controller {

	public static Result getDashboard() {
		return ok(dashboard.render());		
	}
}
