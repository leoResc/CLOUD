package models;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import java.util.List;

import org.junit.Ignore;

public class LikesModelTest {

	/**
	 * Test method for Likes.setID()
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
