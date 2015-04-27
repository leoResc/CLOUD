package controllers;

import java.util.List;






import org.h2.command.dml.Delete;

import play.*;
import models.Song;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import scala.util.parsing.json.JSON;
import views.html.*;

public class Dashboard extends Controller {

	public static Result getDashboard() {
		return ok(dashboard.render());		
	}
	
	public static Result getEvent() {
		return ok(event.render());
	}
	
	public static Result getPlaylist() {
		return ok(playlist.render());
	}
	
	public static Result getSong() {
		List<Song> songs = Song.find.all();
		return ok(song.render(songs));
	}
	
	public static Result getUser() {
		return ok(user.render());
	}
	
	public static Result uploadSong() {
		MultipartFormData body = request().body().asMultipartFormData();
		List<MultipartFormData.FilePart> files = body.getFiles();
		Song.uploadSong(files);
		return ok();		
	}
	
	public static Result deleteSong(long id) {
		Song.deleteSong(id);
		List<Song> songs = Song.find.all();
		return ok(Json.toJson(songs));
	}
}
