package vn.edu.vnua.dse.stcalendar.security.auth.ajax;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import vn.edu.vnua.dse.stcalendar.model.User;
import vn.edu.vnua.dse.stcalendar.security.model.UserContext;
import vn.edu.vnua.dse.stcalendar.service.UserService;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "No authentication data provided");
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy User: " + username));
	
		//so sánh mật khẩu 
		if (!encoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("Quá trình xác thực đã thất bại. Tên đăng nhập hoặc mật khẩu không hợp lệ.");
		}
		//check null quyền của User
		if (user.getRoles() == null) throw new InsufficientAuthenticationException("Người dùng không được cấp quyền nào.");
		
		//Lấy danh sách các quyền của người dùng
		List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))//get Name of Role
                .collect(Collectors.toList());
        //Tạo đối tượng user context
		UserContext userContext = UserContext.create(user.getUsername(), authorities);
				
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
	}

	 @Override
	 public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	 }

}
