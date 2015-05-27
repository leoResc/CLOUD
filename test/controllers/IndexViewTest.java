package controllers;
import static org.fest.assertions.Assertions.assertThat;
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

public class IndexViewTest {

	/**
	 * View: Index with casual user session
	 */
	@Test
	public void testIndexView() {

		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				// with casual user session
				FakeRequest fakeRequest = new FakeRequest("GET", "/");
				fakeRequest.withSession("user", "john");
				Result result = Helpers.callAction(
						controllers.routes.ref.Application.getIndex(),
						fakeRequest);

				assertThat(status(result)).isEqualTo(200);
				assertThat(contentType(result)).isEqualTo("text/html");
				assertThat(charset(result)).isEqualTo("utf-8");
				assertThat(contentAsString(result)).contains("CLOUD");
			}

		});

	}

	/**
	 * View: Index without session
	 */
	@Test
	public void testIndexViewWithoutSession() {

		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				FakeRequest fakeRequest = new FakeRequest("GET", "/");
				Result result = Helpers.callAction(
						controllers.routes.ref.Application.getIndex(),
						fakeRequest);

				assertThat(status(result)).isEqualTo(303);
			}

		});

	}

}
