package vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj;

import java.util.List;

import com.google.gson.annotations.Expose;

public class GoogleAllDayEvent {
	@Expose(deserialize = true, serialize = false)
	private String id;
	
	@Expose(deserialize = true, serialize = true)
	private String summary;
	
	@Expose(deserialize = true, serialize = true)
	private GoogleDate start;
	
	@Expose(deserialize = true, serialize = true)
	private GoogleDate end;
	
	@Expose(deserialize = true, serialize = true)
	private int sequence;
	
	@Expose(deserialize = true, serialize = true)
	private List<String> recurrence;
	
	@Expose(deserialize = true, serialize = true)
	private String description;
	
	@Expose(deserialize = true, serialize = true)
	private String location;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<String> getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(List<String> recurrence) {
		this.recurrence = recurrence;
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

	public GoogleDate getStart() {
		return start;
	}

	public void setStart(GoogleDate start) {
		this.start = start;
	}

	public GoogleDate getEnd() {
		return end;
	}

	public void setEnd(GoogleDate end) {
		this.end = end;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

}
