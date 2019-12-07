package vn.edu.vnua.dse.stcalendar.security.endpoint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.vnua.dse.stcalendar.exceptions.CustomException;
import vn.edu.vnua.dse.stcalendar.model.User;
import vn.edu.vnua.dse.stcalendar.security.config.JwtSettings;
import vn.edu.vnua.dse.stcalendar.security.exceptions.InvalidJwtToken;
import vn.edu.vnua.dse.stcalendar.security.jwt.extractor.TokenExtractor;
import vn.edu.vnua.dse.stcalendar.security.jwt.verifier.TokenVerifier;
import vn.edu.vnua.dse.stcalendar.security.model.token.RawAccessJwtToken;
import vn.edu.vnua.dse.stcalendar.security.model.token.RegisterToken;
import vn.edu.vnua.dse.stcalendar.service.UserService;

@RestController
public class RegisterTokenEndpoint {
    @Autowired private JwtSettings jwtSettings;
    @Autowired private UserService userService;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;
    
    @RequestMapping(value="/api/auth/register/confirm", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<User> registerConfirm(@RequestParam("token") String token, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
        RawAccessJwtToken rawToken = new RawAccessJwtToken(token);
        RegisterToken registerToken = RegisterToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());
       
        String jti = registerToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }
        
        String subject = registerToken.getSubject();
        User user = userService.findByUsername(subject).orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));
        if(user.isEnabled()) {
        	throw new CustomException("Tài khoản đã được kích hoạt rồi");
        }
        user.setEnabled(true);
        final User updatedUser = userService.save(user);
        
        return  ResponseEntity.ok(updatedUser);

    }
}
