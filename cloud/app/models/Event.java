package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Event extends Model {

	@Id
	public long id;
	public String name;
	public String password;
	public String description;
	public Date begin;
	public Date end;
	
	public Event() {
		super();
	}
}
