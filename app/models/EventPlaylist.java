package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

/**
 * The class EventPlaylist represents the relationship between an event and it's
 * associated playlists.
 * 
 * @author Philipp
 *
 */
@Entity
public class EventPlaylist extends Model {

	@Id
	long eventID;
	long playlistID;

	public static Finder<Long, Long> find = new Finder<Long, Long>(Long.class,
			Long.class);

	public EventPlaylist(long eventID, long playlistID) {
		this.eventID = eventID;
		this.playlistID = playlistID;
	}
}
