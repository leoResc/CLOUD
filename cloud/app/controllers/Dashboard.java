package controllers;

import play.mvc.Result;
import play.mvc.Controller;
import views.html.*;

public class Dashboard extends Controller {

	public static Result getDashboard() {
		return ok(dashboard.render());		
	}
	
	public static Result getEvent() {
		return ok(event.render());
	}
	
	public static Result getPlaylist() {
		return ok(playlist.render());
	}
	
	public static Result getSong() {
		return ok(song.render());
	}
	
	public static Result getUser() {
		return ok(user.render());
	}
}
