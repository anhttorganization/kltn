package vn.edu.vnua.dse.stcalendar.vo;

import java.util.ArrayList;

public class BaseResult<T> {
	public ArrayList<String> getWeekEvents() {
		return weekEvents;
	}

	public void setWeekEvents(ArrayList<String> weekEvents) {
		this.weekEvents = weekEvents;
	}

	boolean status;

	T result;
	
	String message;

	ArrayList<String> weekEvents;
	public BaseResult(boolean status, T result, String message) {
		super();
		this.status = status;
		this.result = result;
		this.message = message;
	}
	
	public BaseResult(boolean status, T result, String message, ArrayList<String> weekEvents) {
		super();
		this.status = status;
		this.result = result;
		this.message = message;
		this.weekEvents = weekEvents;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

	
}
