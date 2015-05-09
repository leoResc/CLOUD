package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.Playlist;
import models.ShellCommand;
import models.Song;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Result;
import views.html.dashboard;
import views.html.event;
import views.html.playlist;
import views.html.song;
import views.html.user;


public class Dashboard extends Controller {

	private static final Form<Playlist> playlistform = Form
			.form(Playlist.class);

	public static Result getDashboard() {
		return ok(dashboard.render());
	}

	public static Result getEvent() {
		return ok(event.render());
	}

	public static Result getPlaylist() {
		List<Song> songs = Song.find.all();
		List<Playlist> playlists = Playlist.find.all();
		return ok(playlist.render(songs, playlistform, playlists,
				new ArrayList<Song>(), null, 0l));
	}

	public static Result getSong() {
		List<Song> songs = Song.find.all();
		return ok(song.render(songs));
	}

	public static Result getUser() {
		return ok(user.render());
	}
	
	public static Result deleteEvent(long id) {
		return TODO;
	}

	public static Result updateTime(String date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(Long.valueOf(date));

		String shellCommand = "sudo date -s \"" + calendar.get(Calendar.YEAR)
				+ "-"
				+ (calendar.get(Calendar.MONTH) < 10 ? "0" : "")
				+ (calendar.get(Calendar.MONTH) + 1) + "-"
				+ (calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "")
				+ calendar.get(Calendar.DAY_OF_MONTH) + " "
				+ (calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "")
				+ calendar.get(Calendar.HOUR_OF_DAY) + ":" 
				+ (calendar.get(Calendar.MINUTE) < 10 ? "0" : "")
				+ calendar.get(Calendar.MINUTE) + ":"
				+ "00\"";
		ShellCommand command = new ShellCommand(shellCommand);
		command.executeShellCommand();

		return ok(Json.toJson(calendar.getTime()));
	}

	public static Result editPlaylist(long id) {

		Playlist foundPlaylist = Playlist.find.byId(id);
		foundPlaylist.updateListOfSongs();
		Form<Playlist> filledPlaylist = playlistform.fill(foundPlaylist);

		List<Song> songs = Song.find.all();
		songs = Playlist.deleteSameSongs(songs, foundPlaylist.songListe);

		List<Playlist> allPlaylists = Playlist.find.all();

		return ok(playlist.render(songs, filledPlaylist, allPlaylists,
				foundPlaylist.songListe, foundPlaylist.name, id));
	}

	public static Result deletePlaylist(long id) {

		Playlist playlist = Playlist.find.byId(id);
		playlist.delete();

		return redirect(routes.Dashboard.getPlaylist());
	}

	public static Result savePlaylist(long idToDelete) {

		Form<Playlist> formPlaylist = Form.form(Playlist.class)
				.bindFromRequest();
		Map<String, String> myPlaylist = formPlaylist.data();

		String name = myPlaylist.remove("name");
		Iterator<String> it = myPlaylist.values().iterator();

		if (myPlaylist.size() < 1) {
			// No songs added
			return redirect(routes.Dashboard.getPlaylist());
		}

		if (idToDelete > 0) {
			Playlist.find.byId(idToDelete).delete();
		}

		ArrayList<Song> songs = new ArrayList<Song>();

		long duration = calculateDuration(it);

		while (it.hasNext()) {
			long id = Integer.valueOf((String) it.next());
			Song song = Song.find.byId(id);
			songs.add(song);
		}

		Playlist playlist = new Playlist(name, songs, duration);
		playlist.save();

		return redirect(routes.Dashboard.getPlaylist());
	}

	public static long calculateDuration(Iterator<String> it) {
		long duration = 0;
		long id;
		while (it.hasNext()) {
			id = Integer.valueOf((String) it.next());

			Song song = Song.find.byId(id);
			duration += song.duration;
		}
		return duration;
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
}