package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Event extends Model {

	public static Finder<Long, Event> find = new Finder<Long, Event>(
			Long.class, Event.class);
	@Id
	public long id;
	public String name;
	public String password;
	public String description;
	public Date begin;
	public Date end;
	
	public Event() {
	}
}
