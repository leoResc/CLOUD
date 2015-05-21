import static org.fest.assertions.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import models.Song;
import org.junit.*;

public class DashboardTest {

	@Test
	public void CheckDeleteSameSongs() {
		List<Song> allSongs = new ArrayList<Song>();
		List<Song> deleteSongs = new ArrayList<Song>();
		List<Song> sortedSongs = new ArrayList<Song>();

		Song song_1 = new Song("title1", "artist1", "gerne1", 10, 1);
		Song song_2 = new Song("title2", "artist2", "gerne2", 11, 2);
		Song song_3 = new Song("title3", "artist3", "gerne1", 12, 3);
		Song song_4 = new Song("title4", "artist4", "gerne4", 13, 1);
		Song song_5 = new Song("title5", "artist5", "gerne1", 14, 0);
		Song song_6 = new Song("title6", "artist6", "gerne3", 15, 4);

		allSongs.add(song_1);
		allSongs.add(song_2);
		allSongs.add(song_3);
		allSongs.add(song_4);
		allSongs.add(song_5);
		allSongs.add(song_6);

		deleteSongs.add(song_1);
		deleteSongs.add(song_2);
		deleteSongs.add(song_3);

		sortedSongs.add(song_4);
		sortedSongs.add(song_5);
		sortedSongs.add(song_6);

		List<Song> gotSongs = models.Playlist.deleteSameSongs(deleteSongs);

		for (int i = 0; i < gotSongs.size(); i++) {
			assertThat(gotSongs.get(i)).isEqualTo(sortedSongs.get(i));
		}
		// assertThat(gotSongs).isEqualTo(sortedSongs);

	}
}
