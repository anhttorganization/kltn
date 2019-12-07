package vn.edu.vnua.dse.stcalendar.security.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

public class AuthMethodNotSupportedException extends AuthenticationServiceException{

	private static final long serialVersionUID = 3335284749902031648L;

	public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }
}
