package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.common.collect.Lists;

import play.Logger;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;


@Entity
public class Playlist extends Model {
	public static Finder<Long, Playlist> find = new Finder<Long, Playlist>(
			Long.class, Playlist.class);
	
	@OneToMany(mappedBy = "playlist")
	public ArrayList<Song> songListe;
	public int numSongs=0;
	public String songIds="";
	public double duration;
	public String name;
	@Id
	public long id;
	
	public Playlist(){
		songListe = new ArrayList<Song>();
	}

	public Playlist(String name, ArrayList<Song> songs, double duration) {
		this.name = name;
		this.songListe = songs;
		this.duration = duration;
		this.numSongs = songs.size();
		listToString(songs);
	}
	
	public void listToString(ArrayList<Song> songs){
		for(Song song : songs){
			songIds += song.id +" ";
		}
	}
	
	public void updateListOfSongs(){
		stringToList(this.songIds);
	}
	
	/**
	 * Creates List of songs of the String with their IDs
	 * @param ids
	 * @return Array of songs
	 */
	private ArrayList<Song> stringToList(String ids) {
		
		String buffer = "";
		ArrayList<Song> songs = new ArrayList<Song>();
		
		for(int i=0; i < ids.length(); i++){
			
			Logger.info("-->"+ids.charAt(i));
			
			if(ids.charAt(i) == ' '){
				songs.add(Song.find.byId(Long.valueOf(buffer)));
				Logger.info("new song -->"+buffer);
				buffer="";
				numSongs ++;
			}
			else {
				buffer += (ids.charAt(i));
			}
		}
		
		songListe = songs;
		
		return songs;
	}
	

	public static List<Song> deleteSameSongs(List<Song> allSongs,
			List<Song> playlistSongs) {
		List<Song> songsClear = new ArrayList<Song>();

		Iterator<Song> songs = allSongs.listIterator();
		Iterator<Song> songsPlaylist = playlistSongs.listIterator();

		while (songsPlaylist.hasNext()) {

			Song songInList = songsPlaylist.next();

			while (songs.hasNext()) {

				Song songToCheck = songs.next();

				if (songInList.id != songToCheck.id) {
					songsClear.add(songToCheck);
				}
			}
			songs = songsClear.listIterator();
			songsClear.clear();
		}

		return Lists.newArrayList(songs);
	}

}
