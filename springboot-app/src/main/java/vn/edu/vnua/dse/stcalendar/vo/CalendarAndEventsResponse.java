package vn.edu.vnua.dse.stcalendar.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.vnua.dse.stcalendar.common.CustomStatusCode;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleCalendar;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleEvent;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarAndEventsResponse {
	private CustomStatusCode message;
	private GoogleCalendar calendar;
	private List<GoogleEvent> list;
}
