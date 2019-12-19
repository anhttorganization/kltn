package vn.edu.vnua.dse.stcalendar.crawling;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import vn.edu.vnua.dse.stcalendar.common.AppUtils;
import vn.edu.vnua.dse.stcalendar.exceptions.CustomException;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleDateTime;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarConstant;
import vn.edu.vnua.dse.stcalendar.vo.ExamEventVo;

public class ExamEventDetails {
    private static String message = "";
    private static final String DESCRIPTION = "Mã học phần: %s" + "\nNhóm: %s" + "\nTổ: %s";

    // lấy danh dách các event từ lịch thi
    public static final List<ExamEventVo> getEventsFromSchedule(String studentId){
	
	ArrayList<ArrayList<String>> examScheduleJson = getExamSchedule(studentId);
	
	return toEventDetailVos(examScheduleJson);
	
    }

    private static final List<ExamEventVo> toEventDetailVos(ArrayList<ArrayList<String>> examScheduleJson) {
	List<ExamEventVo> events = new ArrayList<>();

	for (ArrayList<String> item : examScheduleJson) {

	    String subjectCode = item.get(1).toString();
	    String subjectName = item.get(2).toString();
	    String combined = item.get(3).toString();
	    String examTeam = item.get(4).toString();
	    String date = item.get(6).toString();
	    int startSlot = Integer.parseInt(item.get(7).toString());
	    int endSlot = startSlot + Integer.parseInt(item.get(8).toString()) - 1;
	    String location = item.get(9).toString();

	    ExamEventVo eventVo = ExamEventVo.builder()
		    .eventId(String.format(CalendarConstant.EVENT_ID, new Date().getTime())).subjectCode(subjectCode)
		    .subjectName(subjectName).combined(combined).examTeam(examTeam).startSlot(startSlot)
		    .endSlot(endSlot).date(date).location(location).build();

	    events.add(eventVo);
	    System.out.println("---------------------------");
	    System.out.println(eventVo);

	}

	return events;

    }

    public static final List<GoogleEvent> toGoogleEvents(List<ExamEventVo> eventDetailVos) {
	List<GoogleEvent> googleEvents = new ArrayList<GoogleEvent>();
	for (ExamEventVo eventDetailVo : eventDetailVos) {
	    googleEvents.add(toGoogleEvent(eventDetailVo));
	}
	
	return googleEvents;

    }

    public static final GoogleEvent toGoogleEvent(ExamEventVo eventDetailVo) {

	String subjectCode = eventDetailVo.getSubjectCode();
	String subjectName = eventDetailVo.getSubjectName();
	String combined = eventDetailVo.getCombined();
	String examTeam = eventDetailVo.getExamTeam();
	int startSlot = eventDetailVo.getStartSlot();
	int endSlot = eventDetailVo.getEndSlot();
	String date = eventDetailVo.getDate();
	String location = eventDetailVo.getLocation();

	String summary = getSummary(subjectName, subjectCode);
	Date start = getStartTime(date, startSlot);
	Date end = getEndTime(date, endSlot);
	String description = getDescription(subjectCode, combined, examTeam);

	GoogleEvent event = new GoogleEvent();
	event.setSummary(summary);
	event.setLocation(location);
	event.setStart(new GoogleDateTime(start, CalendarConstant.TIME_ZONE));
	event.setEnd(new GoogleDateTime(end, CalendarConstant.TIME_ZONE));
	event.setDescription(description);

	return event;

    }

    private static String getSummary(String subjectName, String subjectCode) {
	return subjectName + ", " + subjectCode;
    }

    private static Date getStartTime(String dateStr, int startSlot) {
	Date start = new Date();
	String timeStr = DateTimeConstant.STARTTIME.get(startSlot);
	// create datetime
	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	dateTimeFormat.setTimeZone(TimeZone.getTimeZone(CalendarConstant.TIME_ZONE));

	try {
	    start = dateTimeFormat.parse(dateStr + " " + timeStr);
	} catch (ParseException e) {
	    message = "Lỗi khi parse StartTime";
	    throw new CustomException(message);
	}

	return start;
    }

    private static Date getEndTime(String dateStr, int endSlot) {
	Date end = new Date();
	String timeStr = DateTimeConstant.ENDTIME.get(endSlot);
	// create datetime
	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	dateTimeFormat.setTimeZone(TimeZone.getTimeZone(CalendarConstant.TIME_ZONE));

	try {
	    end = dateTimeFormat.parse(dateStr + " " + timeStr);
	} catch (ParseException e) {
	    message = "Lỗi khi parse StartTime";
	    throw new CustomException(message);
	}

	return end;
    }

    private static String getDescription(String subjectCode, String combined, String examTeam) {
	return String.format(DESCRIPTION, subjectCode, combined, examTeam);
    }

    // Lấy danh sách thời khóa biểu dạng json
    private static ArrayList<ArrayList<String>> getExamSchedule(String studentId) {
	// Mo trinh duyet
	WebDriver driver;
	try {
	    driver = ScheduleUtils.openChrome();
	} catch (IOException e1) {
	    String message = "Lỗi khi mở trình duyệt Chrome";
	    throw new CustomException(message);
	}
	// driver.manage().window().setPosition(new Point(-1000, -1000));
	String url = String.format(ScheduleConstant.EXAM_SCHEDULE_URL, studentId);
	driver.get(url);

	// Get schedule
//	WebDriverWait wait = new WebDriverWait(driver, 50);

	// check update
	if (AppUtils.isAlertPresent(driver)) {
	    Alert alert = driver.switchTo().alert();
	    // Capturing alert message.
	    String alertMessage = driver.switchTo().alert().getText();
	    // Accepting alert
	    alert.accept();

	    if (!alertMessage.equals("")) {
		message = "Lỗi khi mở website thời khóa biểu";
	    }
	    if (alertMessage.equals("Server đang tải lại dữ liệu. Vui lòng trở lại sau 15 phút!")) {
		message = "Server đang tải lại dữ liệu. Vui lòng trở lại sau 15 phút!";
	    }
	    driver.close();
	    driver.quit();
	    throw new CustomException(message);
	}

	try {
	    ScheduleUtils.injectResourceJQuery(driver, "js/MyJQuery.js");
	} catch (IOException e1) {
	    driver.close();
	    driver.quit();
	    message = "Lỗi khi inject Jquery";
	    throw new CustomException(message);
	}
	JavascriptExecutor jse = ((JavascriptExecutor) driver);
	String passCap;
	try {
	    passCap = ScheduleUtils.readResourceFile("js/capcha.js");
	} catch (IOException e1) {
	    driver.close();
	    driver.quit();
	    message = "Lỗi khi đọc rile chapcha.js";
	    throw new CustomException(message);
	}
	jse.executeScript(passCap);
	driver.navigate().to(url);
	// check thong tin sinh vien
	// wait.until(ExpectedConditions.presenceOfElementLocated(By.id(ScheduleConstant.CONTENT_MSV)));
	if (driver.findElements(By.id(ScheduleConstant.HEADER_DAOTAO_ID)).size() == 0) {
	    driver.close();
	    driver.quit();
	    message = "Trang đạo tạo VNUA hiện không truy cập được \\nVui lòng thử lại sau!";
	    throw new CustomException(message);
	}

	if (driver.findElements(By.id(ScheduleConstant.LICHTHI_MSV)).size() == 0) {
	    driver.close();
	    driver.quit();
	    message = "Không tìm thấy thông tin lịch thi sinh viên";
	    throw new CustomException(message);
	}

	// Lay lich thi
	String code;
	try {
	    code = ScheduleUtils.readResourceFile("js/getExamSchedule.js");
	} catch (IOException e) {
	    driver.close();
	    driver.quit();
	    message = "Lỗi khi đọc file getExamSchedule.js";
	    throw new CustomException(message);
	}
	@SuppressWarnings("unchecked")
	ArrayList<ArrayList<String>> examScheduleJson = (ArrayList<ArrayList<String>>) jse.executeScript(code);

	driver.close();
	driver.quit();
//	String examScheduleHash = AppUtils.getMD5(examScheduleJson.toString());
	return examScheduleJson;
    }
}
