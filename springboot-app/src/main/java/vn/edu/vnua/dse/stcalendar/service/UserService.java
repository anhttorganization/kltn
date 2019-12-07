package vn.edu.vnua.dse.stcalendar.service;

import java.util.Optional;

import vn.edu.vnua.dse.stcalendar.model.User;
import vn.edu.vnua.dse.stcalendar.security.model.UserContext;


public interface UserService {
	public Optional<User> findByUsername(String username);
	
	public User save(User user);
	
	public UserContext getPrincipal();
	
    public User getUseContextDetail();
    
    public void ThrowError();
}
