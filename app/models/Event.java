package models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
	public boolean setDate(String begin, String end) {
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

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getBegin() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(this.begin.getTime());
	}

	public String getEnd() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(this.end.getTime());
	}
}
