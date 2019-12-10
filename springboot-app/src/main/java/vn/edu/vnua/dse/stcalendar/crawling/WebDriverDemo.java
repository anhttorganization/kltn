package vn.edu.vnua.dse.stcalendar.crawling;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarApi;
import vn.edu.vnua.dse.stcalendar.model.Semester;



public class WebDriverDemo {
	private static String authorizedCode = "4/RgFdRx1j1sHya2k-NeDzj-kuVQ4xklCyo_5q2sod898diBIPBoYctkgvkqFzKv0BR8y81KP_USiG-6kaO7Ym8w8";
	private static String refreshToken = "1//0g_ly8gCQJiKXCgYIARAAGBASNwF-L9IreFFZLUTYNKrCNb-jq85ycLusGuIqX6FzROASEjloZ3brZlZWoFHZKydH_cJg5eDkwU8";
	
	public static void main(String[] args) throws IOException, ParseException {
//		refreshToken = UserDetailsServiceImpl.getRefreshToken();
		CalendarApi wrapper = new CalendarApi(refreshToken);
		System.out.println(wrapper.getAccessToken());
//		
		Gson gson = new Gson();
		List<GoogleEvent> events = (List<GoogleEvent>) SubjectEventDetails.getEventsFromSchedule("CNP02", new Semester("20191", "hk 2", new Date(2019, 7, 5)));
		for (GoogleEvent googleEvent : events) {
			System.out.println(googleEvent);
		}
//		for (GoogleEvent googleEvent : events) {
//			List<GoogleCalendar> calendars = wrapper.getCalendarList();
//			for (GoogleCalendar calen : calendars) {
//				if(calen.getSummary().contains("New Calendar")) {
//					GoogleEvent event = wrapper.insertEvent(calen.getId(), googleEvent);
//					System.out.println(gson.toJson(event));
//				}
//			}
//		}

	}

}
