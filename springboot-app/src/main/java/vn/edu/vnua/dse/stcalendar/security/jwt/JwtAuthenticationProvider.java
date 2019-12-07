package vn.edu.vnua.dse.stcalendar.security.jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import vn.edu.vnua.dse.stcalendar.security.auth.JwtAuthenticationToken;
import vn.edu.vnua.dse.stcalendar.security.config.JwtSettings;
import vn.edu.vnua.dse.stcalendar.security.model.UserContext;
import vn.edu.vnua.dse.stcalendar.security.model.token.JwtToken;
import vn.edu.vnua.dse.stcalendar.security.model.token.RawAccessJwtToken;

/**
 * An {@link AuthenticationProvider} implementation that will use provided
 * instance of {@link JwtToken} to perform authentication.
 * 
 * @author vladimir.stankovic
 *
 *         Aug 5, 2016
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private final JwtSettings jwtSettings;

	@Autowired
	public JwtAuthenticationProvider(JwtSettings jwtSettings) {
		this.jwtSettings = jwtSettings;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// get access token from authentication
		RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
		// parse claims from token
		Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
		String subject = jwsClaims.getBody().getSubject();
		@SuppressWarnings("unchecked")
		List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
		List<GrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		// return JwtAuthenticationToken
		UserContext context = UserContext.create(subject, authorities);

		return new JwtAuthenticationToken(context, context.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
