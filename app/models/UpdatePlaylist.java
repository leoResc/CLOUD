package models;

import java.util.TimerTask;

import play.Logger;

public class UpdatePlaylist extends TimerTask {

	public static boolean loadedNext = false;

	public void run() {
		ShellCommand sh = new ShellCommand("mpc status");
		StringBuffer output = sh.executeShellCommand();
		int begin = output.indexOf("(");
		int end = output.indexOf("%)");
		Logger.info("Matched:'" + output.substring(begin + 1, end) + "'");
		try {
			int percentage = Integer
					.valueOf(output.substring(begin + 1, end));
			if (percentage >= 1 & percentage <= 50) {
				loadedNext = false;
			}
			if ((percentage >= 85) && (!loadedNext)) {
				CurrentPlaylist.addNextSongToPlaylist();
				loadedNext = true;
			}
		} catch (NumberFormatException e) {
			Logger.info("error while parsing mpc status");
		}
	}
}
