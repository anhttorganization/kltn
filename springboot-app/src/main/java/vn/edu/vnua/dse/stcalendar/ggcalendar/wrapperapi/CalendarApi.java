package vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Form;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import vn.edu.vnua.dse.stcalendar.common.AppConstant;
import vn.edu.vnua.dse.stcalendar.common.AppUtils;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleCalendar;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleCalendarList;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleDateTime;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleEventList;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleToken;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleUser;

@Component
public class CalendarApi {
	private static String clientID;// = CalendarConstant.CLIEN_ID;

	private static String clientSecret;// = CalendarConstant.CLIENT_SECRET;

	private static String Scope;// = CalendarConstant.SCOPE;

	private static String Redirect_Uri;// = CalendarConstant.AUTH_REDIRECT_URL;

	private static String AuthUrl = CalendarConstant.AUTH_URL;

	private static String AuthUrlLoginHint = CalendarConstant.AUTH_URL_LOGIN_HINT;

	private String refreshToken;

	private String accessToken;
	private Gson gson;

	public CalendarApi() throws FileNotFoundException, IOException {
		Properties prop = AppUtils.MyProperties(AppConstant.CALENDAR_APP_PRO);

		clientID = prop.getProperty("calenProject.clientID", null);
		clientSecret = prop.getProperty("calenProject.clientSecret");
		Redirect_Uri = prop.getProperty("calenProject.authorizedRedirectURIs").trim();
		Scope = prop.getProperty("calenProject.scope");
		System.out.println(prop.getProperty("calenProject.clientID"));
		gson = new Gson();
	}

	public CalendarApi(String refreshToken) throws IOException {
		Properties prop = AppUtils.MyProperties(AppConstant.CALENDAR_APP_PRO);

		clientID = prop.getProperty("calenProject.clientID", null);
		clientSecret = prop.getProperty("calenProject.clientSecret");
		Redirect_Uri = prop.getProperty("calenProject.authorizedRedirectURIs");
		Scope = prop.getProperty("calenProject.scope");

		gson = new GsonBuilder().create();
		this.refreshToken = refreshToken;
		this.accessToken = getAccessToken(refreshToken);
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	// OAUTH
	public String getAccessToken() {
		return this.accessToken;
	}

	public static String getAuthUrl() {

		String FinalAuthUrl = String.format(AuthUrl, Scope, Redirect_Uri, clientID);

		return FinalAuthUrl;
	}

	public static String getAuthUrl(String gmail) {
		String FinalAuthUrl = String.format(AuthUrlLoginHint, Scope, Redirect_Uri, clientID, gmail);

		return FinalAuthUrl;
	}

	public GoogleToken getRefreshToken(String authorizedCode) {
		String url = "https://www.googleapis.com/oauth2/v4/token";
		List<NameValuePair> forms = Form.form().add("code", authorizedCode).add("client_id", clientID)
				.add("client_secret", clientSecret).add("redirect_uri", Redirect_Uri)
				.add("grant_type", "authorization_code").build();

		String result = APIService.PostResult(url, forms);

		GoogleToken token = gson.fromJson(result, GoogleToken.class);

		return token;
	}

	public String getAccessToken(String refresh_token) {
		String url = "https://www.googleapis.com/oauth2/v4/token";
		List<NameValuePair> forms = Form.form()
				.add("refresh_token", refresh_token)
				.add("client_id", clientID)
				.add("client_secret", clientSecret)
				.add("grant_type", "refresh_token").build();

		String result = APIService.PostResult(url, forms);
		System.out.println("-------------------------------");
		System.out.println(result);
		if (result != null) {
			GoogleToken token = gson.fromJson(result, GoogleToken.class);
			if (token != null) {

				return token.getAccess_token();
			} else {
				return null;
			}
		}
		return null;
	}

	// CALENDAR
	public List<GoogleCalendar> getCalendarList() {
		String url = "https://www.googleapis.com/calendar/v3/users/me/calendarList" // url
				+ "?fields=items(id,summary,timeZone)"; // param

		String result = APIService.GetResult(url, accessToken);
		GoogleCalendarList calendarList = gson.fromJson(result, GoogleCalendarList.class); // convert json string to
																							// java obj

		return calendarList.getItems();
	}

	public GoogleCalendar findCalendarInCalendarList(String calendarId) {
		List<GoogleCalendar> googleCalendars = getCalendarList();

		for (GoogleCalendar googleCalendar : googleCalendars) {
			if (googleCalendar.getId().equals(calendarId)) {
				return googleCalendar;
			}
		}

		return null;
	}

	public GoogleCalendar insertCalendar(String CalendarName, String timeZone) {
		String url = "https://www.googleapis.com/calendar/v3/calendars/";

		// Create a new calendar, with description
		GoogleCalendar newCalendar = new GoogleCalendar();
		newCalendar.setSummary(CalendarName);
		newCalendar.setTimeZone(timeZone);

		// convert calendar to json string
		gson = getExposeOnlyGson();
		String calendarGson = gson.toJson(newCalendar);

		// post json and get result
		String result = APIService.PostResult(url, accessToken, calendarGson);
		newCalendar = gson.fromJson(result, GoogleCalendar.class);

		return newCalendar;
	}

	public GoogleCalendar updateCalendar(String calendarID, String newName) {
		String url = "https://www.googleapis.com/calendar/v3/calendars/%s";
		url = String.format(url, calendarID);

		// Create a new calendar, with description
		GoogleCalendar newCalendar = new GoogleCalendar();
		newCalendar.setSummary(newName);

		// convert calendar to json string
		gson = getExposeOnlyGson();
		String calendarGson = gson.toJson(newCalendar);

		// post json and get result
		String result = APIService.PutResult(url, accessToken, calendarGson);
		newCalendar = gson.fromJson(result, GoogleCalendar.class);

		return newCalendar;
	}

	public GoogleCalendar getCalendar(String calendarID) {
		String url = "https://www.googleapis.com/calendar/v3/calendars/%s";
		url = String.format(url, calendarID);

		//
		try {
			String result = APIService.GetResult(url, accessToken);
			GoogleCalendar calendar = gson.fromJson(result, GoogleCalendar.class);

			return calendar;
		} catch (Exception e) {
			return null;
		}

	}

	// GSON
	private Gson getExposeOnlyGson() {
		return new GsonBuilder().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().create();
	}

	private Gson getDateTimeParseGson() {
		return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.000+07:00").disableHtmlEscaping()
				.excludeFieldsWithoutExposeAnnotation().create();
	}

//	private Gson getDisableHtmlEscapingGson() {
//		return new GsonBuilder().disableHtmlEscaping().create();
//	}

	// EVENT
	// list
	public List<GoogleEvent> getEventList(String calendarID) {
		String url = "https://www.googleapis.com/calendar/v3/calendars/%s/events";
		url = String.format(url, calendarID);

		String result = APIService.GetResult(url, accessToken);
		GoogleEventList eventList = gson.fromJson(result, GoogleEventList.class);

		return eventList.getItems();
	}

	//
	public GoogleEvent insertEvent(String calendarId, String summary, GoogleDateTime start, GoogleDateTime end,
			List<String> recurrence, String description, String location) {
		String url = "https://www.googleapis.com/calendar/v3/calendars/%s/events";
		url = String.format(url, calendarId);

		GoogleEvent event = new GoogleEvent();
		event.setSummary(summary);
		event.setStart(start);
		event.setEnd(end);
		event.setRecurrence(recurrence);
		event.setDescription(description);
		event.setLocation(location);

		Gson gson = getDateTimeParseGson();
		String eventJson = gson.toJson(event);

		String result = APIService.PostResult(url, accessToken, eventJson);
		gson = new Gson();
		event = gson.fromJson(result, GoogleEvent.class);

		return event;
	}

	public GoogleEvent insertEvent(String calendarId, GoogleEvent event) {
		String url = "https://www.googleapis.com/calendar/v3/calendars/%s/events";
		url = String.format(url, calendarId);

		Gson gson = getDateTimeParseGson();
		String eventJson = gson.toJson(event);

		String result = APIService.PostResult(url, accessToken, eventJson);
		gson = new Gson();
		event = gson.fromJson(result, GoogleEvent.class);

		return event;
	}
	
	public GoogleEvent insertEvent(String calendarId, String eventJson) {
		String url = "https://www.googleapis.com/calendar/v3/calendars/%s/events";
		url = String.format(url, calendarId);

		Gson gson = getDateTimeParseGson();

		String result = APIService.PostResult(url, accessToken, eventJson);
		gson = new Gson();
		GoogleEvent event = gson.fromJson(result, GoogleEvent.class);
		
		return event;
	}
	// update
	public GoogleEvent updateEvent(String calendarId, String eventID, String summary, GoogleDateTime start,
			GoogleDateTime end, List<String> recurrence, String description, String location) {
		// get old value of event
		GoogleEvent event = getEvent(calendarId, eventID);

		// create url
		String url = "https://www.googleapis.com/calendar/v3/calendars/%s/events/%s";
		url = String.format(url, calendarId, eventID);

		// update value
		event.setSummary(summary);
		event.setStart(start);
		event.setEnd(end);

		// add 1 to the sequence to update
		event.setSequence(event.getSequence() + 1);

		// convert to json
		gson = getDateTimeParseGson();
		String eventJson = gson.toJson(event);

		// put
		String result = APIService.PutResult(url, accessToken, eventJson);

		// convert to java obj
		gson = new Gson();
		event = gson.fromJson(result, GoogleEvent.class);

		return event;
	}

	private GoogleEvent getEvent(String calendarId, String eventID) {
		// create url
		String url = "https://www.googleapis.com/calendar/v3/calendars/%s/events/%s";
		url = String.format(url, calendarId, eventID);

		// get json result
		String result = APIService.GetResult(url, accessToken);

		// convert to java obj
		gson = new Gson();
		GoogleEvent event = gson.fromJson(result, GoogleEvent.class);

		return event;
	}

	public void deleteEvent(String calendarID, String eventID) {
		String url = "https://www.googleapis.com/calendar/v3/calendars/%s/events/%s";
		url = String.format(url, calendarID, eventID);

		APIService.DeleteResult(url, accessToken);
	}

	// User info
	public String getEmailAddress() {
		String url = "https://www.googleapis.com/userinfo/v2/me"; // url

		String result = APIService.GetResult(url, accessToken);

		GoogleUser user = gson.fromJson(result, GoogleUser.class);

		return user.getEmail();

	}

	public String getEmailAddress(String accessToken) {
		String url = "https://www.googleapis.com/userinfo/v2/me"; // url

		String result = APIService.GetResult(url, accessToken);

		GoogleUser user = gson.fromJson(result, GoogleUser.class);

		return user.getEmail();

	}

}
