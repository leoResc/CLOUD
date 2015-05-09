package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import akka.event.Logging;
import models.Playlist;
import models.ShellCommand;
import models.Song;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;

import java.util.List;

import javax.swing.ListSelectionModel;

import org.h2.command.dml.Delete;

import com.google.common.collect.Lists;

import play.*;
import models.Song;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import scala.util.parsing.json.JSON;
import views.html.*;

public class Dashboard extends Controller {

	public static Result getDashboard() {
		return ok(dashboard.render());
	}

	public static Result getEvent() {
		return ok(event.render());
	}

	public static Result getPlaylist() {
		List<Song> songs = Song.find.all();
		List<Playlist> playlists = Playlist.find.all();
		long idd = 0;
		return ok(playlist.render(songs, playlistform, playlists,
				new ArrayList<Song>(), null, idd));
	}

	public static Result getSong() {
		List<Song> songs = Song.find.all();
		return ok(song.render(songs));
	}

	public static Result getUser() {
		return ok(user.render());
	}

	public static Result updateTime(String date) {
		String timeArray[] = date.split(" ");
		int year = Integer.valueOf(timeArray[3]);
		int month;

		switch (timeArray[1]) {
		case "Jan":
			month = Integer.valueOf(Calendar.JANUARY);
			break;
		case "Feb":
			month = Integer.valueOf(Calendar.FEBRUARY);
			break;
		case "Mar":
			month = Integer.valueOf(Calendar.MARCH);
			break;
		case "Apr":
			month = Integer.valueOf(Calendar.APRIL);
			break;
		case "May":
			month = Integer.valueOf(Calendar.MAY);
			break;
		case "Jun":
			month = Integer.valueOf(Calendar.JUNE);
			break;
		case "Jul":
			month = Integer.valueOf(Calendar.JULY);
			break;
		case "Aug":
			month = Integer.valueOf(Calendar.AUGUST);
			break;
		case "Sep":
			month = Integer.valueOf(Calendar.SEPTEMBER);
			break;
		case "Oct":
			month = Integer.valueOf(Calendar.OCTOBER);
			break;
		case "Nov":
			month = Integer.valueOf(Calendar.NOVEMBER);
			break;
		case "Dec":
			month = Integer.valueOf(Calendar.DECEMBER);
			break;
		default:
			month = Integer.valueOf(Calendar.JANUARY);
		}

		int day = Integer.valueOf(timeArray[2]);
		String time = timeArray[4];
		int hour = Integer.valueOf(time.substring(0, 2));
		int minute = Integer.valueOf(time.substring(3, 5));
		
		Calendar calendar = new GregorianCalendar(year, month, day, hour,
				minute);
		String shellCommand = "sudo date -s \"" + year + "-"
				+ (month < 10 ? "0" : "") + (month + 1) + "-"
				+ (day < 10 ? "0" : "") + day + " " + time + "\"";
		ShellCommand command = new ShellCommand(shellCommand);
		command.executeShellCommand();
		
		return ok(Json.toJson(calendar.getTime()));
	}

	public static Result showPlaylist(long id) {

		final Playlist foundPlaylist = Playlist.find.byId(id);
		foundPlaylist.updateListOfSongs();
		Form<Playlist> filledPlaylist = playlistform.fill(foundPlaylist);

		List<Song> songs = Song.find.all();
		songs = deleteSameSongs(songs, foundPlaylist.songListe);
		Logger.info(songs.size() + " songs on left side");

		List<Playlist> allPlaylists = Playlist.find.all();

		return ok(playlist.render(songs, filledPlaylist, allPlaylists,
				foundPlaylist.songListe, foundPlaylist.name, id));
	}

	public static Result deletePlaylist(long id) {

		final Playlist foundPlaylist = Playlist.find.byId(id);
		foundPlaylist.delete();

		return redirect(routes.Dashboard.getPlaylist());
	}

	public static List<Song> deleteSameSongs(List<Song> allSongs,
			List<Song> playlistSongs) {
		List<Song> songsClear = new ArrayList<Song>();

		Iterator<Song> songs = allSongs.listIterator();
		Iterator<Song> songsPlaylist = playlistSongs.listIterator();

		while (songsPlaylist.hasNext()) {

			Song songInList = songsPlaylist.next();

			while (songs.hasNext()) {

				Song songToCheck = songs.next();

				if (songInList.id != songToCheck.id) {
					songsClear.add(songToCheck);
				}
			}
			songs = songsClear.listIterator();
			songsClear.clear();
		}

		return Lists.newArrayList(songs);
	}

	public static Result savePlaylist(long idToDelete) {

		Form<Playlist> formPlaylist = Form.form(Playlist.class)
				.bindFromRequest();
		Map<String, String> myPlaylist = formPlaylist.data();

		String name = myPlaylist.remove("name");
		Iterator it = myPlaylist.values().iterator();

		if (myPlaylist.size() < 1) {
			// No songs added
			Logger.info("No songs were added!");
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

	public static long calculateDuration(Iterator it) {
		long duration = 0;

		while (it.hasNext()) {
			long id = Integer.valueOf((String) it.next());

			Logger.info("id: " + id);

			Song song = Song.find.byId(id);
			duration += song.duration;
		}
		return duration;
	}

	private static final Form<Playlist> playlistform = Form
			.form(Playlist.class);

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