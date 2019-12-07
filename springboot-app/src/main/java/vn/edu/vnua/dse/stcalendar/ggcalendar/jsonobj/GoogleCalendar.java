package vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj;

import com.google.gson.annotations.Expose;

public class GoogleCalendar {
	@Expose(deserialize = true, serialize = false)
	private String id;
	@Expose(deserialize = true, serialize = true)
	private String summary;
	@Expose(deserialize = true, serialize = true)
	public String timeZone;

	public GoogleCalendar() {
		super();
	}

//	public GoogleCalendar(String id, String summary, String timeZone) {
//		this.id = id;
//		this.summary = summary;
//		this.timeZone = timeZone;
//	}

	public GoogleCalendar(String summary) {
		super();
		this.summary = summary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

}
