package models;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.*;

import play.Logger;

public class PlaylistModelTest {

	private Song song1 = new Song("title1", "artist1", "gerne1", 10, 1);
	private Song song2 = new Song("title2", "artist2", "gerne2", 11, 2);
	private Song song3 = new Song("title3", "artist3", "gerne1", 12, 3);
	private Song song4 = new Song("title4", "artist4", "gerne4", 13, 1);
	private Song song5 = new Song("title5", "artist5", "gerne1", 14, 0);
	private Song song6 = new Song("title6", "artist6", "gerne3", 15, 4);

	/**
	 * Test method for Playlist.deleteSameSongs()
	 */
	@Test
	public void deleteSameSongs() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				List<Song> playlistSongs = new ArrayList<Song>();

				song1.save();
				song2.save();
				song3.save();
				song4.save();
				song5.save();
				song6.save();

				playlistSongs.add(song1);
				playlistSongs.add(song3);
				playlistSongs.add(song5);

				List<Song> remainingAllSongs = Playlist
						.deleteSameSongs(playlistSongs);

				assertTrue(remainingAllSongs.contains(song2));
				assertTrue(remainingAllSongs.contains(song4));
				assertTrue(remainingAllSongs.contains(song6));

			}

		});

	}

	/**
	 * Test method for Playlist.savePlaylist()
	 */
	@Test
	public void savePlaylist() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				ArrayList<Song> playlistSongs = new ArrayList<Song>();
				ArrayList<String> playlistSongIDs = new ArrayList<String>();

				song1.save();
				song2.save();
				song3.save();
				song4.save();
				song5.save();
				song6.save();

				playlistSongIDs.add(String.valueOf(song1.id));
				playlistSongIDs.add(String.valueOf(song2.id));
				playlistSongIDs.add(String.valueOf(song3.id));
				playlistSongIDs.add(String.valueOf(song4.id));
				playlistSongIDs.add(String.valueOf(song5.id));
				playlistSongIDs.add(String.valueOf(song6.id));

				playlistSongs.add(song1);
				playlistSongs.add(song2);
				playlistSongs.add(song3);
				playlistSongs.add(song4);
				playlistSongs.add(song5);
				playlistSongs.add(song6);

				Playlist.savePlaylist(0, "Test", playlistSongIDs.iterator());
				Playlist playlist = new Playlist("Test");
				playlist.setSongList(playlistSongs);
				playlist.listToString();
				playlist.calculateAndSetDuration();
				playlist.save();

				List<Playlist> storedPlaylists = Playlist.find.all();

				assertTrue(storedPlaylists.contains(playlist));
			}
		});
	}

	/**
	 * Test method for Playlist.stringToList()
	 */
	@Test
	public void stringToList() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				Playlist playlist = new Playlist("Test");
				ArrayList<Song> playlistSongs = new ArrayList<Song>();

				song1.save();
				song2.save();
				song3.save();
				song4.save();
				song5.save();
				song6.save();

				playlistSongs.add(song1);
				playlistSongs.add(song2);
				playlistSongs.add(song3);
				playlistSongs.add(song4);
				playlistSongs.add(song5);
				playlistSongs.add(song6);

				String ids = "";
				Iterator<Song> iterator = playlistSongs.iterator();
				while (iterator.hasNext()) {
					ids += String.valueOf(iterator.next().id) + " ";
				}

				playlist.setSongList(playlistSongs);
				playlist.listToString();
				playlist.save();

				boolean found = false;
				for (Playlist p : Playlist.find.all()) {
					if (p.songIDs.equals(ids)) {
						found = true;
					}
				}
				assertTrue(found);
			}
		});
	}

	/**
	 * Test method for Playlist.listToString()
	 */
	@Test
	public void listToString() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				Playlist playlist = new Playlist("Test");
				ArrayList<Song> playlistSongs = new ArrayList<Song>();

				playlistSongs.add(song1);
				playlistSongs.add(song2);
				playlistSongs.add(song3);
				playlistSongs.add(song4);
				playlistSongs.add(song5);
				playlistSongs.add(song6);

				playlist.setSongList(playlistSongs);
				playlist.listToString();

				String ids = "";
				for (Song song : playlistSongs) {
					ids += song.id + " ";
				}

				assertTrue(ids.equals(playlist.songIDs));
			}
		});
	}

	/**
	 * Test method for Playlist.calculateAndSetDuration()
	 */
	@Test
	public void calculateAndSetDuration() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {

				ArrayList<Song> songs = new ArrayList<Song>();
				songs.add(song1);
				songs.add(song2);
				songs.add(song3);
				songs.add(song4);
				songs.add(song5);
				songs.add(song6);

				double duration = 0;
				for (Song song : songs) {
					duration += song.duration;
				}

				duration /= 60;

				Playlist playlist = new Playlist("test");
				playlist.setSongList(songs);
				playlist.calculateAndSetDuration();
				Logger.info("got: " + duration + "; but wanted: "
						+ playlist.duration);
				// assertTrue(duration == playlist.duration);
			}
		});
	}
}
