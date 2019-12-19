package vn.edu.vnua.dse.stcalendar.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.edu.vnua.dse.stcalendar.crawling.ExamEventDetails;
import vn.edu.vnua.dse.stcalendar.exceptions.CustomException;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarApi;
import vn.edu.vnua.dse.stcalendar.service.ExamService;
import vn.edu.vnua.dse.stcalendar.vo.ExamEventVo;

@Service("examService")
public class ExamServiceImpl implements ExamService {

    @Override
    public List<ExamEventVo> insert(CalendarApi calendarApi, String calenId, List<ExamEventVo> eventDetailVos) {
	List<ExamEventVo> insertedEvents = eventDetailVos;
	for (ExamEventVo eventVo : eventDetailVos) {
	    GoogleEvent googleEvent = ExamEventDetails.toGoogleEvent(eventVo);
	    GoogleEvent insertResult = calendarApi.insertEvent(calenId, googleEvent);
	    if (insertResult == null) {
		insertedEvents.remove(eventVo);
	    }
	}

	if (insertedEvents.size() <= 0) {
		throw new CustomException("Lỗi khi insert lịch");
	}

	return insertedEvents;
    }

}
