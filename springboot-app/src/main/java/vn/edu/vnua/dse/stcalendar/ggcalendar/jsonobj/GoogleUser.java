package vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj;

import com.google.gson.annotations.Expose;

public class GoogleUser {
	@Expose(deserialize = true, serialize = true)
	private String id;
	@Expose(deserialize = true, serialize = true)
	private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
