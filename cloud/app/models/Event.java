package models;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Event extends Model {

	@Id
	public String id;
	public String name;
	public String password;
	public String description;
	public Calendar begin;
	public Calendar end;
}
