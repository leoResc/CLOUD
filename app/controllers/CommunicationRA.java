package controllers;

import java.util.List;

import models.CurrentPlaylist;
import models.Likes;
import models.ShellCommand;
import play.mvc.Controller;
import play.mvc.Result;

public class CommunicationRA extends Controller {

	public static Result changeMode(int mode) {

		if ((mode >= 0) & (mode <= 5)) {
			// Shutdown
			if (mode == 0) {
				// delete current playlist
				List<CurrentPlaylist> currentPlaylist = CurrentPlaylist.find
						.all();
				for (CurrentPlaylist song : currentPlaylist) {
					song.delete();
				}
				// delete all votes
				List<Likes> likes = Likes.find.all();
				for (Likes like : likes) {
					like.delete();
				}
				ShellCommand command = new ShellCommand("sudo shutdown -h now");
				command.executeShellCommand();
			} else {
				for (int i = 0; i < 3; i++) {
					ShellCommand command = new ShellCommand(
							"python communicationArduino.py " + mode);
					command.executeShellCommand();
				}
				// Thunderstorm
				if (mode == 5) {
					ShellCommand s1 = new ShellCommand("mpc clear");
					s1.executeShellCommand();
					ShellCommand s2 = new ShellCommand(
							"mpc add Thunderstorm.mp3");
					s2.executeShellCommand();
					ShellCommand s3 = new ShellCommand("mpc play");
					s3.executeShellCommand();
				}
			}
			return ok();
		}

		return badRequest();
	}
}