import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import java.util.ArrayList;
import java.util.List;

import models.Likes;
import models.Playlist;
import models.Song;

import org.junit.Test;

import play.data.Form;
import play.mvc.Content;

public class LikesTest {

	@Test
	public void test_setDongID() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				
				Likes like = new Likes();
				
				like.setSongID(123);

				assert(true);
			}
		});
	}
	
	@Test
	public void test_setUserID() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				
				Likes like = new Likes();
				
				like.setUserID(123);

				assert(true);
			}
		});
	}
	
	@Test
	public void test_deleteLike() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				
				Likes like = Likes.find.all().get(0);
				
				like.deleteLike(like.songID, like.userID);
				
				Likes deletedLike = Likes.find.byId(like.id);
				
				assertThat(deletedLike).equals(null);
			}
		});
	}
	
	@Test
	public void test_findLike() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				
				List<Likes> likes = Likes.find.all();
				Likes like = likes.get(0);
				
				Likes foundLike = like.findLike(like.songID, like.userID);
				
				assertThat(like.songID).equals(foundLike.songID);
				assertThat(like.userID).equals(foundLike.userID);
			}
		});
	}
	
	@Test
	public void test_createLike() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				
				Likes like = new Likes();
				
				like = like.createLike(111, 222);
				
				assertThat(like.songID).equals(111);
				assertThat(like.userID).equals(222);
			}
		});
	}
	
}
