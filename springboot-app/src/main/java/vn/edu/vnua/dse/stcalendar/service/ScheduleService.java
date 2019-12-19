package vn.edu.vnua.dse.stcalendar.service;

import java.util.List;

import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarApi;
import vn.edu.vnua.dse.stcalendar.vo.ScheduleEventVo;
import vn.edu.vnua.dse.stcalendar.vo.ScheduleCreate;

public interface ScheduleService {
	public boolean isExist(ScheduleCreate scheduleCreate);
	
	public List<ScheduleEventVo> insert(CalendarApi calendarApi, String calenId, ScheduleCreate scheduleCreate);
	
	public List<ScheduleEventVo> insert(CalendarApi calendarApi, String calenId, List<ScheduleEventVo> eventDetailVos);
	
	void insertByJson(CalendarApi calendarApi, String calenId, List<String> events);
}
