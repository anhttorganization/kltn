//package vn.edu.vnua.dse.stcalendar.crawling;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import antlr.collections.List;
//import vn.edu.vnua.dse.calendar.repository.SemesterRepository;
//import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.ExGoogleEvent;
//import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleCalendar;
//import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleCalendarList;
//import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.APIWrapper;
//
//public class Test {
//	
//	@Autowired
//	public static Date findDay(Date startSemester, int week, int day) {
//		//set date
//		Calendar calen = Calendar.getInstance();
//		calen.setTime(startSemester);
//		//compute
//		calen.set(Calendar.DAY_OF_MONTH, calen.get(Calendar.DAY_OF_MONTH) + (week - 1) * 7 + day);
//
//		return calen.getTime();
//	}
//
//	public static void main(String[] args) throws ParseException, IOException {
////		APIWrapper apiWrapper = new APIWrapper();
//////		List<	apiWrapper.getCalendarList("");
////	    String accessToken =	apiWrapper.getAccessToken("1/C2AbT5TL2OUAcTvcKv6m10nGleKxfhsLb5olyr4gq3o");
////		System.out.println(accessToken);
//		// set date
////				Calendar calen = Calendar.getInstance();
//				// compute
//				ArrayList<String> events = new ArrayList<>();
//				for (int i = 0; i < 20; i++) {
//					
//					Calendar calen = Calendar.getInstance();
//					// compute
//					calen.set(Calendar.DAY_OF_MONTH, calen.get(Calendar.DAY_OF_MONTH) + i * 7 + 0);//thêm vào mỗi thứ 2
//					Date start = calen.getTime();
//					calen.set(Calendar.DAY_OF_MONTH, calen.get(Calendar.DAY_OF_MONTH) + 1);
//					Date end = calen.getTime();
//					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//					String startStr = formatter.format(start);
//					String endStr = formatter.format(end);
//					
//					String event = String.format(ExGoogleEvent.allDayEvent, "Tuần " + (i + 1), startStr, endStr, "Tuần " + (i + 1),	"Học viện Nông nghiệp Việt Nam", "default");
//					events.add(event);
//				}
//				System.out.println("hello");
//				for (String string : events) {
//					System.out.println(string);
//				}
//			
//	}
//}
