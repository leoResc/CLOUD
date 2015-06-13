package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.CurrentPlaylist;
import models.Event;
import models.EventPlaylist;
import models.Playlist;
import models.ShellCommand;
import models.Song;
import models.User;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Result;
import views.html.*;

public class Dashboard extends Controller {

	private static final Form<Playlist> playlistform = Form
			.form(Playlist.class);

	public static Result getDashboard() {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		int countUser = User.find.findRowCount();
		int countSongs = Song.find.findRowCount();
		return ok(dashboard.render(countUser, countSongs));
	}

	public static Result getEvent() {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		List<Playlist> playlists = Playlist.find.all();
		List<Event> allEvents = Event.find.all();
		return ok(views.html.event.render(playlists, allEvents));
	}

	public static Result getPlaylist() {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		List<Song> allSongs = Song.find.all();
		List<Playlist> allPlaylists = Playlist.find.all();
		return ok(views.html.playlist.render(allSongs, playlistform,
				allPlaylists, new ArrayList<Song>(), null, 0l));
	}

	public static Result getSong() {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		List<Song> songs = Song.find.all();
		return ok(song.render(songs));
	}

	public static Result getUser() {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		List<User> allUser = User.find.all();
		return ok(user.render(allUser));
	}

	public static Result getTime() {
		Calendar calendar = Calendar.getInstance();
		return ok(Json.toJson(calendar.getTime()));
	}

	public static Result deleteUser(long id) {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		User.byId.byId(id).delete();
		return ok(Json.toJson(User.find.all()));
	}

	public static Result deleteAllUser() {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		List<User> alluser = User.find.all();
		for (User user : alluser) {
			user.delete();
		}
		return redirect(routes.Dashboard.getUser());
	}

	public static Result updateTime(String date) {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(Long.valueOf(date));

		String shellCommand = "sudo date -s \"" + calendar.get(Calendar.YEAR)
				+ "-" + (calendar.get(Calendar.MONTH) < 10 ? "0" : "")
				+ (calendar.get(Calendar.MONTH) + 1) + "-"
				+ (calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "")
				+ calendar.get(Calendar.DAY_OF_MONTH) + " "
				+ (calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "")
				+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ (calendar.get(Calendar.MINUTE) < 10 ? "0" : "")
				+ calendar.get(Calendar.MINUTE) + ":" + "00\"";
		ShellCommand command = new ShellCommand(shellCommand);
		command.executeShellCommand();

		return ok(Json.toJson(calendar.getTime()));
	}

	public static Result editPlaylist(long id) {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		List<Song> allSongs = Song.find.all();
		List<Playlist> allPlaylists = Playlist.find.all();

		Playlist playlist = Playlist.find.byId(id);
		playlist.stringToList();
		Form<Playlist> filledPlaylist = playlistform.fill(playlist);

		allSongs = Playlist.deleteSameSongs(playlist.getSongList());

		return ok(views.html.playlist.render(allSongs, filledPlaylist,
				allPlaylists, playlist.getSongList(), playlist.name, id));
	}

	public static Result deletePlaylist(long id) {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		Playlist playlist = Playlist.find.byId(id);
		if (playlist != null) {
			playlist.delete();
		}

		return redirect(routes.Dashboard.getPlaylist());
	}

	public static Result savePlaylist(long id) {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		Form<Playlist> formPlaylist = Form.form(Playlist.class)
				.bindFromRequest();
		Map<String, String> myPlaylist = formPlaylist.data();

		String playlistName = myPlaylist.remove("name");
		Iterator<String> iterator = myPlaylist.values().iterator();

		// No songs added
		if (myPlaylist.size() < 1) {
			return redirect(routes.Dashboard.getPlaylist());
		}
		Playlist.savePlaylist(id, playlistName, iterator);
		return redirect(routes.Dashboard.getPlaylist());
	}

	public static Result uploadSong() {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		MultipartFormData body = request().body().asMultipartFormData();
		List<MultipartFormData.FilePart> files = body.getFiles();
		Song.uploadSong(files);
		return ok();
	}

	public static Result deleteSong(long id) {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		Song.deleteSong(id);
		List<Song> songs = Song.find.all();
		return ok(Json.toJson(songs));
	}

	public static Result saveEvent() {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		List<Playlist> allPlaylists = Playlist.find.all();
		List<Event> allEvents = Event.find.all();
		Map<String, String[]> postData = request().body().asFormUrlEncoded();

		Event event = new Event();
		event.name = postData.get("name")[0];
		event.password = postData.get("password")[0];
		event.description = postData.get("description")[0];
		String begin = postData.get("begin")[0];
		String end = postData.get("end")[0];

		// no date selected or exception while parsing date
		int setDate = event.setDate(begin, end);
		if (setDate == -2) {
			flash("error",
					"You didn't select any begin or end for the event ...");
			return ok(views.html.event.render(allPlaylists, allEvents));
		} else if (setDate == -1) {
			flash("error",
					"The ending date of the event isn't before the beginning date ...");
			return ok(views.html.event.render(allPlaylists, allEvents));
		}
		event.save();

		Set<String> postSet = postData.keySet();
		Iterator<String> iterator = postSet.iterator();
		int count = 0;

		while (iterator.hasNext()) {
			String key = iterator.next();
			if (!(key.equals("name") || key.equals("password")
					|| key.equals("description") || key.equals("begin") || key
						.equals("end"))) {
				count++;
				EventPlaylist eventPlaylist = new EventPlaylist(event.id,
						Long.parseLong(key));
				eventPlaylist.save();
			}
		}

		// no playlists selected
		if (count == 0) {
			flash("error", "You didn't select any playlist for the event ...");
			Event.find.byId(event.id).delete();
		}

		return redirect(routes.Dashboard.getEvent());
	}

	public static Result deleteEvent(long id) {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		Event event = Event.find.byId(id);
		event.delete();
		List<Event> allEvents = Event.find.all();
		return ok(Json.toJson(allEvents));
	}

	/**
	 * Method for play - pause - forward music
	 * 
	 * @param represents
	 *            different modes: 0 - play; 1 - pause; 2 - forward
	 */
	public static Result musicControl(int mode) {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		String command = "mpc ";
		if (mode == 0) {
			command += "play";
		} else if (mode == 1) {
			command += "pause";
		} else if (mode == 2) {
			CurrentPlaylist.playNextSong();
		}
		Logger.info(command);
		ShellCommand sh = new ShellCommand(command);
		sh.executeShellCommand();
		return ok();
	}

	/**
	 * Method for adjusting the volume
	 * 
	 * @param volume
	 *            volume level between 0 and 100
	 */
	public static Result setVolume(int volume) {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		if ((volume >= 0) && (volume <= 100)) {
			ShellCommand sh = new ShellCommand("mpc volume " + volume);
			sh.executeShellCommand();
		}
		return ok();
	}

	/**
	 * Returns the current volume
	 * 
	 */
	public static Result getVolume() {
		String session = session("user");
		if (session == null) {
			return redirect(routes.Application.getLogin());
		} else {
			if (!session.equals("admin")) {
				return forbidden(forbidden.render("NOT AUTHORIZED"));
			}
		}
		ShellCommand sh = new ShellCommand("mpc volume");
		StringBuffer output = sh.executeShellCommand();
		String volume = output.substring(7, output.length() - 2);
		Logger.info("read volume: " + volume);
		return ok(Json.toJson(volume));
	}
}