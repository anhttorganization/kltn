package vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj;

public class ExGoogleEvent extends GoogleEvent{
	public static final String event = 	//start, end, description, location, visibility
			"{\r\n" + 
			"  \"summary\": \"%s\",\r\n" + 
			"  \"start\": {\r\n" + 
			"    \"dateTime\": \"%s\",\r\n" + 
			"    \"timeZone\": \"Asia/Ho_Chi_Minh\"\r\n" + 
			"  },\r\n" + 
			"  \"end\": {\r\n" + 
			"    \"dateTime\": \"%s\",\r\n" + 
			"    \"timeZone\": \"Asia/Ho_Chi_Minh\"\r\n" + 
			"  },\r\n" + 
			"  \"sequence\": 0,\r\n" + 
			"  \"description\": \"%s\",\r\n" + 
			"  \"location\": \"%s\"\r\n" + 
			"}";
	
	public static final String allDayEvent = //start, end, description, location, visibility
			"{\r\n" + 
			"  \"summary\": \"%s\",\r\n" + 
			"  \"start\": {\r\n" + 
			"    \"date\": \"%s\"\r\n" + 
			"  },\r\n" + 
			"  \"end\": {\r\n" + 
			"    \"date\": \"%s\"\r\n" + 
			"  },\r\n" + 
			"  \"sequence\": 0,\r\n" + 
			"  \"description\": \"%s\",\r\n" + 
			"  \"location\": \"%s\"\r\n" + 
			"}";
	
	public static final String weekStudyEvent =  //start, end, description, visibility
			"{\"summary\":\"%s\","
			+ "\"start\":{"
			+ "\"date\":\"%s\"},"
			+ "\"end\":{\"date\":\"%s\"},"
			+ "\"sequence\":0,"
			+ "\"description\":\"%s\","
			+ " \"visibility\": \"%s\"" + 
			"}";
}
