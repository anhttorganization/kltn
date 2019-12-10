package vn.edu.vnua.dse.stcalendar.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import vn.edu.vnua.dse.stcalendar.config.GlobalConfig;
import vn.edu.vnua.dse.stcalendar.exceptions.AlreadyExisted;
import vn.edu.vnua.dse.stcalendar.exceptions.CustomException;
import vn.edu.vnua.dse.stcalendar.model.Role;
import vn.edu.vnua.dse.stcalendar.model.User;
import vn.edu.vnua.dse.stcalendar.repository.RoleRepository;
import vn.edu.vnua.dse.stcalendar.security.config.JwtSettings;
import vn.edu.vnua.dse.stcalendar.security.model.UserContext;
import vn.edu.vnua.dse.stcalendar.security.model.token.JwtToken;
import vn.edu.vnua.dse.stcalendar.security.model.token.JwtTokenFactory;
import vn.edu.vnua.dse.stcalendar.service.UserService;
import vn.edu.vnua.dse.stcalendar.vo.UserVo;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
	@Autowired
	UserService userService;
	@Autowired
	private JwtTokenFactory tokenFactory;
	@Autowired
	public JavaMailSender emailSender;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private GlobalConfig globalConfig;
	@Autowired
	private JwtSettings jwtSettings;
	

	@RequestMapping(value = "/api/auth/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<User> Register(@RequestBody UserVo userVo) throws MessagingException {
		// check exited by username (email)
		if (userService.findByUsername(userVo.getUsername()).isPresent()) {
			throw new AlreadyExisted("gmail đã được đăng ký");
		}

		// check role có tồn tại không
		ArrayList<Role> roles = new ArrayList<Role>();
		Optional<Role> opt;
		opt = roleRepository.findById(userVo.getRoleId());
		if (!opt.isPresent()) {
			throw new CustomException("Role không tồn tại");
		}
		roles.add(opt.get());

		// Tạo token để xác nhận đăng ký
		JwtToken registerToken = tokenFactory.createRegisterToken(userVo.getUsername());

		// Gửi mail kèm token
		String email = userVo.getUsername().toLowerCase();
		String appUrl = globalConfig.getAPP_URL();
		String confirmLink = appUrl + "/api/auth/register/confirm?token=" + registerToken.getToken();
		String html = "<!DOCTYPE html>\r\n<html>\r\n\t<head>\r\n\t\t<meta charset=\"UTF-8\">\r\n\t\t</head>\r\n\t\t<body>\r\n\t\t\t<div class=\"container\" style=\"text-align: center;background-color: #f0fbfa;padding: 10px 40px 10px 40px;\">\r\n\t\t\t\t<h1>Xác nhận email</h1>\r\n\t\t\t\t<p>Cảm ơn bạn đã tạo tài khoản trên \r\n\t\t\t\t\t<a href=\"%s\" id=\"stcalendar\" style=\"color: #007bff;text-decoration: none !important;\"><b>STCalendar</b></a>. Xác minh địa chỉ email của bạn đảm bảo rằng chỉ bạn mới có quyền truy cập vào thông tin tài khoản của mình. Để hoàn tất quá trình đăng ký, vui lòng nhấn vào nút dưới đây:\r\n\t\t\t\t</p>\r\n\t\t\t\t<a href=\"%s\" class=\"button\" onMouseOver=\"this.style.color='#0F0'\"   onMouseOut=\"this.style.color='#fff'\" style=\"text-align: center;display: inline-block;margin: 10px auto;padding: 10px 20px 10px 20px;color: white;background-color: #007bff;box-shadow: 2px 2px 2px grey;border-radius: 10px;text-decoration: none !important;\">Xác nhận</a>\r\n\t\t\t</div>\r\n\t\t</body>\r\n\t</html>\r\n";
		String htmlMsg = String.format(html, appUrl, confirmLink);

		MimeMessage message = emailSender.createMimeMessage();

		boolean multipart = true;

		MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
		message.setContent(htmlMsg, "text/html");
		helper.setTo(email);
		helper.setSubject("Xác nhận đăng ký tài khoản");

		this.emailSender.send(message);
		// Lưu tài khoản
		// encode, set role ->save

		User user = new User();
		user.setUsername(userVo.getUsername());
		user.setPassword(encoder.encode(userVo.getPassword())); // encode pasword
		user.setFirstName(userVo.getFirstName());
		user.setLastName(userVo.getLastName());
		user.setAvatar(userVo.getAvatar());
		user.setClazz(userVo.getClazz());
		user.setFaculty(userVo.getFaculty());
		user.setEnabled(false); // chưa kích hoạt
		user.setRoles(null);
		user.setPosts(null);
		user.setCalendars(null);
		user.setGgRefreshToken(null);
		user.setCreatedAt(new Date());
		user.setUpdatedAt(new Date());
//		user = userService.save(user);
		user.setRoles(roles);
		user = userService.save(user);
		return ResponseEntity.ok().body(user);

	}

//	@RequestMapping(value = { "/api/auth/register-confirm" }, method = { RequestMethod.GET }, produces={ MediaType.APPLICATION_JSON_VALUE })
//	public String confirmRegistration(@RequestParam("token") String token) {
//		RawAccessJwtToken rawAccessToken = new RawAccessJwtToken(token);
//		Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
//		String subject = jwsClaims.getBody().getSubject();
//		@SuppressWarnings("unchecked")
//		List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
//		List<GrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new)
//				.collect(Collectors.toList());
//		
//		
//		return "hello";
//
//	}

	@RequestMapping(value = { "/api/context" }, method = { RequestMethod.GET }, produces={ MediaType.APPLICATION_JSON_VALUE })
	public UserContext hello() {
//		userService.ThrowError();
		SecurityContext context1 = SecurityContextHolder.getContext();
		Authentication  authentication = context1.getAuthentication();
	  UserContext userContext = (UserContext) authentication.getPrincipal();
	  return userContext;
	}
}
