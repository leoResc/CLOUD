package models;

import java.util.Iterator;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Likes extends Model {
	public static Finder<Long, Likes> find = new Finder<Long, Likes>(
			Long.class, Likes.class);

	@Id
	public long id;
	public long userID;
	public long songID;

	public Likes() {
	}

	public void setSongID(long song) {
		if (Song.find.byId(song) != null) {
			this.songID = song;
		}
	}

	public void setUserID(long user) {
		if (User.byId.byId(user) != null) {
			this.userID = user;
		}
	}

	/**
	 * Deletes Like if it exists
	 * 
	 * @param song
	 * @param user
	 */
	public void deleteLike(long song, long user) {

		Likes like = findLike(song, user);

		this.userID = user;
		this.songID = song;

		if (like != null) {
			like.delete();
			Song mySong = Song.find.byId(song);
			mySong.dislike();
		}

	}

	/**
	 * Creates new Like in DB if does not exist yet
	 * 
	 * @param song
	 * @param user
	 */
	public Likes createLike(long song, long user) {

		Likes like = findLike(song, user);

		this.userID = user;
		this.songID = song;

		if (like == null) {
			this.save();
			Song mySong = Song.find.byId(song);
			mySong.like();
		}

		return this;
	}

	/**
	 * Searches for id in the List
	 * 
	 * @param song
	 * @param user
	 * @return null or found Like
	 */
	public Likes findLike(long song, long user) {

		Iterator<Likes> it = Likes.find.all().iterator();
		Likes like;

		while (it.hasNext()) {

			like = it.next();

			if (like.songID == song && like.userID == user) {
				return like;
			}
		}

		return null;
	}

}
