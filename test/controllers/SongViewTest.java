package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import static play.test.Helpers.status;

import org.junit.Test;

import play.mvc.Result;
import play.test.FakeRequest;
import play.test.Helpers;

public class SongViewTest {
	
	/**
	 * View: Dashboard - Songs with admin session
	 */
	@Test
	public void testDashboardViewAsAdmin() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				FakeRequest fakeRequest = new FakeRequest("GET", "/songs");
				fakeRequest.withSession("user", "admin");
				Result result = Helpers.callAction(
						controllers.routes.ref.Dashboard.getSong(),
						fakeRequest);

				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentType(result)).isEqualTo("text/html");
				assertThat(charset(result)).isEqualTo("utf-8");
				assertThat(contentAsString(result)).contains(
						"Dashboard - Songs");
			}

		});
	}

	/**
	 * View: Dashboard - Overview with casual user session
	 */
	@Test
	public void testDashboardViewAsUser() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				FakeRequest fakeRequest = new FakeRequest("GET", "/songs");
				fakeRequest.withSession("user", "john");
				Result result = Helpers.callAction(
						controllers.routes.ref.Dashboard.getSong(),
						fakeRequest);

				assertThat(status(result)).isEqualTo(401);
			}

		});
	}

	/**
	 * View: Dashboard - Overview without session
	 */
	@Test
	public void testDashboardViewWithoutSession() {

		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				FakeRequest fakeRequest = new FakeRequest("GET", "/songs");
				Result result = Helpers.callAction(
						controllers.routes.ref.Dashboard.getSong(),
						fakeRequest);

				assertThat(status(result)).isEqualTo(303);
			}

		});
	}

}
