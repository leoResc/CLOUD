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

public class LoginViewTest {

	/**
	 * View: Login
	 */
	@Test
	public void testLoginView() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {

				FakeRequest fakeRequest = new FakeRequest("GET", "/login");
				Result result = Helpers.callAction(
						controllers.routes.ref.Application.getLogin(),
						fakeRequest);

				assertThat(status(result)).isEqualTo(200);
				assertThat(contentType(result)).isEqualTo("text/html");
				assertThat(charset(result)).isEqualTo("utf-8");
				assertThat(contentAsString(result))
						.contains("Welcome to Cloud");
			}

		});

	}

}
