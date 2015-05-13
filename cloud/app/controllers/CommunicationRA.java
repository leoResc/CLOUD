package controllers;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

public class CommunicationRA extends Controller {
	
	public static Result changeMode(int mode) {
		Logger.info("mode " + mode + " requested");
		return ok();
	}

}
