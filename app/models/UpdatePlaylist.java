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
		try {
			Logger.info("Matched: " + output.substring(begin + 1, end));
			int percentage = Integer
					.valueOf(output.substring(begin - 2, begin));
			if (percentage >= 1 & percentage <= 50) {
				loadedNext = false;
			}
			if ((percentage >= 85) && (!loadedNext)) {
				CurrentPlaylist.addNextSongToPlaylist();
				loadedNext = true;
			}
		} catch (Exception e) {
			Logger.info("error while parsing mpc status");
		}
	}
}
