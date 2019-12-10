package vn.edu.vnua.dse.stcalendar.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleEventsResult {
	private List<EventDetailVo> subjectEvents;
	private List<String> weekEvents;
}
