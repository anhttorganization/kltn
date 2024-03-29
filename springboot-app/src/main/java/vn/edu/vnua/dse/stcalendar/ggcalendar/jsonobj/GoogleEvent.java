package vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj;

import java.util.List;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleEvent {
	
	@Expose(deserialize = true, serialize = false)
	private String id;
	
	@Expose(deserialize = true, serialize = true)
	private String summary;
	
	@Expose(deserialize = true, serialize = true)
	private GoogleDateTime start;
	
	@Expose(deserialize = true, serialize = true)
	private GoogleDateTime end;
	
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

	public GoogleDateTime getStart() {
		return start;
	}

	public void setStart(GoogleDateTime start) {
		this.start = start;
	}

	public GoogleDateTime getEnd() {
		return end;
	}

	public void setEnd(GoogleDateTime end) {
		this.end = end;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

}
