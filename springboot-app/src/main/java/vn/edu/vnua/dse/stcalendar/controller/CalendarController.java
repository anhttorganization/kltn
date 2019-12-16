package vn.edu.vnua.dse.stcalendar.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.vnua.dse.stcalendar.common.AppUtils;
import vn.edu.vnua.dse.stcalendar.exceptions.CustomException;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleCalendar;
import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarApi;
import vn.edu.vnua.dse.stcalendar.service.UserService;

@RestController
@RequestMapping
public class CalendarController {

	@Autowired
	CalendarApi calendarApi;

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "api/calendars", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public  ResponseEntity<List<GoogleCalendar>> calendars() {
		String refreshToken = userService.getUseContextDetail().getGgRefreshToken();
		
		if(!AppUtils.isNotNullOrEmpty(refreshToken)) {
			throw new CustomException("refresh token rỗng");
		}
		try {
			calendarApi = new CalendarApi(refreshToken);
		} catch (IOException e) {
			throw new CustomException("Lỗi với refresh token");
		}
		
		List<GoogleCalendar> calendars = calendarApi.getCalendarList();
		
		return ResponseEntity.ok().body(calendars);
	}
	
	
	
}
