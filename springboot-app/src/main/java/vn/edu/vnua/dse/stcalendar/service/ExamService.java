package vn.edu.vnua.dse.stcalendar.service;

import java.util.List;

import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarApi;
import vn.edu.vnua.dse.stcalendar.vo.ExamEventVo;
import vn.edu.vnua.dse.stcalendar.vo.ScheduleEventVo;

public interface ExamService {

    public List<ExamEventVo> insert(CalendarApi calendarApi, String calenId, List<ExamEventVo> eventDetailVos);
}
