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
import play.mvc.Result;
import play.mvc.Controller;
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
		Logger.info("----------> getPlaylist");
		return ok(playlist.render(songs,playlistform, playlists, new ArrayList<Song>(), null));
	}
	
	public static Result getSong() {
		return ok(song.render());
	}
	
	public static Result getUser() {
		return ok(user.render());
	}
	
	public static Result showPlaylist(long id){
		
		final Playlist myplaylist = Playlist.find.byId(id);
		Form<Playlist> filledPlaylist = playlistform.fill(myplaylist);
		List<Song> songs = Song.find.all();
		List<Playlist> playlists = Playlist.find.all();
		Logger.info("------------> showPlaylist");
		myplaylist.updateListOfSongs();
		return ok(playlist.render(songs,filledPlaylist, playlists, myplaylist.songListe, myplaylist.name));
	}
	
	public static Result savePlaylist(){
		
		Form<Playlist> formPlaylist = Form.form(Playlist.class).bindFromRequest();
		Map<String, String> myPlaylist = formPlaylist.data();
		
		String name = myPlaylist.remove("name");
		Iterator it = myPlaylist.values().iterator();
		
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
}
