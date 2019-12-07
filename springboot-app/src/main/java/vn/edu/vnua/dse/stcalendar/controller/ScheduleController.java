package vn.edu.vnua.dse.stcalendar.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarApi;
import vn.edu.vnua.dse.stcalendar.model.Calendar;
import vn.edu.vnua.dse.stcalendar.model.CalendarDetail;
import vn.edu.vnua.dse.stcalendar.model.User;
import vn.edu.vnua.dse.stcalendar.repository.CalendarDetailRepository;
import vn.edu.vnua.dse.stcalendar.repository.CalendarRepository;
import vn.edu.vnua.dse.stcalendar.repository.EventRepository;
import vn.edu.vnua.dse.stcalendar.repository.SemesterRepository;
import vn.edu.vnua.dse.stcalendar.repository.UserRepository;
import vn.edu.vnua.dse.stcalendar.service.ScheduleService;
import vn.edu.vnua.dse.stcalendar.service.UserService;
import vn.edu.vnua.dse.stcalendar.vo.CalendarAndEventsResponse;
import vn.edu.vnua.dse.stcalendar.vo.ScheduleCreate;

@Controller
@RequestMapping
public class ScheduleController {

	@Autowired
	CalendarRepository calendarRepository;

	@Autowired
	CalendarDetailRepository calendarDetailRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	CalendarApi calendarApi;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	ScheduleService scheduleService;

	@Autowired
	SemesterRepository semesterRepository;

	
	
	
	
	
	@RequestMapping(value = "api/schedule/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<CalendarAndEventsResponse> create(
			@ModelAttribute("scheduleCreate") ScheduleCreate scheduleCreate) throws IOException {
		CalendarAndEventsResponse response = new CalendarAndEventsResponse();
		
		String studentId = scheduleCreate.getStudentId();
		String semesterId = scheduleCreate.getSemester();

		// get user context
		User user = userService.getUseContextDetail();
		
		String ggRefreshToken = user.getGgRefreshToken();
		
		calendarApi = new CalendarApi(ggRefreshToken);
		
		// 1. Kiểm tra xem thời khóa biểu với mã sinh viên nhập vào đã được thêm chưa
		// find
		Optional<List<Calendar>> calenOptional = calendarRepository.findByStudentIdAndTypeAndUserId(studentId, false,
				user.getId());

		// Nếu không có lịch với mã sinh viên được thêm
		if (!calenOptional.isPresent()) {
			addCalendar(studentId, semesterId);
		}

		// Nếu đã có lịch với mã sinh viên truyền vào
		List<Calendar> calendars = calenOptional.get();
		Calendar calendar = filterCalendar(calendars);

		if (calendar == null) {
			addCalendar(studentId, semesterId);
		}

		// kiem tra xem calendar voi hoc ky nhap vao da co chua
		Optional<CalendarDetail> detailOptional = calendarDetailRepository.findByCalendar_IdAndSemester_Id(calendar.getId(), semesterId);
		if (!detailOptional.isPresent()) {
			addEvents(calendar, studentId, semesterId);
			//thanh cong----------------------------------
		}
		
		CalendarDetail calendarDetail = detailOptional.get();
		handlingChanges(calendarDetail, scheduleCreate, calendar);
		
		return null;
	}



	private ResponseEntity<CalendarAndEventsResponse> addCalendar(String studentId, String semesterId) {
		return null;
	}

	private ResponseEntity<CalendarAndEventsResponse> addEvents(Calendar calendar, String studentId, String semesterId) {
		return null;
		
	}

	private Calendar filterCalendar(List<Calendar> calendars) {
		// TODO Auto-generated method stub
		return null;
	}

	private ResponseEntity<CalendarAndEventsResponse> handlingChanges(CalendarDetail calendarDetail, ScheduleCreate scheduleCreate, Calendar calendar) {
		return null;
	}
}
