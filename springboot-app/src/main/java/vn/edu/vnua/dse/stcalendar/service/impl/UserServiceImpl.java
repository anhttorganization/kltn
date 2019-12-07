package vn.edu.vnua.dse.stcalendar.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import vn.edu.vnua.dse.stcalendar.exceptions.CustomException;
import vn.edu.vnua.dse.stcalendar.model.User;
import vn.edu.vnua.dse.stcalendar.repository.UserRepository;
import vn.edu.vnua.dse.stcalendar.security.model.UserContext;
import vn.edu.vnua.dse.stcalendar.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public UserContext getPrincipal() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication  authentication = context.getAuthentication();
		  UserContext userContext = (UserContext) authentication.getPrincipal();
		return userContext;
	}

	@Override
	public User getUseContextDetail() {
	    String username = getPrincipal().getUsername();
	    User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException("Không tìm thấy User với username: " + username));
	    
		return user;
	}

	@Override
	public void ThrowError() {
		// TODO Auto-generated method stub
		throw new CustomException("Đây là lỗi bắn từ userservice");
	}
	
}
