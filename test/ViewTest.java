import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.ArrayList;

import models.Playlist;
import models.Song;

import org.junit.Test;

import play.data.Form;
import play.mvc.Content;

public class ViewTest {

	/**
	 * View: Old view CreateEvent
	 */
	@Test
	public void testCreateEventView() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				Content html = views.html.createEvent.render();

				assertThat(contentType(html)).isEqualTo("text/html");

				assertThat(contentAsString(html)).contains(
						"Adminpanel - Create Event");
			}

		});
	}

	/**
	 * View: Login page
	 */
	@Test
	public void testLoginView() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				Content html = views.html.login.render(null);

				assertThat(contentType(html)).isEqualTo("text/html");

				assertThat(contentAsString(html)).contains("Welcome to Cloud");
			}

		});
	}

	/**
	 * View: Landing page
	 */
	@Test
	public void testLandingView() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				Content html = views.html.index.render(new ArrayList<Song>(), null);

				assertThat(contentType(html)).isEqualTo("text/html");

				assertThat(contentAsString(html)).contains("CLOUD");
			}

		});
	}

	/**
	 * View: Dashboard - Overview
	 */
	@Test
	public void testDashboardView() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				Content html = views.html.dashboard.render();

				assertThat(contentType(html)).isEqualTo("text/html");

				assertThat(contentAsString(html)).contains(
						"Dashboard - Overview");
			}

		});
	}

	/**
	 * View: Dashboard - Events
	 */
	@Test
	public void testEventView() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				Content html = views.html.event.render();

				assertThat(contentType(html)).isEqualTo("text/html");

				assertThat(contentAsString(html))
						.contains("Dashboard - Events");
			}

		});
	}

	/**
	 * View: Dashboard - Playlists
	 */
	@Test
	public void testPlaylistView() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				Form<Playlist> playlistform = Form.form(Playlist.class);
				Content html = views.html.playlist.render(
						new ArrayList<Song>(), playlistform,
						new ArrayList<Playlist>(), new ArrayList<Song>(), null,
						(long) 0);

				assertThat(contentType(html)).isEqualTo("text/html");

				assertThat(contentAsString(html)).contains(
						"Dashboard - Playlists");
			}

		});
	}

	/**
	 * View: Dashboard - Songs
	 */
	@Test
	public void testSongView() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				Content html = views.html.song.render(new ArrayList<Song>());

				assertThat(contentType(html)).isEqualTo("text/html");

				assertThat(contentAsString(html)).contains("Dashboard - Songs");
			}

		});
	}

	/**
	 * View: Dashboard - User
	 */
	@Test
	public void testUserView() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				Content html = views.html.user.render();

				assertThat(contentType(html)).isEqualTo("text/html");

				assertThat(contentAsString(html)).contains("Dashboard - User");
			}

		});
	}
}
