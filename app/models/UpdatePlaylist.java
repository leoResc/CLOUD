package models;

import java.util.TimerTask;

import play.Logger;

public class UpdatePlaylist extends TimerTask {

	public static boolean loadedNext = false;

	public void run() {
		ShellCommand sh = new ShellCommand("mpc status");
		StringBuffer output = sh.executeShellCommand();
		int index = output.indexOf("%)");
		try {
			Logger.info("Matched: " + output.substring(index - 2, index));
			int percentage = Integer
					.valueOf(output.substring(index - 2, index));
			if (percentage >= 1 & percentage <= 50) {
				loadedNext = false;
			}
			if ((percentage >= 85) && (!loadedNext)) {
				CurrentPlaylist.addNextSongToPlaylist();
				loadedNext = true;
				Logger.info("loaded next");
			}
		} catch (Exception e) {
			Logger.info("error while parsing mpc status");
		}
	}
}
