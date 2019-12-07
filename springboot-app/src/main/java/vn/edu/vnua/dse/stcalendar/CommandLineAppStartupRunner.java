package vn.edu.vnua.dse.stcalendar;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import vn.edu.vnua.dse.stcalendar.model.User;
import vn.edu.vnua.dse.stcalendar.service.UserService;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    private UserService userService;

    @Override
    public void run(String...args) throws Exception {
    	Optional<User> user = userService.findByUsername("anhttmail@gmail.com");
    	System.out.println(user);
    	System.out.println();
    	
//    	System.out.println(new JwtSettings().getTokenSigningKey());
    }
}