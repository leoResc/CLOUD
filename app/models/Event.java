package models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Event extends Model {

	public static Finder<Long, Event> find = new Finder<Long, Event>(
			Long.class, Event.class);

	@Id
	public long id;
	public String name;
	public String password;
	public String description;
	public Calendar begin;
	public Calendar end;

	public Event() {
	}

	/**
	 * This method parses two given dates.
	 * 
	 * @param begin
	 *            Beginning date in the format dd/mm/yyyy
	 * @param end
	 *            Ending date in the format dd/mm/yyyy
	 * @return true if everything went fine; false if an exception occured
	 */
	public int setDate(String begin, String end) {
		try {
			int day = Integer.valueOf(begin.substring(0, 2));
			int month = Integer.valueOf(begin.substring(3, 5));
			int year = Integer.valueOf(begin.substring(6, 10));

			Calendar calendarBegin = new GregorianCalendar();
			calendarBegin.set(year, month - 1, day);
			this.begin = calendarBegin;

			day = Integer.valueOf(end.substring(0, 2));
			month = Integer.valueOf(end.substring(3, 5));
			year = Integer.valueOf(end.substring(6, 10));

			Calendar calendarEnd = new GregorianCalendar();
			calendarEnd.set(year, month - 1, day);
			this.end = calendarEnd;
			if (calendarEnd.before(calendarBegin)) {
				return -1;
			}
			if(Event.findByBegin(calendarBegin) != null){
				return -3;
			}
			return 0;
		} catch (Exception e) {
			return -2;
		}
	}
	
	public static Event findByBegin(Calendar date) {
		
		List<Event> events = Event.find.all();
		
		for(Event event : events){
			
			if(event.begin == date){
				return event;
			}
			
		}
		 
		return null;
	}

	public String getBegin() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(this.begin.getTime());
	}

	public String getEnd() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(this.end.getTime());
	}

	public static Event getCurrentEvent() {
		Calendar currentTime = Calendar.getInstance();
		List<Event> events = Event.find.all();
		for (Event event : events) {
			if ((currentTime.after(event.begin))
					&& (currentTime.before(event.end))) {
				return event;
			}
		}
		return null;
	}
}
