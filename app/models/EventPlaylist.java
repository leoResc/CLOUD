package models;

import java.util.ArrayList;
import java.util.List;

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
	long id;
	long eventID;
	long playlistID;
	
	public static Finder<Long, EventPlaylist> find = new Finder<Long, EventPlaylist>(Long.class,
			EventPlaylist.class);

	public EventPlaylist(long eventID, long playlistID) {
		this.eventID = eventID;
		this.playlistID = playlistID;
	}
	
	public static List<Playlist> getPlaylistsForEvent(long eventID) {
		List<EventPlaylist> eventPlaylists = EventPlaylist.find.all();
		List<Playlist> playlists = new ArrayList<Playlist>();
		
		// get all playlists for the given event
		for (EventPlaylist eventPlaylist : eventPlaylists) {
			if (eventPlaylist.eventID == eventID) {
				playlists.add(Playlist.find.byId(eventPlaylist.playlistID));
			}
		}
		return playlists;
	}
}
