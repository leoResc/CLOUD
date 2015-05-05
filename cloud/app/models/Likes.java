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
import play.db.ebean.Model.Finder;
import play.mvc.Http.MultipartFormData.FilePart;

@Entity
public class Likes extends Model {
	public long userID;
	public long songID;

	public long id;

	public static Finder<Long, Likes> find = new Finder<Long, Likes>(
			Long.class, Likes.class);

	public Likes() {
		super();
	}

	public void setID() {

		if (this.userID >= 0 && this.songID >= 0) {

			String uid = String.valueOf(this.userID);
			String sid = String.valueOf(this.songID);
			String tid = uid + sid;
			this.id = Long.valueOf(tid);
		}
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
	
	public void deleteLike(long song, long user) {
		
		this.setSongID(song);
		this.setUserID(user);
		this.setID();
		
		Likes like = Likes.find.byId(this.id);
		
		if(like != null){
			this.delete();
			Song mySong = Song.find.byId(song);
			mySong.dislike();
		}
		
	}

	public void createLike(long song, long user) {
		
		this.setSongID(song);
		this.setUserID(user);
		this.setID();
		
		Likes like = Likes.find.byId(this.id);
		
		if(like == null){
			this.save();
			Song mySong = Song.find.byId(song);
			mySong.like();
		}
		
	}

}
