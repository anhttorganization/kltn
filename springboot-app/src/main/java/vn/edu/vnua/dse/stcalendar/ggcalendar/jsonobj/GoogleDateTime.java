package vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj;

import java.util.Date;

import com.google.gson.annotations.Expose;

public class GoogleDateTime {
	//instance variable
	@Expose(deserialize = true, serialize = true)
	private Date dateTime;
	
	@Expose(deserialize = true, serialize = true)
	private String timeZone;
	
	
	public GoogleDateTime(Date dateTime, String timeZone) {
		super();
		this.dateTime = dateTime;
		this.timeZone = timeZone;
	}

	//properties
	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}


	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	
}
