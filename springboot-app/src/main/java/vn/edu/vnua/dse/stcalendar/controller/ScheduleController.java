package vn.edu.vnua.dse.stcalendar.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.edu.vnua.dse.stcalendar.common.AppConstant;
import vn.edu.vnua.dse.stcalendar.common.AppUtils;
import vn.edu.vnua.dse.stcalendar.common.WebUtil;
import vn.edu.vnua.dse.stcalendar.crawling.SubjectEventDetails;
import vn.edu.vnua.dse.stcalendar.exceptions.CustomException;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleCalendar;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleEventList;
import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarApi;
import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarConstant;
import vn.edu.vnua.dse.stcalendar.model.Calendar;
import vn.edu.vnua.dse.stcalendar.model.CalendarDetail;
import vn.edu.vnua.dse.stcalendar.model.Event;
import vn.edu.vnua.dse.stcalendar.model.Semester;
import vn.edu.vnua.dse.stcalendar.model.User;
import vn.edu.vnua.dse.stcalendar.repository.CalendarDetailRepository;
import vn.edu.vnua.dse.stcalendar.repository.CalendarRepository;
import vn.edu.vnua.dse.stcalendar.repository.EventRepository;
import vn.edu.vnua.dse.stcalendar.repository.SemesterRepository;
import vn.edu.vnua.dse.stcalendar.repository.UserRepository;
import vn.edu.vnua.dse.stcalendar.service.ScheduleService;
import vn.edu.vnua.dse.stcalendar.service.UserService;
import vn.edu.vnua.dse.stcalendar.vo.CalendarAndEventsResponse;
import vn.edu.vnua.dse.stcalendar.vo.EventDetailVo;
import vn.edu.vnua.dse.stcalendar.vo.ScheduleCreate;
import vn.edu.vnua.dse.stcalendar.vo.ScheduleEventsResult;

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

	@RequestMapping(value = "api/calendar/schedule/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<CalendarAndEventsResponse> create(@RequestBody ScheduleCreate scheduleCreate){
		CalendarAndEventsResponse response = new CalendarAndEventsResponse();

		String studentId = scheduleCreate.getStudentId();
		String semesterId = scheduleCreate.getSemester();
		
		if(AppUtils.isNullOrEmpty(studentId)) {
			throw new CustomException("Mã sinh viên rỗng");
		}
		
		if(AppUtils.isNullOrEmpty(semesterId)) {
			throw new CustomException("Học kỳ rỗng");
		}
		// get user context
		User user = userService.getUseContextDetail();

		String ggRefreshToken = user.getGgRefreshToken();

		try {
			calendarApi = new CalendarApi(ggRefreshToken);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new CustomException(ScheduleController.class + ": Lỗi khi tạo đối tượng calendarApi" + e.getMessage());	
		}

		// 1. Kiểm tra xem thời khóa biểu với mã sinh viên nhập vào đã được thêm chưa
		// find
		Optional<List<Calendar>> calenOptional = calendarRepository.findByStudentIdAndTypeAndUserId(studentId, false,
				user.getId());

		// Nếu không có lịch với mã sinh viên được thêm
		if (!calenOptional.isPresent()) {
			addCalendar(scheduleCreate, user);
		}

		// Nếu đã có lịch với mã sinh viên truyền vào
		List<Calendar> calendars = calenOptional.get();
		Calendar calendar = filterCalendar(calendars);

		if (calendar == null) {
			addCalendar(scheduleCreate, user);
		}

		// kiem tra xem calendar voi hoc ky nhap vao da co chua
		Optional<CalendarDetail> detailOptional = calendarDetailRepository
				.findByCalendar_IdAndSemester_Id(calendar.getId(), semesterId);
		if (!detailOptional.isPresent()) {
			addEvents(calendar, studentId, semesterId);
			// thanh cong----------------------------------
		}

		CalendarDetail calendarDetail = detailOptional.get();
		handlingChanges(calendarDetail, scheduleCreate, calendar);

		return null;
	}

	/**
	 * Hàm thêm một calendar mới
	 * 
	 * @param studentId  mã sinh viên/ giảng viên
	 * @param semester ma hoc ky
	 * @return Đối tượng lịch sau khi thêm
	 */
	private ResponseEntity<CalendarAndEventsResponse> addCalendar(ScheduleCreate scheduleCreate, User user) {
		
		//insert calendar mới trên google
		String studentId = scheduleCreate.getStudentId();
		String semesterId = scheduleCreate.getSemester();
		//Kiem tra, lay thong tin hoc ky trong db
		Semester semester = semesterRepository.getOne(semesterId);
		if(semester == null) {
			throw new CustomException("Không tồn tại học kỳ đã nhập");
		}
		ScheduleEventsResult result = SubjectEventDetails.getEventsFromSchedule(studentId, semester);
		List<EventDetailVo> eventDetailVos = result.getSubjectEvents();
		List<String> weekEvents = result.getWeekEvents();
		
		String summary = AppConstant.SCHEDULE_SUMMARY + studentId;

		GoogleCalendar ggcalen = calendarApi.insertCalendar(summary, CalendarConstant.TIME_ZONE);
		
		
		List<EventDetailVo> insertedEventVos = scheduleService.insert(calendarApi, ggcalen.getId(), eventDetailVos);
		GoogleEventList eventList = new GoogleEventList();
		eventList.setItems(SubjectEventDetails.toGoogleEvents(eventDetailVos));
		
		HashSet<Event> events = new HashSet<>();
		
		//convert to event và lưu vào db
		for (EventDetailVo detailVo : insertedEventVos) {
			Event evt = Event.builder()
					.eventId(detailVo.getEventId())
					.calendarDetail(null)
					.subjectId(detailVo.getSubjectId())
					.subjectGroup(detailVo.getSubjectGroup())
					.clazz(detailVo.getClazz())
					.practiceGroup(detailVo.getPracticeGroup())
					.credit(detailVo.getCredit())
					.startSlot(detailVo.getStartSlot())
					.endSlot(detailVo.getEndSlot())
					.status(true)
					.createdAt(new Date())
					.updatedAt(new Date())
					.calendar(null)
					.build();
			events.add(evt);
		}
		scheduleService.insertByJson(ggcalen.getId(), weekEvents);
		String scheduleHash = SubjectEventDetails.scheduleHash;//

		CalendarDetail calendarDetail = new CalendarDetail(semester, scheduleHash, events);
		calendarDetail.setScheduleHash(SubjectEventDetails.scheduleHash);
		Calendar calendar = new Calendar(user, studentId, ggcalen.getId(), false, new Date(), new Date(), calendarDetail);
		
		try {
			calendarRepository.save(calendar);
		}catch(Exception e){
			throw new CustomException("Không insert dc calendar vào db "+ e.getMessage());
		}
		return ResponseEntity.ok().body(new CalendarAndEventsResponse("Thêm thời khóa biểu thành công", ggcalen, eventList.getItems()));
	}

	/**
	 * Hàm thêm một các sự kiện mới vào calendar đã có
	 * 
	 * @param studentId  mã sinh viên/ giảng viên
	 * @param semesterId ma hoc ky
	 * @param calendar đối tượng lịch
	 * @return Đối tượng lịch sau khi thêm
	 */
	private ResponseEntity<CalendarAndEventsResponse> addEvents(Calendar calendar, String studentId,
			String semesterId) {
		return null;

	}

	/**
	 * Hàm loại bỏ các calendar trùng lặp/lỗi trong database
	 * 
	 * @param studentId  mã sinh viên/ giảng viên
	 * @param semesterId ma hoc ky
	 * @param calendar đối tượng lịch
	 * @return Calendar hợp lệ
	 */
	private Calendar filterCalendar(List<Calendar> calendars) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Xử lý thay đổi, cập nhật lịch
	 * @param calendarDetail đối tượng chứa các sự kiện của một học kỳ
	 * @return Calendar hợp lệ
	 */
	private ResponseEntity<CalendarAndEventsResponse> handlingChanges(CalendarDetail calendarDetail,
			ScheduleCreate scheduleCreate, Calendar calendar) {
		return null;
	}
}
