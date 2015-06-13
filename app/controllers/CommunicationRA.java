package controllers;

import models.ShellCommand;
import play.mvc.Controller;
import play.mvc.Result;

public class CommunicationRA extends Controller {

	public static Result changeMode(int mode) {

		if ((mode >= 0) & (mode <= 9)) {
			ShellCommand command = new ShellCommand(
					"python communicationArduino.py " + mode);
			command.executeShellCommand();

			if (mode == 0) {
				command = new ShellCommand("sudo shutdown -h now");
				command.executeShellCommand();
			} else if (mode == 5) {
				ShellCommand s1 = new ShellCommand("mpc clear");
				s1.executeShellCommand();
				ShellCommand s2 = new ShellCommand("mpc add Thunderstorm.mp3");
				s2.executeShellCommand();
				ShellCommand s3 = new ShellCommand("mpc play");
				s3.executeShellCommand();
			}
			return ok();
		}

		return badRequest();
	}
}