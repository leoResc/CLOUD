package models;

import java.util.TimerTask;

import play.Logger;

public class UpdatePlaylist extends TimerTask {

	public static boolean loadedNext = false;

	public void run() {
		ShellCommand sh = new ShellCommand("mpc status");
		StringBuffer output = sh.executeShellCommand();
		Logger.info("read full status:" + output);
		int begin = output.indexOf("[p");
		int end = output.indexOf("%)");
		// substring from [playing] to %)
		if ((begin >= 0) && (end >= begin)) {
			String mpcStatus = output.substring(begin, end);
			begin = mpcStatus.indexOf("(");
			end = mpcStatus.indexOf("%)");
			try {
				int percentage = Integer.valueOf(mpcStatus.substring(begin + 1,
						end));
				Logger.info("read percentage of song: " + percentage);
				if (percentage >= 1 & percentage < 80) {
					loadedNext = false;
					Logger.info("loaded next: " + loadedNext);
				} else if ((percentage >= 80) && (!loadedNext)) {
					CurrentPlaylist.addNextSongToPlaylist();
					loadedNext = true;
					Logger.info("loaded next song");
				}
			} catch (NumberFormatException | StringIndexOutOfBoundsException e) {
				Logger.error("error while parsing mpc status");
			}
		}
	}
}
