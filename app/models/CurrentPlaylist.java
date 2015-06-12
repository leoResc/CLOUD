package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.Logger;
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
	long id;
	long songID;

	public CurrentPlaylist(long songID) {
		this.songID = songID;
	}

	public static void fill() {
		List<CurrentPlaylist> oldPlaylist = CurrentPlaylist.find.all();
		for (CurrentPlaylist currentPlaylist : oldPlaylist) {
			currentPlaylist.delete();
		}
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
		List<Song> songs = new ArrayList<Song>();
		List<CurrentPlaylist> cp = CurrentPlaylist.find.all();
		for (CurrentPlaylist currentPlaylist : cp) {
			songs.add(Song.find.byId(currentPlaylist.songID));
		}
		Collections.sort(songs);
		Logger.info("amount of songs: " + songs.size());
		return songs;
	}

	public static void playNextSong() {
		Song song = getCurrentPlaylist().get(0);
		Logger.info("song id: " + song.id);
		CurrentPlaylist.find.where().eq("songID", song.id).findUnique()
				.delete();
		String mp3 = song.artist + "-" + song.title + ".mp3";
		mp3 = mp3.replaceAll("\\s", "");
		Logger.info("telling mpc: " + "mpc add " + mp3);
		ShellCommand sh = new ShellCommand("mpc add " + mp3);
		sh.executeShellCommand();
	}

}
