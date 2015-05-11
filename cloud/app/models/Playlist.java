package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Playlist extends Model {

	public static Finder<Long, Playlist> find = new Finder<Long, Playlist>(
			Long.class, Playlist.class);

	@Id
	public long id;
	public String name;
	public int numberOfSongs;
	public double duration;
	@OneToMany(mappedBy = "playlist")
	public ArrayList<Song> songList;
	public String songIDs;

	public Playlist(String name) {
		this.name = name;
		this.numberOfSongs = 0;
		this.duration = 0;
		this.songList = new ArrayList<Song>();
		this.songIDs = "";
	}

	public ArrayList<Song> getSongList() {
		return songList;
	}

	public void setSongList(ArrayList<Song> songList) {
		this.songList = songList;
		this.numberOfSongs = this.songList.size();
	}

	/**
	 * Calculates and sets the duration of this playlist in seconds
	 */
	public void calculateAndSetDuration() {
		for (Song song : this.songList) {
			this.duration += song.duration;
		}
		this.duration /= 60;
	}

	/**
	 * Transforms the list of songs into a string; each song's ID is separated
	 * by a whitespace
	 */
	public void listToString() {
		this.songIDs = "";
		for (Song song : this.songList) {
			this.songIDs += song.id + " ";
		}
	}

	/**
	 * Creates List of songs using the String with their IDs
	 */
	public void stringToList() {

		ArrayList<Song> songs = new ArrayList<Song>();

		Scanner scanner = new Scanner(this.songIDs);
		long id;
		while (scanner.hasNextLong()) {
			id = scanner.nextLong();
			Song song = Song.find.byId(id);
			if (song != null) {
				songs.add(song);
			}
		}
		scanner.close();
		this.songList = songs;
	}

	/**
	 * This method creates a Playlist object and saves it in the database
	 * 
	 * @param idToDelete
	 *            ID of the playlist which shall be updated
	 * @param playlistName
	 *            Name of the playlist
	 * @param iterator
	 *            Iterator over list of songs
	 */
	public static void savePlaylist(long idToDelete, String playlistName,
			Iterator<String> iterator) {

		if (idToDelete > 0) {
			Playlist.find.byId(idToDelete).delete();
		}

		ArrayList<Song> songs = new ArrayList<Song>();
		long id;
		while (iterator.hasNext()) {
			id = Integer.valueOf(iterator.next());
			Song song = Song.find.byId(id);
			songs.add(song);
		}

		Playlist playlist = new Playlist(playlistName);
		playlist.setSongList(songs);
		playlist.listToString();
		playlist.calculateAndSetDuration();
		playlist.save();
	}

	public static List<Song> deleteSameSongs(List<Song> playlistSongs) {
		
		List<Song> allSongs = Song.find.all();
		Iterator<Song> songsPlaylist = playlistSongs.listIterator();

		while (songsPlaylist.hasNext()) {

			Song songInList = songsPlaylist.next();
			if (allSongs.contains(songInList)) {
				allSongs.remove(songInList);
			}
		}

		return allSongs;
	}
}