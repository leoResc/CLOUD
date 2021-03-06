package models;

import static play.mvc.Controller.flash;
import helliker.id3.CorruptHeaderException;
import helliker.id3.ID3v2FormatException;
import helliker.id3.MP3File;
import helliker.id3.NoMPEGFramesException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;

import play.db.ebean.Model;
import play.mvc.Http.MultipartFormData.FilePart;

@Entity
public class Song extends Model implements Comparable<Song> {

	@Id
	public long id;
	public String title;
	public String artist;
	public String genre;
	public long duration;
	public int likes;
	public String filename;

	public static Finder<Long, Song> find = new Finder<Long, Song>(Long.class,
			Song.class);
	public static File storageLocation = new File("../songs/");

	public Song(String title, String artist, String genre, long duration,
			int likes) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.duration = duration;
		this.likes = likes;
		this.filename = "";
	}

	public void dislike() {
		if (this.likes >= 1) {
			this.likes--;
			this.update();
		}
	}

	public void like() {
		this.likes++;
		this.update();
	}

	/**
	 * This method does the validation and saving of the songs.
	 * 
	 * @param files
	 *            List of files which shall be processed
	 */
	public static void uploadSong(List<FilePart> files) {

		int countFiles = files.size();

		if (files.isEmpty()) {
			flash("error", "No files were selected for upload.");
			return;
		}
		for (FilePart filePart : files) {
			String contentType = filePart.getContentType();

			if (contentType.equals("audio/mpeg")
					|| contentType.equals("audio/mp3")) {

				File file = filePart.getFile();

				// move file to storage location and delete temp file
				try {

					Song song = getID3Tags(file);

					if (song != null) {
						// rename file according to guidelines
						song.filename += cleanUpString(song.artist + "-"
								+ song.title + ".mp3");
						FileUtils.moveFile(file, new File(storageLocation,
								song.filename));
						// save in database
						song.save();
						continue;
					}
					countFiles--;
					flash("error",
							"One or more songs were missing the id3 tags artist, title and genre ...");
				} catch (FileExistsException e) {
					flash("uploadWarning",
							"One or more songs have already been uploaded before ...");
					countFiles--;
				} catch (IOException e) {
					flash("error", "Internal Server Error");
					countFiles--;
				} finally {
					filePart.getFile().delete();
				}
			} else {
				flash("fileError",
						"One or more files were not of file format mp3.");
				countFiles--;
				filePart.getFile().delete();
			}
		}
		if (countFiles > 0) {
			flash("success", String.format(
					"You uploaded successfully %d %s to the database.",
					countFiles, (countFiles == 1) ? "song" : "songs"));
		}
		ShellCommand comand = new ShellCommand("mpc update");
		comand.executeShellCommand();
	}

	/**
	 * This method returns the id3 tags artist, title and genre of a given mp3
	 * file.
	 * 
	 * @param file
	 *            The file which shall be processed.
	 * @return Returns a object of Song if all information was given, otherwise
	 *         null
	 */
	public static Song getID3Tags(File file) {
		try {
			MP3File mp3 = new MP3File(file);
			String artist = mp3.getArtist();
			String title = mp3.getTitle();
			String genre = mp3.getGenre();
			long duration = mp3.getPlayingTime();

			if (artist.equals("") || title.equals("") || genre.equals("")) {
				return null;
			}
			return new Song(title, artist, genre, duration, 0);

		} catch (NoMPEGFramesException | ID3v2FormatException
				| CorruptHeaderException | IOException e) {
			return null;
		}
	}

	/**
	 * This method deletes a given song on the file system and in the database.
	 * 
	 * @param song
	 */
	public static void deleteSong(long id) {
		Song song = Song.find.byId(id);
		File songFile = new File(storageLocation + "/" + song.filename);

		if (songFile.delete()) {
			song.delete();
			return;
		}
		flash("fileError", "The selected file could not be deleted ...");
	}

	@Override
	public int compareTo(Song o) {
		return o.likes - this.likes;
	}

	public static boolean songAlreadyLiked(long userID, long songID) {
		Likes likes = new Likes();

		if (likes.findLike(songID, userID) == null) {
			return false;
		}
		return true;
	}

	/**
	 * This method removes all symbols that are not allowed for file names.
	 * 
	 * @param string
	 *            String which shall be cleaned up
	 * @return returns cleaned up string
	 */
	private static String cleanUpString(String string) {
		string = string.replaceAll("\\s", "");
		string = string.replaceAll(";", "");
		string = string.replaceAll("/", "");
		string = string.replaceAll("\\\\", "");
		string = string.replaceAll("\\*", "");
		string = string.replaceAll("'", "");
		string = string.replaceAll("&", "");
		return string;
	}

}