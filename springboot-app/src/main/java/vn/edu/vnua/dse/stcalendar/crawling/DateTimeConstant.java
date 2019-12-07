package vn.edu.vnua.dse.stcalendar.crawling;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public final class DateTimeConstant {
	public static final Map<Integer, String> STARTTIME = initStartTime();

	private static Map<Integer, String> initStartTime() {
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "07:00:00");
		map.put(2, "07:55:00");
		map.put(3, "08:50:00");
		map.put(4, "09:55:00");
		map.put(5, "10:50:00");
		map.put(6, "12:45:00");
		map.put(7, "13:40:00");
		map.put(8, "14:35:00");
		map.put(9, "15:40:00");
		map.put(10, "16:35:00");
		map.put(11, "18:00:00");
		map.put(12, "18:55:00");
		map.put(13, "19:50:00");
		return Collections.unmodifiableMap(map);
	}
	
	public static final Map<Integer, String> ENDTIME = initEndTime();

	private static Map<Integer, String> initEndTime() {
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "07:50:00");
		map.put(2, "08:45:00");
		map.put(3, "09:40:00");
		map.put(4, "10:45:00");
		map.put(5, "11:40:00");
		map.put(6, "13:35:00");
		map.put(7, "14:30:00");
		map.put(8, "15:25:00");
		map.put(9, "16:30:00");
		map.put(10, "17:25:00");
		map.put(11, "18:50:00");
		map.put(12, "19:45:00");
		map.put(13, "20:40:00");
		return Collections.unmodifiableMap(map);
	}
	
	public static final String STARTSEMESTERDATE_STR = "31/12/2018";
	
	public static final Date STARTSEMESTERDATE = initStartSemesterDate();
	
	private static Date initStartSemesterDate() {
		//String sDate="31/12/2018";  
	    try {
			Date date=new SimpleDateFormat("dd/MM/yyyy").parse(STARTSEMESTERDATE_STR);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    return null;   
	}
	
}



