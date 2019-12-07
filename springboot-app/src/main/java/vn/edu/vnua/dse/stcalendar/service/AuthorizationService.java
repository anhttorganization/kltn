package vn.edu.vnua.dse.stcalendar.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import vn.edu.vnua.dse.stcalendar.common.AppUtils;
import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarApi;
import vn.edu.vnua.dse.stcalendar.model.User;

public class AuthorizationService {
	@Autowired
	public static CalendarApi calendarApi;
	
	@Autowired
	public static UserService userService;
	
	
	public static String isauthorized() throws FileNotFoundException, IOException {

		// 1.kiểm tra xem đã cấp quyền(có refreshToken) chưa
		User user = userService.getUseContextDetail();

		String refreshToken = user.getGgRefreshToken();

		// 2.nếu chưa => yêu cầu cấp quyền
		calendarApi = new CalendarApi();
		String accessToken = calendarApi.getAccessToken(refreshToken);
		if (AppUtils.isNullOrEmpty(refreshToken) || AppUtils.isNullOrEmpty(accessToken)) {
			String email = user.getUsername();
			String authUrl;
			if (AppUtils.isNullOrEmpty(email)) {
				authUrl = calendarApi.getAuthUrl();
			} else {
				authUrl = calendarApi.getAuthUrl(email);
			}

			return authUrl;
		}
		return null;
	}
}
