package vn.edu.vnua.dse.stcalendar.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.stcalendar.model.Calendar;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarAndEventsResponse {
	private String message;
	private Calendar calendar;
	private List<GoogleEvent> list;
}
