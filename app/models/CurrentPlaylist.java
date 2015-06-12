package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

/**
 * This class CurrentPlaylist represents the current playlist of the event which
 * is currently active. This relation contains all the songs of the event
 * ordered by votes.
 * 
 * @author Philipp
 *
 */
@Entity
public class CurrentPlaylist extends Model {

	public static Finder<Long, CurrentPlaylist> find = new Finder<Long, CurrentPlaylist>(
			Long.class, CurrentPlaylist.class);

	@Id
	long songID;
	long ranking;

	public CurrentPlaylist(long songID) {
		this.songID = songID;
	}

	public static void fill() {
		Event event = Event.getCurrentEvent();
		if (event != null) {
			List<Playlist> playlists = EventPlaylist
					.getPlaylistsForEvent(event.id);
			for (Playlist playlist : playlists) {
				ArrayList<Song> songs = playlist.getSongList();
				for (Song song : songs) {
					CurrentPlaylist cp = new CurrentPlaylist(song.id);
					cp.save();
				}
			}
		}
	}

	public static List<Song> getCurrentPlaylist() {
		Event event = Event.getCurrentEvent();
		List<Song> songs = new ArrayList<Song>();
		if (event != null) {
			List<Playlist> playlists = EventPlaylist.getPlaylistsForEvent(event.id);
			for (Playlist playlist : playlists) {
				songs.addAll(playlist.getSongList());
			}
		}
		Collections.sort(songs);
		return songs;
	}
	
	public static void test() {
		Song song = getCurrentPlaylist().remove(0);
		ShellCommand sh = new ShellCommand("mpc add " + song.artist + song.title + ".mp3");
	}

}
