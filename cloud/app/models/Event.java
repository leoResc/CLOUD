package models;

import java.util.Calendar;

public class Event {

	public String name;
	public String password;
	public String description;
	public Calendar begin;
	public Calendar end;
	
	public Event()  {
		this.name = "Test";
		this.password = "aligator7";
		this.description = "Test-Party";
		
		this.begin = Calendar.getInstance();
		this.begin.set(2014, 10, 26, 12, 24);

		this.end = Calendar.getInstance();
		this.end.set(2014, 10, 26, 13, 24);
	}

	public String getBegin() {
		return this.begin.getTime().toString();
	}
	
	public void setBegin(Calendar begin) {
		this.begin = begin;
	}
	
}
