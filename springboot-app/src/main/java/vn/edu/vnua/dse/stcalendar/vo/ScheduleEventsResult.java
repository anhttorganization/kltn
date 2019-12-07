package vn.edu.vnua.dse.stcalendar.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleEvent;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleEventsResult {
	private List<GoogleEvent> subjectEvents;
	private List<String> weekEvents;
}
