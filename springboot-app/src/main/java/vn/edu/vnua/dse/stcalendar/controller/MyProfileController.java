package vn.edu.vnua.dse.stcalendar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.vnua.dse.stcalendar.model.User;
import vn.edu.vnua.dse.stcalendar.security.auth.JwtAuthenticationToken;
import vn.edu.vnua.dse.stcalendar.security.model.UserContext;
import vn.edu.vnua.dse.stcalendar.service.UserService;

@RestController
public class MyProfileController {
	
	@Autowired UserService userService;
	
	 @RequestMapping(value="/api/me", method=RequestMethod.GET, produces = "application/json;charset=UTF-8")
	    public @ResponseBody User get(JwtAuthenticationToken token) {
		 	UserContext principal =(UserContext) token.getPrincipal();
	        return userService.findByUsername(principal.getUsername()).get();
	    }
}
