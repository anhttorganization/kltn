package vn.edu.vnua.dse.stcalendar.security.model.token;

import java.util.List;
import java.util.Optional;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import vn.edu.vnua.dse.stcalendar.security.model.Scopes;

public class RegisterToken implements JwtToken{
	  private Jws<Claims> claims;

	    private RegisterToken(Jws<Claims> claims) {
	        this.claims = claims;
	    }
	    public static Optional<RegisterToken> create(RawAccessJwtToken token, String signingKey) {
	        Jws<Claims> claims = token.parseClaims(signingKey);

	        @SuppressWarnings("unchecked")
			List<String> scopes = claims.getBody().get("scopes", List.class);
	        if (scopes == null || scopes.isEmpty() 
	                || !scopes.stream().filter(scope -> Scopes.REGISTER_TOKEN.authority().equals(scope)).findFirst().isPresent()) {
	            return Optional.empty();
	        }

	        return Optional.of(new RegisterToken(claims));
	    }

	    @Override
	    public String getToken() {
	        return null;
	    }

	    public Jws<Claims> getClaims() {
	        return claims;
	    }
	    
	    public String getJti() {
	        return claims.getBody().getId();
	    }
	    
	    public String getSubject() {
	        return claims.getBody().getSubject();
	    }
}
