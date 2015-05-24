package controllers;

import models.ShellCommand;
import play.mvc.Controller;
import play.mvc.Result;

public class CommunicationRA extends Controller {

	public static Result changeMode(int mode) {

		if ((mode >= 0) & (mode <= 9)) {
			ShellCommand command = new ShellCommand("python communicationArduino.py " + mode);
			command.executeShellCommand();
			
			if(mode == 0) {
				command = new ShellCommand("sudo shutdown -h now");
				command.executeShellCommand();
			}

			return ok();
		}
		
		return badRequest();
	}
}