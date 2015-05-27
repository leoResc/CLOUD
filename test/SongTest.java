import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
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

import models.Playlist;
import models.Song;

import org.junit.Test;

import play.data.Form;
import play.mvc.Content;

public class SongTest {

	@Test
	public void test_deleteSong() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				List<Song> songs = Song.find.all();
				Song song = songs.get(0);
				long id = song.id;

				Song.deleteSong(id);

				Song deletedSong = Song.find.byId(id);

				assert(deletedSong).equals(null);
			}
		});
	}

	@Test
	public void test_dislike() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				List<Song> songs = Song.find.all();
				Song song = songs.get(0);
				int likes = song.likes;

				song.dislike();

				assertThat(song.likes).equals(likes - 1);
			}
		});
	}

	@Test
	public void test_like() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				List<Song> songs = Song.find.all();
				Song song = songs.get(0);
				int likes = song.likes;

				song.like();

				assertThat(song.likes).equals(likes + 1);
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

					assertThat(artist).equals(song.artist);
					assertThat(title).equals(song.title);
					assertThat(genre).equals(song.genre);
					assertThat(duration).equals(song.duration);
					
				} catch (IOException | NoMPEGFramesException
						| ID3v2FormatException | CorruptHeaderException e) {
					assert (false);
				}
			}
		});
	}
}
