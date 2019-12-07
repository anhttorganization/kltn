package vn.edu.vnua.dse.stcalendar.security.model.token;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import vn.edu.vnua.dse.stcalendar.security.config.JwtSettings;
import vn.edu.vnua.dse.stcalendar.security.model.Scopes;
import vn.edu.vnua.dse.stcalendar.security.model.UserContext;

@Component
public class JwtTokenFactory {
	@Autowired
	private JwtSettings settings;

	/**
	 * Factory method for issuing new JWT Tokens.
	 * 
	 * @param username
	 * @param roles
	 * @return
	 */
	public AccessJwtToken createAccessJwtToken(UserContext userContext) {
		if (StringUtils.isBlank(userContext.getUsername()))
			throw new IllegalArgumentException("Không thể tạo JWT Token vì không có username");

		if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty())
			throw new IllegalArgumentException("User không có bất kỳ quyền nào");
		// tạo claims
		Claims claims = Jwts.claims().setSubject(userContext.getUsername());
		claims.put("scopes", userContext.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));

		// Thời gian hiện tại
		LocalDateTime currentTime = LocalDateTime.now();

		String token = Jwts.builder().setClaims(claims).setIssuer(settings.getTokenIssuer())
				.setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
				.setExpiration(Date.from(currentTime.plusMinutes(settings.getTokenExpirationTime())
						.atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey()).compact();

		return new AccessJwtToken(token, claims);
	}

	public JwtToken createRefreshToken(UserContext userContext) {
		if (StringUtils.isBlank(userContext.getUsername())) {
			throw new IllegalArgumentException("Không thể tạo JWT Token vì không có username");
		}

		LocalDateTime currentTime = LocalDateTime.now();

		Claims claims = Jwts.claims().setSubject(userContext.getUsername());
		claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));

		String token = Jwts.builder().setClaims(claims).setIssuer(settings.getTokenIssuer())
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
				.setExpiration(Date.from(currentTime.plusMinutes(settings.getRefreshTokenExpTime())
						.atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey()).compact();

		return new AccessJwtToken(token, claims);
	}
	
	public JwtToken createRegisterToken(String username) {
		if (StringUtils.isBlank(username)) {
			throw new IllegalArgumentException("Không thể tạo JWT Token vì không có username");
		}

		LocalDateTime currentTime = LocalDateTime.now();

		Claims claims = Jwts.claims().setSubject(username);
		claims.put("scopes", Arrays.asList(Scopes.REGISTER_TOKEN.authority()));

		String token = Jwts.builder().setClaims(claims).setIssuer(settings.getTokenIssuer())
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
				.setExpiration(Date.from(currentTime.plusMinutes(settings.getRefreshTokenExpTime())
						.atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey()).compact();

		return new AccessJwtToken(token, claims);
	}
}
