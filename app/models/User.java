package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class User extends Model {

	@Id
	public long id;
	public String username;
	public static Finder<String, User> find = new Finder<String, User>(
			String.class, User.class);
	public static Finder<Long, User> byId = new Finder<Long, User>(Long.class,
			User.class);

	public User() {
	}
}
