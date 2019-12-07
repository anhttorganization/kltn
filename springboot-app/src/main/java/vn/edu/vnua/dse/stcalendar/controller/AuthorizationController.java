package vn.edu.vnua.dse.stcalendar.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.vnua.dse.stcalendar.common.AppUtils;
import vn.edu.vnua.dse.stcalendar.exceptions.CustomException;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleToken;
import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarApi;
import vn.edu.vnua.dse.stcalendar.model.User;
import vn.edu.vnua.dse.stcalendar.service.UserService;

@RestController
public class AuthorizationController {

	@Autowired
	CalendarApi calendarApi;

	@Autowired
	UserService userService;
	
	@Autowired
	private ObjectMapper mapper;

	private String message = "AuthorizationController: ";

	@RequestMapping(value = "/api/calendar/check-authorization", method = RequestMethod.GET)
	public String checkAuthorization(@RequestParam Map<String, String> requestParams, Model model) {
		// 1.kiểm tra xem đã cấp quyền(có refreshToken) chưa
		User user = userService.getUseContextDetail();
		String refreshToken = user.getGgRefreshToken();
		String accessToken = calendarApi.getAccessToken(refreshToken);
		// Nếu chưa có refresh token
		if (AppUtils.isNullOrEmpty(refreshToken) || AppUtils.isNullOrEmpty(accessToken)) {
			String email = user.getUsername();
			String authUrl;
			if (AppUtils.isNullOrEmpty(email)) {
				message += "Không có username";
				throw new CustomException(message);
			}
			authUrl = CalendarApi.getAuthUrl(email);

			return authUrl;
		}

		return null;

	}
	
	

	@RequestMapping(value = "api/calendar/authorization", method = RequestMethod.GET)
	public String Authorization(@RequestParam("code") String code) throws IOException {
		// get refresh token
	
		GoogleToken token = calendarApi.getRefreshToken(code);
		String accessToken = token.getAccess_token();
		String refreshToken = token.getRefresh_token();
		
		User user = userService.getUseContextDetail();
		if(AppUtils.isNotNullOrEmpty(accessToken) && AppUtils.isNullOrEmpty(refreshToken)) { 
			//truong hop da cap quyen - da lay refresh token nhung het han hoac sai
			userService.save(user);
			
			Map<String, String> maps = new HashMap();
	        maps.put("error", "https://myaccount.google.com/permissions");
	        
			String json = mapper.writeValueAsString(maps);
			return json;
		}
		// compare email
		// lay email ma nguoi dung vua cap quyen
		calendarApi = new CalendarApi(refreshToken);
		String grantedEmail = calendarApi.getEmailAddress();

		String userEmail = user.getUsername();
		if (AppUtils.isNullOrEmpty(userEmail) || AppUtils.isNullOrEmpty(grantedEmail)) {
			message += "Lỗi khi so sánh email, emai rỗng";
			throw new CustomException(message);
		}
		
		if (grantedEmail.equals(userEmail)) {
//			model.addAttribute("success", "Cấp quyền thành công");
		
			//cập nhật user 
			user.setGgRefreshToken(refreshToken);	
			//cập nhập user vào db
			userService.save(user);
			
			Map<String, String> maps = new HashMap();
	        maps.put("success", "Cấp quyền thành công!");
	        
	        String json = mapper.writeValueAsString(maps);
			return json;
		} else {
			message += "Vui lòng cấp quyền cho ứng dụng với tài khoản gmail đã đăng ký!";
			throw new CustomException(message);
		}
	}
}
