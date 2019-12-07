package vn.edu.vnua.dse.stcalendar.security.jwt.extractor;

public interface TokenExtractor {
	public String extract(String payload);
}
