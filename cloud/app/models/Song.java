package models;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;

import play.Logger;
import play.db.ebean.Model;
import play.mvc.Http.MultipartFormData.FilePart;

@Entity
public class Song extends Model {

	@Id
	public long id;
	public String title;
	public String artist;
	public String genre;
	public long duration;
	public int user_likes;

	public static Finder<Long, Song> find = new Finder<Long, Song>(Long.class,
			Song.class);
	public static File storageLocation = new File("C:/songs");

	public Song(String title, String artist, String genre, long duration,
			int likes) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.duration = duration;
		this.user_likes = likes;
	}

	public static void uploadSong(List<FilePart> files) {

		int countFiles = files.size();

		if (files.isEmpty()) {
			play.mvc.Controller.flash("fileError",
					"No files were selected for upload.");
			return;
		}
		for (FilePart filePart : files) {
			String contentType = filePart.getContentType();

			if (contentType.equals("audio/mpeg")) {

				File file = filePart.getFile();
				String fileName = filePart.getFilename();

				try {
					FileUtils.moveFile(file,
							new File(storageLocation, fileName));
				} catch (FileExistsException e) {
					play.mvc.Controller.flash("fileError",
							"One or more songs existed already.");
					countFiles--;
				} catch (IOException e) {
					play.mvc.Controller.flash("fileError",
							"Internal Server Error");
					countFiles--;
				} finally {
					filePart.getFile().delete();
				}
				continue;
			}
			countFiles--;
		}
		play.mvc.Controller.flash("fileSuccess", String.format(
				"You uploaded successfully %d %s to the database.", countFiles,
				(countFiles == 1) ? " song" : " songs"));
		Logger.info(String.format(
				"You uploaded successfully %d %s to the database.", countFiles,
				(countFiles == 1) ? "song" : "songs"));
	}
}
