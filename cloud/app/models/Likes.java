package models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import play.Logger;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.mvc.Http.MultipartFormData.FilePart;

@Entity
public class Likes extends Model {
	public long userID;
	public long songID;

	@Id
	public long id;

	public static Finder<Long, Likes> find = new Finder<Long, Likes>(
			Long.class, Likes.class);

	public Likes() {
		super();
	}

	/**
	 * Creates "fake" ID for searching in DB for Likes
	 */
	/*
	 * public void setID() {
	 * 
	 * if (this.userID >= 0 && this.songID >= 0) {
	 * 
	 * String uid = String.valueOf(this.userID); String sid =
	 * String.valueOf(this.songID); String tid = uid + sid; this.id =
	 * Long.valueOf(tid); } }
	 */

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
			Logger.info("Like will be deleted: "+like.songID);
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
	public void createLike(long song, long user) {

		Likes like = findLike(song, user);
		
		this.userID = user;
		this.songID = song;

		if (like == null) {
			Logger.info("Like abgeschickt : "+song);
			this.save();
			Song mySong = Song.find.byId(song);
			mySong.like();
		}

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
		
		Logger.info("NOT FOUND song: "+ song + ", user: "+user);

		return null;
	}

}
