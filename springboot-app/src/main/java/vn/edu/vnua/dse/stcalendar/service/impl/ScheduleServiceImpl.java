package vn.edu.vnua.dse.stcalendar.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import vn.edu.vnua.dse.stcalendar.vo.ScheduleCreate;
import vn.edu.vnua.dse.stcalendar.vo.ScheduleEventsResult;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	CalendarApi calendarApi;

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
	public List<GoogleEvent> insert(CalendarApi calendarApi, String calenId, ScheduleCreate scheduleCreate) {
		
		String semesterId = scheduleCreate.getSemester();
		List<GoogleEvent> insertedEvents = new ArrayList<GoogleEvent>();
		Semester semester = semesterRepository.findById(semesterId)
				.orElseThrow(() -> new CustomException("Không tìm thấy Học kỳ với id " + semesterId));

		ScheduleEventsResult events = SubjectEventDetails.getEventsFromSchedule(scheduleCreate.getStudentId(), semester);
		
		// Thêm sự kiện môn học
		for (GoogleEvent event : events.getSubjectEvents()) {
			insertedEvents.add(calendarApi.insertEvent(calenId, event));
		}
		
		// Thêm sự kiện tuần
		for (String event : events.getWeekEvents()) {
			insertedEvents.add(calendarApi.insertEvent(calenId, event));
		}

		return insertedEvents;
	}

	@Override
	public Set<String> insert(String calenId, List<GoogleEvent> events) throws IOException {
		Set<String> eventIds = new HashSet<>();
		String refreshToken = userService.getUseContextDetail().getGgRefreshToken();
		calendarApi = new CalendarApi(refreshToken);

		for (GoogleEvent event : events) {
			eventIds.add(calendarApi.insertEvent(calenId, event).getId());
		}

		return eventIds;
	}

	/**
	 * 
	 */
	@Override
	public void insert1(String calenId, List<String> events) {
		try {
			if (events.size() > 0) {
				for (String eventJson : events) {
					calendarApi.insertEvent(calenId, eventJson);
				}
			}
		} catch (Exception e) {
			System.out.println("thêm tuần không thành công!");
		}
	}

}
