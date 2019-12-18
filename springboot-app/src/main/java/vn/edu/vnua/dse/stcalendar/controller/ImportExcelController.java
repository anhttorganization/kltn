package vn.edu.vnua.dse.stcalendar.controller;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.vnua.dse.stcalendar.service.impl.ImportServiceImpl;

@RestController
public class ImportExcelController {

	@Autowired
	ImportServiceImpl importService;

	@GetMapping(value = "/dowload", produces = "application/json;charset=UTF-8")
	public ResponseEntity<Object> dowloadFile() {

		FileWriter filewriter = null;
		try {
			return importService.getFile();
		} catch (Exception e) {
			return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			if (filewriter != null)
				try {
					filewriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
