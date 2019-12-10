package vn.edu.vnua.dse.stcalendar.service;

import java.util.List;

import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarApi;
import vn.edu.vnua.dse.stcalendar.vo.EventDetailVo;
import vn.edu.vnua.dse.stcalendar.vo.ScheduleCreate;

public interface ScheduleService {
	public boolean isExist(ScheduleCreate scheduleCreate);
	
	public List<EventDetailVo> insert(CalendarApi calendarApi, String calenId, ScheduleCreate scheduleCreate);
	
	public List<EventDetailVo> insert(CalendarApi calendarApi, String calenId, List<EventDetailVo> eventDetailVos);
	
	void insertByJson(String calenId, List<String> events);
}
