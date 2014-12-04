package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Song extends Model{
	
	public String title;
	public String artist;
	public String genre;
	public double duration;
	public int user_likes;
	@Id
	public long id;
	
	public Song(String tit, String art, String gen, double dur, int lik){
		this.title = tit;
		this.artist = art;
		this.genre = gen;
		this.duration = dur;
		this.user_likes = lik;

	}
}
