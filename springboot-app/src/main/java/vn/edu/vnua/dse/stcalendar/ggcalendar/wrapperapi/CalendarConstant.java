package vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi;

public final class CalendarConstant {
	public static final String TIME_ZONE = "Asia/Ho_Chi_Minh";

	public static final String RDATE = "RDATE;VALUE=DATE:%s";
	public static final String EXDATE ="EXDATE;TZID=%s:%s";
	public static final String RRULE_WEEKLY_COUNT = "RRULE:FREQ=WEEKLY;COUNT=%s";

//	public static final String CLIEN_ID = "230214211786-v7jupfhbqhn13igto95equlqd7ncg06b.apps.googleusercontent.com";
//
//	public static final String CLIENT_SECRET = "QXiTgZVFuM777ZKEHXQpHf7j";
//
//	public static final String AUTH_REDIRECT_URL = "http://localhost:8081/ScheduleAndCalendar/authorization";
//
//	public static final String SCOPE = "https://www.googleapis.com/auth/calendar https://www.googleapis.com/auth/userinfo.email";
	
	public static final String AUTH_URL = 
		"https://accounts.google.com/o/oauth2/v2/auth?scope=%s" 
		+ "&redirect_uri=%s"
		+ "&response_type=code" 
		+ "&client_id=%s" 
		+ "&access_type=offline" 
		+ "&include_granted_scopes=true"
		+ "&state=state_parameter_passthrough_value";								
	
	public static final String AUTH_URL_LOGIN_HINT = 
		"https://accounts.google.com/o/oauth2/v2/auth?scope=%s" 
		+ "&redirect_uri=%s"
		+ "&response_type=code" 
		+ "&client_id=%s" 
		+ "&access_type=offline" 			
		+ "&include_granted_scopes=true"
		+ "&state=state_parameter_passthrough_value" 
		+ "&login_hint=%s";
}


