package models;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import play.db.ebean.Model;

@Entity
public class Playlist extends Model
{
	
	public ArrayList<Song>  liste;
	public double duration;
	@Id
	public String name;

}
