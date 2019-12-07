package vn.edu.vnua.dse.stcalendar.vo;

import org.springframework.web.multipart.MultipartFile;

public class ImportCreate {
	  private MultipartFile multipartFile;
	  private String calendarId;
	  
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	public String getCalendarId() {
		return calendarId;
	}
	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}
	  
}
