package vn.edu.vnua.dse.stcalendar.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.vnua.dse.stcalendar.common.AppConstant;
import vn.edu.vnua.dse.stcalendar.common.AppUtils;
import vn.edu.vnua.dse.stcalendar.common.CustomStatusCode;
import vn.edu.vnua.dse.stcalendar.crawling.ExamEventDetails;
import vn.edu.vnua.dse.stcalendar.crawling.SubjectEventDetails;
import vn.edu.vnua.dse.stcalendar.exceptions.CustomException;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleCalendar;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarApi;
import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarConstant;
import vn.edu.vnua.dse.stcalendar.model.Calendar;
import vn.edu.vnua.dse.stcalendar.model.CalendarDetail;
import vn.edu.vnua.dse.stcalendar.model.Event;
import vn.edu.vnua.dse.stcalendar.model.ExamEvent;
import vn.edu.vnua.dse.stcalendar.model.Semester;
import vn.edu.vnua.dse.stcalendar.model.Types;
import vn.edu.vnua.dse.stcalendar.model.User;
import vn.edu.vnua.dse.stcalendar.repository.CalendarDetailRepository;
import vn.edu.vnua.dse.stcalendar.repository.CalendarRepository;
import vn.edu.vnua.dse.stcalendar.repository.EventRepository;
import vn.edu.vnua.dse.stcalendar.repository.SemesterRepository;
import vn.edu.vnua.dse.stcalendar.repository.UserRepository;
import vn.edu.vnua.dse.stcalendar.service.ExamService;
import vn.edu.vnua.dse.stcalendar.service.UserService;
import vn.edu.vnua.dse.stcalendar.vo.CalendarAndEventsResponse;
import vn.edu.vnua.dse.stcalendar.vo.ExamEventVo;
import vn.edu.vnua.dse.stcalendar.vo.ScheduleCreate;

@RestController
@CrossOrigin
public class ExamController {

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
    ExamService examService;

    @Autowired
    SemesterRepository semesterRepository;

    @RequestMapping(value = "api/exam-calendars/", method = RequestMethod.POST, produces = "+")
    public ResponseEntity<CalendarAndEventsResponse> create(@RequestBody ScheduleCreate scheduleCreate) {

	String studentId = scheduleCreate.getStudentId();
	String semesterId = scheduleCreate.getSemester();

	if (AppUtils.isNullOrEmpty(studentId)) {
	    throw new CustomException("Mã sinh viên rỗng");
	}
	if (AppUtils.isNullOrEmpty(semesterId)) {
	    throw new CustomException("Học kỳ rỗng");
	}
	// get user context
	User user = userService.getUseContextDetail();

	String ggRefreshToken = user.getGgRefreshToken();

	try {
	    calendarApi = new CalendarApi(ggRefreshToken);
	} catch (IOException e) {
	    throw new CustomException(
		    ScheduleController.class + ": Lỗi khi tạo đối tượng calendarApi" + e.getMessage());
	}

	// 1. Kiểm tra xem thời lich thi với mã sinh viên nhập vào đã được thêm chưa
	// find
	Optional<List<Calendar>> calenOptional = calendarRepository.findByStudentIdAndTypeAndUserId(studentId, true,
		user.getId());

	// Nếu không có lịch với mã sinh viên được thêm
	if (!calenOptional.isPresent()) {
	    return addCalendar(scheduleCreate, user);
	}

	// Nếu đã có lịch với mã sinh viên truyền vào
	List<Calendar> calendars = calenOptional.get();
	Calendar calendar = filterCalendar(calendars);

	if (calendar == null) {
	    return addCalendar(scheduleCreate, user);
	}

	// kiem tra xem calendar voi hoc ky nhap vao da co chua
	Optional<CalendarDetail> detailOptional = calendarDetailRepository.findByCalendIdAndSemesId(calendar.getId(),
		semesterId); // chưa test hàm này
	if (!detailOptional.isPresent()) { // calendar đã có, học kỳ này chưa thêm lịch
	    return addEvents(calendar, scheduleCreate);
	    // thanh cong----------------------------------
	}

	CalendarDetail calendarDetail = detailOptional.get();
	return handlingChanges(calendarDetail, scheduleCreate, calendar);

    }

    private ResponseEntity<CalendarAndEventsResponse> handlingChanges(CalendarDetail calendarDetail,
	    ScheduleCreate scheduleCreate, Calendar calendar) {
	String summary = AppConstant.SCHEDULE_SUMMARY + scheduleCreate.getStudentId();

	GoogleCalendar ggcalen = new GoogleCalendar(calendar.getCalendarId(), summary, CalendarConstant.TIME_ZONE);

	return ResponseEntity.ok().body(new CalendarAndEventsResponse(CustomStatusCode.EXISTED, ggcalen, null));
    }

    /**
     * Hàm thêm một calendar mới
     * 
     * @param studentId mã sinh viên/ giảng viên
     * @param semester  ma hoc ky
     * @return Đối tượng lịch sau khi thêm
     */
    private ResponseEntity<CalendarAndEventsResponse> addEvents(Calendar calendar, ScheduleCreate scheduleCreate) {
	// insert calendar mới trên google
	String studentId = scheduleCreate.getStudentId();
	String semesterId = scheduleCreate.getSemester();
	// Kiem tra, lay thong tin hoc ky trong db
	Semester semester = semesterRepository.getOne(semesterId);
	if (semester == null) {
	    throw new CustomException("Không tồn tại học kỳ đã nhập");
	}

	List<ExamEventVo> examEventVos = ExamEventDetails.getEventsFromSchedule(studentId);

	String summary = AppConstant.TEST_SCHEDULE_SUMMARY + studentId;
	GoogleCalendar googleCalendar = new GoogleCalendar(calendar.getCalendarId(), summary,
		CalendarConstant.TIME_ZONE);

	List<ExamEventVo> insertedEventVos = examService.insert(calendarApi, googleCalendar.getId(), examEventVos);
	// chuyen ket qua da insert sang kieu GoogleEvent de return
	List<GoogleEvent> googleEvents = ExamEventDetails.toGoogleEvents(insertedEventVos);

	HashSet<Event> events = new HashSet<>();
	// chuyen sang kieu Event de luu vao db
	for (ExamEventVo eventVo : insertedEventVos) {
	    ExamEvent examEvent = ExamEvent.builder().subjectCode(eventVo.getSubjectCode())
		    .combined(eventVo.getCombined()).examTeam(eventVo.getExamTeam()).build();

	    Event event = new Event(eventVo.getEventId(), examEvent, Types.EXAM);
	    events.add(event);
	}

	String scheduleHash = SubjectEventDetails.scheduleHash;
	// insert to db
	CalendarDetail calendarDetail = new CalendarDetail(semester, scheduleHash, events);
	calendar.addCalendarDetails(calendarDetail);

	try {
	    calendarRepository.save(calendar);
	} catch (Exception e) {
	    throw new CustomException("Không insert được calendar vào database " + e.getMessage());
	}

	return ResponseEntity.ok()
		.body(new CalendarAndEventsResponse(CustomStatusCode.CREATED, googleCalendar, googleEvents));
    }

    private ResponseEntity<CalendarAndEventsResponse> addCalendar(ScheduleCreate scheduleCreate, User user) {

	// insert calendar mới trên google
	String studentId = scheduleCreate.getStudentId();
	String semesterId = scheduleCreate.getSemester();
	// Kiem tra, lay thong tin hoc ky trong db
	Semester semester = semesterRepository.getOne(semesterId);
	if (semester == null) {
	    throw new CustomException("Không tồn tại học kỳ đã nhập");
	}

	List<ExamEventVo> examEventVos = ExamEventDetails.getEventsFromSchedule(studentId);

	String summary = AppConstant.TEST_SCHEDULE_SUMMARY + studentId;

	GoogleCalendar googleCalendar = calendarApi.insertCalendar(summary, CalendarConstant.TIME_ZONE);

	List<ExamEventVo> insertedEventVos = examService.insert(calendarApi, googleCalendar.getId(), examEventVos);
	// chuyen ket qua da insert sang kieu GoogleEvent de return
	List<GoogleEvent> googleEvents = ExamEventDetails.toGoogleEvents(insertedEventVos);

	HashSet<Event> events = new HashSet<>();
	// chuyen sang kieu Event de luu vao db
	for (ExamEventVo eventVo : insertedEventVos) {
	    ExamEvent examEvent = ExamEvent.builder().subjectCode(eventVo.getSubjectCode())
		    .combined(eventVo.getCombined()).examTeam(eventVo.getExamTeam()).build();

	    Event event = new Event(eventVo.getEventId(), examEvent, Types.EXAM);
	    events.add(event);
	}

	String scheduleHash = SubjectEventDetails.scheduleHash;
	// insert to db
	CalendarDetail calendarDetail = new CalendarDetail(semester, scheduleHash, events);
//			calendarDetail.setScheduleHash(SubjectEventDetails.scheduleHash);
	Calendar calendar = new Calendar(user, studentId, googleCalendar.getId(), false, new Date(), new Date(),
		calendarDetail);

	try {
	    calendarRepository.save(calendar);
	} catch (Exception e) {
	    throw new CustomException("Không insert dc calendar vào db " + e.getMessage());
	}

	return ResponseEntity.ok()
		.body(new CalendarAndEventsResponse(CustomStatusCode.CREATED, googleCalendar, googleEvents));
    }

    private Calendar filterCalendar(List<Calendar> calendars) {
	ArrayList<Calendar> results = new ArrayList<>();
	for (Calendar calendar : calendars) { // tìm số calendars tồn tại trên cả db và google
	    if (calendar != null) {
		String calendarId = calendar.getCalendarId(); // kiểm tra xem calendar còn trên calendar list cua nguoi dung khong
		GoogleCalendar googleCalendar = calendarApi.findCalendarInCalendarList(calendarId);
		// Nếu lịch nào chỉ có trong db mà không có trên GGCalendar thì xóa trong db
		if (googleCalendar == null) {
		    calendarRepository.delete(calendar);
		} else {
		    results.add(calendar);
		}
	    }
	}

	if (results.size() == 0) {
	    return null;
	}
	// giu lai mot calendar
	for (int i = 1; i < results.size(); i++) {
	    calendarRepository.delete(results.get(i));
	}
	Calendar calendar = results.get(0);
	return calendar;

    }

}
