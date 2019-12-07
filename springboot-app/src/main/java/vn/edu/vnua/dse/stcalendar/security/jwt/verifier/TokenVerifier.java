package vn.edu.vnua.dse.stcalendar.security.jwt.verifier;

public interface TokenVerifier {
    public boolean verify(String jti);
}
