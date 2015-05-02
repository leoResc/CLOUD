package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import akka.event.Logging;
import models.Playlist;
import models.Song;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import java.util.List;
import org.h2.command.dml.Delete;
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
		return ok(playlist.render(songs,playlistform, playlists, new ArrayList<Song>(), null, idd ));
	}
	
	public static Result getSong() {
		List<Song> songs = Song.find.all();
		return ok(song.render(songs));
	}
	
	public static Result getUser() {
		return ok(user.render());
	}
	
	public static Result showPlaylist(long id){
		
		final Playlist foundPlaylist = Playlist.find.byId(id);
		foundPlaylist.updateListOfSongs();
		Form<Playlist> filledPlaylist = playlistform.fill(foundPlaylist);
		
		List<Song> songs = Song.find.all();
		songs = deleteSameSongs(songs, foundPlaylist.songListe);
		Logger.info(songs.size()+" songs on left side");
		
		List<Playlist> allPlaylists = Playlist.find.all();
		
		
		return ok(playlist.render(songs,filledPlaylist, 
				allPlaylists, foundPlaylist.songListe, foundPlaylist.name, id));
	}
	
	public static Result deletePlaylist(long id) {
		
		final Playlist foundPlaylist = Playlist.find.byId(id);
		foundPlaylist.delete();
		
		return redirect(routes.Dashboard.getPlaylist());
	}
	
	public static  List<Song> deleteSameSongs(List<Song> allSongs, List<Song> playlistSongs) {
		List<Song> songs = allSongs;
		List<Song> songsCleaned = new ArrayList<Song>();
		
		for(int i=0; i<playlistSongs.size(); i++) {
			
			Logger.info("Loop with i:" + i+", songs size: "+songs.size() + ", clearedList: "+songsCleaned.size());
			Song songInList = playlistSongs.get(i);
			Logger.info("Search for id#"+songInList.id);
			
			for(int j=0; j<songs.size(); j++) {
				Song songToCheck = songs.get(j);
				Logger.info("Check with id#"+songToCheck.id);
				
				if(songInList.id != songToCheck.id){
					songsCleaned.add(songToCheck);
					Logger.info(songToCheck.id +" ^ added");
				}
			}
			
			songs.clear();
			songs.addAll(songsCleaned);
			songsCleaned.clear();
			Logger.info("songs size:"+songs.size());
		}
		Logger.info("return " +songs.size()+ " songs");
		return songs;
	}
	
	public static Result savePlaylist(long idToDelete){
		
		Form<Playlist> formPlaylist = Form.form(Playlist.class).bindFromRequest();
		Map<String, String> myPlaylist = formPlaylist.data();
		
		String name = myPlaylist.remove("name");
		Iterator it = myPlaylist.values().iterator();
		
		if(myPlaylist.size() < 1) {
			//No songs added
			Logger.info("No songs were added!");
			return redirect(routes.Dashboard.getPlaylist());
		}
		
		if(idToDelete > 0){
		Playlist.find.byId(idToDelete).delete();
		}
	
		ArrayList<Song> songs = new ArrayList<Song>();
		
		long duration = 0;
		
		while (it.hasNext()) {
			long id = Integer.valueOf((String) it.next());
			
			Logger.info("id: "+id);
			
			Song song = Song.find.byId(id);
			duration += song.duration;
			songs.add(song);
		}
		
		Playlist playlist = new Playlist(name, songs, duration);
		playlist.save();
	
		return redirect(routes.Dashboard.getPlaylist());
	}
	
	private static final Form<Playlist> playlistform = Form.form(Playlist.class);

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
