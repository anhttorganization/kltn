package vn.edu.vnua.dse.stcalendar.security.exceptions;

import org.springframework.security.core.AuthenticationException;

import vn.edu.vnua.dse.stcalendar.security.model.token.JwtToken;

public class JwtExpiredTokenException extends AuthenticationException {

	private static final long serialVersionUID = 3677312908940645012L;

	private JwtToken token;

	public JwtExpiredTokenException(String msg) {
		super(msg);
	}

	public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
		super(msg, t);
		this.token = token;
	}

	public String token() {
		return this.token.getToken();
	}
}
