package vn.edu.vnua.dse.stcalendar.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.vnua.dse.stcalendar.crawling.SubjectEventDetails;
import vn.edu.vnua.dse.stcalendar.exceptions.CustomException;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarApi;
import vn.edu.vnua.dse.stcalendar.model.Semester;
import vn.edu.vnua.dse.stcalendar.repository.SemesterRepository;
import vn.edu.vnua.dse.stcalendar.service.ScheduleService;
import vn.edu.vnua.dse.stcalendar.service.UserService;
import vn.edu.vnua.dse.stcalendar.vo.EventDetailVo;
import vn.edu.vnua.dse.stcalendar.vo.ScheduleCreate;
import vn.edu.vnua.dse.stcalendar.vo.ScheduleEventsResult;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

//	@Autowired
//	CalendarApi calendarApi;

	@Autowired
	SemesterRepository semesterRepository;

	@Autowired
	UserService userService;

	@Override
	public boolean isExist(ScheduleCreate scheduleCreate) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public List<EventDetailVo> insert(CalendarApi calendarApi, String calenId, ScheduleCreate scheduleCreate) {

		String semesterId = scheduleCreate.getSemester();
		List<EventDetailVo> insertedEvents = new ArrayList<EventDetailVo>();
		Semester semester = semesterRepository.findById(semesterId)
				.orElseThrow(() -> new CustomException("Không tìm thấy Học kỳ với id " + semesterId));

		ScheduleEventsResult result = SubjectEventDetails.getEventsFromSchedule(scheduleCreate.getStudentId(),
				semester);
		insertedEvents = result.getSubjectEvents();

		// Thêm sự kiện môn học
		for (EventDetailVo eventDetailVo : insertedEvents) {
			GoogleEvent googleEvent = SubjectEventDetails.toGoogleEvent(eventDetailVo, userService.getUseContextDetail().getRoles().iterator().next().getName());
			GoogleEvent insertResult = calendarApi.insertEvent(calenId, googleEvent);
			if (insertResult == null) {
				insertedEvents.remove(eventDetailVo);
			}
		}

		if (insertedEvents.size() <= 0) {
			throw new CustomException("Lỗi khi insert lịch");
		}

		return insertedEvents;
	}

	@Override
	public List<EventDetailVo> insert(CalendarApi calendarApi, String calenId, List<EventDetailVo> eventDetailVos) {
		List<EventDetailVo> insertedEvents = new ArrayList<EventDetailVo>();
		insertedEvents = eventDetailVos;
		// Thêm sự kiện môn học
		for (EventDetailVo eventDetailVo : insertedEvents) {
			GoogleEvent googleEvent = SubjectEventDetails.toGoogleEvent(eventDetailVo, userService.getUseContextDetail().getRoles().iterator().next().getName());
			GoogleEvent insertResult = calendarApi.insertEvent(calenId, googleEvent);
			if (insertResult == null) {
				insertedEvents.remove(eventDetailVo);
			}
		}

		if (insertedEvents.size() <= 0) {
			throw new CustomException("Lỗi khi insert lịch");
		}

		return insertedEvents;
	}

	/**
	 * 
	 */
	@Override
	public void insertByJson(CalendarApi calendarApi, String calenId, List<String> events) {
		try {
			if (events.size() > 0) {
				for (String eventJson : events) {
					calendarApi.insertEvent(calenId, eventJson);
				}
			}
		} catch (Exception e) {
			throw new CustomException(ScheduleServiceImpl.class + ": Lỗi khi insert events vào calendar");
		}
	}

}
