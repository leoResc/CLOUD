package models;

import static org.junit.Assert.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import helliker.id3.CorruptHeaderException;
import helliker.id3.ID3v2FormatException;
import helliker.id3.MP3File;
import helliker.id3.NoMPEGFramesException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Song;

import org.junit.Ignore;
import org.junit.Test;

public class SongModelTest {

	private Song song1 = new Song("title1", "artist1", "gerne1", 10, 1);
	private Song song2 = new Song("title2", "artist2", "gerne2", 11, 2);
	private Song song3 = new Song("title3", "artist3", "gerne1", 12, 3);
	private Song song4 = new Song("title4", "artist4", "gerne4", 13, 1);
	private Song song5 = new Song("title5", "artist5", "gerne1", 14, 0);
	private Song song6 = new Song("title6", "artist6", "gerne3", 15, 4);

	@Ignore
	public void test_deleteSong() {
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

				List<Song> songs = Song.find.all();
				Song song = songs.get(0);
				long id = song.id;

				Song.deleteSong(id);

				Song deletedSong = Song.find.byId(id);

				assertTrue(deletedSong == null);
			}
		});
	}

	@Test
	public void test_dislike() {
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

				List<Song> songs = Song.find.all();
				Song song = songs.get(0);
				int likes = song.likes;

				song.dislike();

				assertTrue(song.likes == (likes - 1));
			}
		});
	}

	@Test
	public void test_like() {
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

				List<Song> songs = Song.find.all();
				Song song = songs.get(0);
				int likes = song.likes;

				song.like();

				assertTrue(song.likes == (likes + 1));
			}
		});
	}

	@Test
	public void test_getID3Tags() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				try {
					File file = new File("Cucumber/features/TestSong.mp3");
					MP3File mp3 = new MP3File("Cucumber/features/TestSong.mp3");

					String artist = mp3.getArtist();
					String title = mp3.getTitle();
					String genre = mp3.getGenre();
					long duration = mp3.getPlayingTime();

					Song song = Song.getID3Tags(file);

					assertTrue(artist.equals(song.artist));
					assertTrue(title.equals(song.title));
					assertTrue(genre.equals(song.genre));
					assertTrue(duration == song.duration);

				} catch (IOException | NoMPEGFramesException
						| ID3v2FormatException | CorruptHeaderException e) {
					assert (false);
				}
			}
		});
	}
}
