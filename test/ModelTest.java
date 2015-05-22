import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;
import static play.test.Helpers.*;

import java.util.List;
import models.Likes;
import org.junit.*;

public class ModelTest {

	/**
	 * Model: Likes.setID()
	 */
	@Ignore
	public void LikesSetID() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				Likes likes = new Likes();
				long song = 123;
				long user = 456;
				likes.createLike(song, user);
				likes.save();
				String id = song + "" + user;

				List<Likes> allLikes = Likes.find.all();
				Likes savedLike = null;

				for (Likes like : allLikes) {
					if (like.songID == song && like.userID == user) {
						savedLike = like;
					}
				}

				assertNotNull(savedLike);
				assertThat(savedLike.id).isEqualTo(Long.valueOf(id));
				assertThat(savedLike.songID).isEqualTo(song);
				assertThat(savedLike.userID).isEqualTo(user);
			}
		});
	}
}
