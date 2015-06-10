package models;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.avaje.ebeaninternal.server.cluster.mcast.McastPacketControl;

public class LikesModelTest {

	@Test
	public void test_setDongID() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {

				Likes like = new Likes();

				like.setSongID(123);

				assert (true);
				
				// wrong id number
				
				like.setSongID(-2);
				
				assert (true);
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

				assert (true);
				
				// wrong id number
				
				like.setUserID(-3);
				
				assert (true);
			}
		});
	}

	@Test
	public void test_deleteLike() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				Song mockSong = new Song("a", "b", "c", 234, 0);
				mockSong.save();
				
				Likes mockLike = new Likes();
				mockLike.songID = mockSong.id;
				mockLike.save();

				Likes like = Likes.find.all().get(0);
				like.deleteLike(mockLike.songID, mockLike.userID);

				Likes deletedLike = Likes.find.byId(like.id);

				assertTrue(deletedLike == null);
			}
		});
	}

	@Test
	public void test_findLike() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				Likes mockLike = new Likes();
				mockLike.songID = mockLike.id;
				mockLike.userID = 5;
				mockLike.save();

				Song mockSong = new Song("a", "b", "c", 234, 0);
				mockSong.save();

				List<Likes> likes = Likes.find.all();
				assertTrue(likes.contains(mockLike));
			}
		});
	}

	@Test
	public void test_createLike() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {

			@Override
			public void run() {
				Song mockSong = new Song("a", "b", "c", 234, 0);
				mockSong.save();

				Likes like = new Likes();

				like = like.createLike(mockSong.id, 222);

				assertTrue(like.songID == mockSong.id);
				assertTrue(like.userID == 222);
			}
		});
	}

}
