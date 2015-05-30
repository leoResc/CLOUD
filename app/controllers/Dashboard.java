package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.Event;
import models.EventPlaylist;
import models.Playlist;
import models.ShellCommand;
import models.Song;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Result;

public class Dashboard extends Controller {

	private static final Form<Playlist> playlistform = Form
			.form(Playlist.class);

	public static Result getDashboard() {
		return ok(views.html.dashboard.render());
	}

	public static Result getEvent() {
		List<Playlist> playlists = Playlist.find.all();
		List<Event> allEvents = Event.find.all();
		return ok(views.html.event.render(playlists, allEvents));
	}

	public static Result getPlaylist() {
		List<Song> allSongs = Song.find.all();
		List<Playlist> allPlaylists = Playlist.find.all();
		return ok(views.html.playlist.render(allSongs, playlistform, allPlaylists,
				new ArrayList<Song>(), null, 0l));
	}

	public static Result getSong() {
		List<Song> songs = Song.find.all();
		return ok(views.html.song.render(songs));
	}

	public static Result getUser() {
		return ok(views.html.user.render());
	}

	public static Result deleteEvent(long id) {
		return TODO;
	}

	public static Result updateTime(String date) {
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

		Playlist playlist = Playlist.find.byId(id);
		if (playlist != null) {
			playlist.delete();
		}

		return redirect(routes.Dashboard.getPlaylist());
	}

	public static Result savePlaylist(long id) {

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
		MultipartFormData body = request().body().asMultipartFormData();
		List<MultipartFormData.FilePart> files = body.getFiles();
		Song.uploadSong(files);
		return ok();
	}

	public static Result deleteSong(long id) {
		Song.deleteSong(id);
		List<Song> songs = Song.find.all();
		return ok(Json.toJson(songs));
	}

	public static Result saveEvent() {
		List<Playlist> allPlaylists = Playlist.find.all();
		List<Event> allEvents = Event.find.all();
		Map<String, String[]> postData = request().body().asFormUrlEncoded();

		Event event = new Event();
		event.name = postData.get("name")[0];
		event.password = postData.get("password")[0];
		event.description = postData.get("description")[0];
		String begin = postData.get("begin")[0];
		Logger.info("begin was " + begin);
		String end = postData.get("end")[0];
		Logger.info("end was " + end);

		// no date selected or exception while parsing date
		if (!event.setDate(begin, end)) {
			Logger.info("exception while date parsing");
			return ok(views.html.event.render(allPlaylists, allEvents));
		}
		;
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
			Logger.info("No playlist selcted");
			Event.find.byId(event.id).delete();
		}

		return ok(views.html.event.render(allPlaylists, allEvents));
	}
}