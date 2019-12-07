package vn.edu.vnua.dse.stcalendar.crawling;
//package vn.edu.vnua.dse.stcalendar.crawling;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.util.List;
//
//import com.google.gson.Gson;
//
//import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleCalendar;
//import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleEvent;
//import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.APIWrapper;
//import vn.edu.vnua.dse.calendar.service.UserDetailsServiceImpl;
//
//public class WebDriverDemo {
//	private static String authorizedCode = "4/RgFdRx1j1sHya2k-NeDzj-kuVQ4xklCyo_5q2sod898diBIPBoYctkgvkqFzKv0BR8y81KP_USiG-6kaO7Ym8w8";
//	private static String refreshToken = "1/GLbM4Pv2s-z66g88X_ydvLYvu0mq6smSQeVmwlcpqmw";
//	
//	public static void main(String[] args) throws IOException, ParseException {
////		refreshToken = UserDetailsServiceImpl.getRefreshToken();
//		APIWrapper wrapper = new APIWrapper(refreshToken);
//		System.out.println(wrapper.getAccessToken());
////		
//		Gson gson = new Gson();
//		List<GoogleEvent> events = SubjectEventDetails.getEventsFromSchedule("CNP02", "20182");
//		for (GoogleEvent googleEvent : events) {
//			System.out.println(googleEvent);
//		}
////		for (GoogleEvent googleEvent : events) {
////			List<GoogleCalendar> calendars = wrapper.getCalendarList();
////			for (GoogleCalendar calen : calendars) {
////				if(calen.getSummary().contains("New Calendar")) {
////					GoogleEvent event = wrapper.insertEvent(calen.getId(), googleEvent);
////					System.out.println(gson.toJson(event));
////				}
////			}
////		}
//
//	}
//
//}
