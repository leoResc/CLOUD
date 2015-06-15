package models;

import java.util.TimerTask;

import play.Logger;

public class UpdatePlaylist extends TimerTask {

	public static boolean loadedNext = false;

	public void run() {
		ShellCommand sh = new ShellCommand("mpc status");
		StringBuffer output = sh.executeShellCommand();
		int begin = output.indexOf("] #");
		int end = output.indexOf("%)");
		// substring from '] #' to '%)'
		String mpcStatus = output.substring(begin, end + 2);
		if ((begin >= 0) && (end >= begin)) {
			begin = mpcStatus.indexOf("(");
			end = mpcStatus.indexOf("%");
			try {
				int percentage = Integer.valueOf(mpcStatus.substring(begin + 1,
						end));
				if (percentage >= 1 & percentage < 80) {
					loadedNext = false;
				} else if ((percentage >= 80) && (!loadedNext)) {
					CurrentPlaylist.addNextSongToPlaylist();
					loadedNext = true;
				}
			} catch (NumberFormatException | StringIndexOutOfBoundsException e) {
				Logger.error("error while parsing mpc status");
			}
		}
	}
}
