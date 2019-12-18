package vn.edu.vnua.dse.stcalendar.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vn.edu.vnua.dse.stcalendar.model.CSVData;
import vn.edu.vnua.dse.stcalendar.service.ImportService;

@Service("importService")
public class ImportServiceImpl implements ImportService {

	@Value("${save.firstTime.import}")
	private String filename;

	public ResponseEntity<Object> getFile() throws IOException {
		CSVData csv1 = new CSVData();
		csv1.setSubject("Event 1");
		csv1.setStartDate("12/17/2019");
		csv1.setStartTime("08:00");
		csv1.setEndDate("12/18/2019");
		csv1.setEndTime("09:00");
		csv1.setAllDayEvent("FALSE");
		csv1.setDescription("Description 1");
		csv1.setLocation("Ho Chi Minh");

		CSVData csv2 = new CSVData();
		csv2.setSubject("Event 2");
		csv2.setStartDate("09/01/2019");
		csv2.setEndDate("09/03/2019");
		csv2.setAllDayEvent("TRUE");
		csv2.setDescription("Description 2");
		csv2.setLocation("Ha Noi");

		List<CSVData> csvDataList = new ArrayList<CSVData>();
		csvDataList.add(csv1);
		csvDataList.add(csv2);

		StringBuilder filecontent = new StringBuilder(
				"Subject,Start Date,Start Time,End Date,End Time,All day event,Description,Location\n");
		for (CSVData csv : csvDataList) {
			filecontent.append(csv.getSubject()).append(",").append(csv.getStartDate()).append(",")
					.append(csv.getStartTime()).append(",").append(csv.getEndDate()).append(",")
					.append(csv.getEndTime()).append(",").append(csv.getAllDayEvent()).append(",")
					.append(csv.getDescription()).append(",").append(csv.getLocation()).append("\n");
		}

		FileWriter filewriter = new FileWriter(filename);
		filewriter.write(filecontent.toString());
		filewriter.flush();

		File file = new File(filename);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "O");

		ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/txt")).body(resource);
		return responseEntity;
	}
}
