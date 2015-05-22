import static org.fest.assertions.Assertions.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import java.util.ArrayList;
import java.util.List;

import models.Playlist;
import models.Song;

import org.junit.*;

public class PlaylistModelTest {

	@Test
	public void CheckDeleteSameSongs() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				List<Song> playlistSongs = new ArrayList<Song>();

				Song song_1 = new Song("title1", "artist1", "gerne1", 10, 1);
				Song song_2 = new Song("title2", "artist2", "gerne2", 11, 2);
				Song song_3 = new Song("title3", "artist3", "gerne1", 12, 3);
				Song song_4 = new Song("title4", "artist4", "gerne4", 13, 1);
				Song song_5 = new Song("title5", "artist5", "gerne1", 14, 0);
				Song song_6 = new Song("title6", "artist6", "gerne3", 15, 4);

				song_1.save();
				song_2.save();
				song_3.save();
				song_4.save();
				song_5.save();
				song_6.save();

				playlistSongs.add(song_1);
				playlistSongs.add(song_3);
				playlistSongs.add(song_5);

				List<Song> remainingAllSongs = Playlist
						.deleteSameSongs(playlistSongs);

				assertThat(remainingAllSongs.contains(song_2));
				assertThat(remainingAllSongs.contains(song_4));
				assertThat(remainingAllSongs.contains(song_6));

			}

		});

	}
}