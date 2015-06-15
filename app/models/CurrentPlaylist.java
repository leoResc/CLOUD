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
	long id;
	long songID;

	public CurrentPlaylist(long songID) {
		this.songID = songID;
	}

	/**
	 * Fills the playlist for the current event
	 */
	public static void fillCurrentPlaylist() {

		// delete playlist entries of last event
		List<CurrentPlaylist> oldPlaylist = CurrentPlaylist.find.all();
		for (CurrentPlaylist currentPlaylist : oldPlaylist) {
			currentPlaylist.delete();
		}

		ShellCommand sh = new ShellCommand("mpc clear");
		sh.executeShellCommand();
		// search all playlists for today's event and their contained songs
		Event event = Event.getCurrentEvent();

		if (event != null) {

			List<Playlist> playlists = EventPlaylist
					.getPlaylistsForEvent(event.id);
			for (Playlist playlist : playlists) {

				ArrayList<Song> songs = playlist.getSongList();

				for (Song song : songs) {

					CurrentPlaylist cp = new CurrentPlaylist(song.id);

					if (CurrentPlaylist.find.byId(song.id) == null) {
						cp.save();
					} else {
						cp.delete();
					}
				}
			}
		}
	}

	/**
	 * 
	 * @return returns all songs in the current playlist for today's event
	 */
	public static List<Song> getCurrentPlaylist() {
		List<Song> songs = new ArrayList<Song>();

		if (Event.getCurrentEvent() != null) {
			List<CurrentPlaylist> cp = CurrentPlaylist.find.all();
			for (CurrentPlaylist currentPlaylist : cp) {
				Song song = Song.find.byId(currentPlaylist.songID);

				if (songs.contains(song) == false)
				{
					songs.add(song);
				}
			}
			Collections.sort(songs);
		}

		return songs;
	}

	/**
	 * Adds next song to playlist of mpc
	 */
	public static void addNextSongToPlaylist() {
		List<Song> currentPlaylist = getCurrentPlaylist();
		if (currentPlaylist.size() > 0) {
			Song song = currentPlaylist.get(0);
			CurrentPlaylist.find.where().eq("songID", song.id).findUnique()
					.delete();
			String mp3 = song.artist + "-" + song.title + ".mp3";
			mp3 = mp3.replaceAll("\\s", "");
			ShellCommand sh = new ShellCommand("mpc add " + mp3);
			sh.executeShellCommand();
		}
	}

}
