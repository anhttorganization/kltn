package vn.edu.vnua.dse.stcalendar.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

public interface ImportService {
	public ResponseEntity<Object> getFile() throws IOException;
}
