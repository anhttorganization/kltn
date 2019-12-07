package vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj;

import java.util.Date;

import com.google.gson.annotations.Expose;

public class GoogleDate {
	//instance variable
	@Expose(deserialize = true, serialize = true)
	private Date date;
	
	public GoogleDate(Date date) {
		super();
		this.date = date;
	}

	public Date getdate() {
		return date;
	}

	public void setdate(Date date) {
		this.date = date;
	}
	
}
