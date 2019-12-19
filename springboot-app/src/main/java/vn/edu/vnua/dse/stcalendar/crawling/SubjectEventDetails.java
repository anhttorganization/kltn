package vn.edu.vnua.dse.stcalendar.crawling;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import vn.edu.vnua.dse.stcalendar.common.AppUtils;
import vn.edu.vnua.dse.stcalendar.exceptions.CustomException;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.ExGoogleEvent;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleDateTime;
import vn.edu.vnua.dse.stcalendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi.CalendarConstant;
import vn.edu.vnua.dse.stcalendar.model.Semester;
import vn.edu.vnua.dse.stcalendar.vo.ScheduleEventVo;
import vn.edu.vnua.dse.stcalendar.vo.ScheduleEventsResult;

@Service
public final class SubjectEventDetails {
	private static final String DESCRIPTION = "Mã học phần: %s" + "\nMã lớp: %s" + "\nNhóm: %s" + "\nTuần học: %s"
			+ "\nTiết học: %s";
	private static final String DESCRIPTION_HAVE_PRACTICE = "Mã học phần: %s" + "\nMã lớp: %s" + "\nNhóm: %s"
			+ "\nNhóm thực hành: %s" + "\nTuần học: %s" + "\nTiết học: %s";
	public static String scheduleHash;
	public static Date semesterStart;
	public static String message = "";
	public static final ScheduleEventsResult getEventsFromSchedule(String studentId, Semester semester) {
		ArrayList<ArrayList<String>> scheduleJson = getSchedule(studentId, semester.getId());

		ArrayList<String> weekEvents = getWeekOfSemesEvent(semester);
		if (scheduleJson.size() > 0) {
			return new ScheduleEventsResult(toEventDetailVos(scheduleJson), weekEvents);
		} else {
			throw new CustomException("Học kỳ không có môn học nào");
		}

	}

	// Đọc lịch từ trang thời khóa biểu trả về array có các phần tử là nội dung của
	// các thẻ td
	private static final ArrayList<ArrayList<String>> getSchedule(String studentId, String semesId) {
		// Mo trinh duyet
		WebDriver driver;
		try {
			driver = ScheduleUtils.openChrome();
		} catch (IOException e1) {
			String message = "Lỗi khi mở trình duyệt Chrome";
			throw new CustomException(message);
		}

		// driver.manage().window().setPosition(new Point(-1000, -1000));
		driver.get(String.format(ScheduleConstant.SCHEDULE_URL, studentId));

		WebDriverWait wait = new WebDriverWait(driver, 50);

		// check update
		if (AppUtils.isAlertPresent(driver)) {
			Alert alert = driver.switchTo().alert();
			// Capturing alert message.
			String alertMessage = driver.switchTo().alert().getText();
			// Accepting alert
			alert.accept();

			if (!alertMessage.equals("")) {
				message += "Lỗi khi mở website thời khóa biểu";
			}
			if (alertMessage.equals("Server đang tải lại dữ liệu. Vui lòng trở lại sau 15 phút!")) {
				message += "Server đang tải lại dữ liệu. Vui lòng trở lại sau 15 phút!";
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
			message += "Lỗi khi inject Jquery";
			throw new CustomException(message);
		}
		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		String passCap;
		try {
			passCap = ScheduleUtils.readResourceFile("js/capcha.js");
		} catch (IOException e1) {
			driver.close();
			driver.quit();
			message += "Lỗi khi đọc rile chapcha.js";
			throw new CustomException(message);
		}
		jse.executeScript(passCap);
		driver.navigate().to(String.format(ScheduleConstant.SCHEDULE_URL, studentId));
		// check thong tin sinh vien
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.id(ScheduleConstant.CONTENT_MSV)));

		if (driver.findElements(By.id(ScheduleConstant.HEADER_DAOTAO_ID)).size() == 0) {
			driver.close();
			driver.quit();
			message += "Trang đạo tạo VNUA hiện không truy cập được \\nVui lòng thử lại sau!";
			throw new CustomException(message);
		}

		if (driver.findElements(By.id(ScheduleConstant.CONTENT_MSV)).size() == 0) {
			driver.close();
			driver.quit();


		}
		// chon hoc ky
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(ScheduleConstant.SCHEDULE_ELEMENT)));
		Select dropdown = new Select(driver.findElement(By.id(ScheduleConstant.SCHEDULE_ELEMENT)));
		try {
			dropdown.selectByValue(semesId);
		} catch (Exception e) {
			driver.close();
			driver.quit();

			scheduleHash = null;
			message += "Lỗi khi chọn học kỳ";
			throw new CustomException(message);
		}

		// chon che do xem theo thu tiet
		WebElement radio = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.id(ScheduleConstant.THUTIET_RADIO)));

		// Nếu không tìm được button "xem theo thứ tiết"
		if (ExpectedConditions.elementToBeClickable(radio) == null) {
			message += "Lỗi khi chọn xem theo thứ tiết";
			throw new CustomException(message);
		}

		radio.click();
		WebDriverWait wait1 = new WebDriverWait(driver, 10000);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ctl00_lblNote")));
		String semesStartDate = (String) jse
				.executeScript("return semesStart = $('#ctl00_ContentPlaceHolder1_ctl00_lblNote').text()");
		// get day string
		Pattern pattern = Pattern.compile("\\d{1,2}\\/\\d{1,2}\\/\\d{4}");
		Matcher matcher = pattern.matcher(semesStartDate);
		String semesStartDateStr = "";
		if (matcher.find()) {
			semesStartDateStr = matcher.group().trim();
		}

		try {
			semesterStart = new SimpleDateFormat("dd/MM/yyyy").parse(semesStartDateStr);
		} catch (ParseException e) {
			driver.close();
			driver.quit();
			message += "Lỗi khi đọc thời gian bắt đầu học kỳ";
			throw new CustomException(message);
		}

		String code;
		try {
			code = ScheduleUtils.readResourceFile("js/getSchedule.js");
		} catch (IOException e) {
			driver.close();
			driver.quit();
			message += "Lỗi khi đọc file getSchedule.js";
			throw new CustomException(message);
		}
		@SuppressWarnings("unchecked")
		ArrayList<ArrayList<String>> scheduleJson = (ArrayList<ArrayList<String>>) jse.executeScript(code);

		for (ArrayList<String> json : scheduleJson) {// json = 1 subject
			driver.navigate().to(json.get(14));
			@SuppressWarnings("unused")
			Long siso = (Long) jse.executeScript(
					"return $('#ctl00_ContentPlaceHolder1_ctl00_gvDSSinhVien >tbody>tr').length ?  $('#ctl00_ContentPlaceHolder1_ctl00_gvDSSinhVien >tbody>tr').length - 1: 0;");
			json.add(String.valueOf(siso));
		}

		driver.close();
		driver.quit();

		scheduleHash = AppUtils.getMD5(scheduleJson.toString());
		return scheduleJson;

	}

	public static final GoogleEvent toGoogleEvent(ScheduleEventVo eventDetailVo, String role) {

		String subjectCode = eventDetailVo.getSubjectId();
		String subjectName = eventDetailVo.getSubjectName();
		String classCode = eventDetailVo.getClazz();
		String group = eventDetailVo.getSubjectGroup();
		String practiceGroup = eventDetailVo.getPracticeGroup();
		String weeks = eventDetailVo.getWeeks();
		int day = eventDetailVo.getDay();
		ArrayList<Integer> weekStudy = ScheduleUtils.getWeek(weeks);

		int startSlot = eventDetailVo.getStartSlot();
		int endSlot = eventDetailVo.getEndSlot();
		String dssvUrl = eventDetailVo.getDdsv();
		String siSo = eventDetailVo.getSiSo();

		String summary = getSummary(subjectName, eventDetailVo.getSubjectId(), eventDetailVo.getSubjectGroup(),
				eventDetailVo.getPracticeGroup());

		String description = "";
		// phân biệt gv/sv
		if (role.equals("ROLE_STUDENT")) {
			description = getDescriptionStudent(subjectCode, classCode, group, practiceGroup, weekStudy, startSlot, endSlot);
		}else {
			 description = getDescription(subjectCode, classCode, group, practiceGroup, weekStudy, startSlot, endSlot,
					dssvUrl, siSo);
		}
		Date start = getStartTime(weekStudy.get(0), day, startSlot);
		Date end = getEnd_Time(weekStudy.get(0), day, endSlot);
		GoogleDateTime startTime = new GoogleDateTime(start, CalendarConstant.TIME_ZONE);
		GoogleDateTime endTime = new GoogleDateTime(end, CalendarConstant.TIME_ZONE);

		int count = ScheduleUtils.getFullWeek(weeks).size();
		String WEEKLY = getWEEKLY_COUNT(count);

		ArrayList<Integer> exceptWeek = ScheduleUtils.getExceptWeek(weeks);
		ArrayList<String> recurrence = new ArrayList<>();
		recurrence.add(WEEKLY);
		if (exceptWeek.size() > 0) {
			String EXDATE = getEXDATE(exceptWeek, day, startSlot);
			recurrence.add(EXDATE);
		}

		GoogleEvent googleEvent = GoogleEvent.builder().summary(summary).location(eventDetailVo.getLocation())
				.description(description).start(startTime).end(endTime).recurrence(recurrence).build();

		return googleEvent;

	}

	
	public static final List<GoogleEvent> toGoogleEvents(List<ScheduleEventVo> eventDetailVos, String role) {
		List<GoogleEvent> googleEvents = new ArrayList<GoogleEvent>();
		for (ScheduleEventVo eventDetailVo : eventDetailVos) {
			googleEvents.add(toGoogleEvent(eventDetailVo, role));
		}

		return googleEvents;

	}

	// từ list json chứa thông tin các buổi học => list event
	private static final List<ScheduleEventVo> toEventDetailVos(ArrayList<ArrayList<String>> scheduleJson) {
		List<ScheduleEventVo> events = new ArrayList<>();
		for (ArrayList<String> item : scheduleJson) {
			String classCode = item.get(4).toString().trim();
			if (!classCode.equals("")) {
				String subjectCode = item.get(0).toString().trim();
				String group = item.get(2).toString().trim();
				String practiceGroup = item.get(7).toString().trim().replace("\n", "");
				int day = ScheduleUtils.getDay(item.get(8).toString().trim());
				int startSlot = Integer.parseInt(item.get(9).toString().trim());
				int endSlot = startSlot + Integer.parseInt(item.get(10).toString().trim()) - 1;
				String weekStr = item.get(13).toString().trim();
//				ArrayList<Integer> weekStudy = ScheduleUtils.getWeek(weekStr);
				String subjectName = item.get(1).toString().trim();
				String location = item.get(11).toString();
				String dssvUrl = item.get(14).toString();
				String siSo = item.get(15).toString().trim();

				int credit = Integer.parseInt(item.get(3).toString().trim());

				ScheduleEventVo evt = ScheduleEventVo.builder()
						.eventId(String.format(CalendarConstant.EVENT_ID, new Date().getTime()))
						.subjectId(subjectCode)
						.subjectGroup(group)
						.clazz(classCode)
						.practiceGroup(practiceGroup)
						.credit(credit)
						.startSlot(startSlot)
						.endSlot(endSlot)
						.day(day)
						.weeks(weekStr)
						.subjectName(subjectName)
						.location(location)
						.ddsv(dssvUrl)
						.siSo(siSo).build();

				events.add(evt);

				System.out.println("---------------------------");
				System.out.println(evt);
			}
		}

		return events;
	}

	private static String getWEEKLY_COUNT(int count) {
		// TODO Auto-generated method stub
		String WEEKLY_COUNT = String.format(CalendarConstant.RRULE_WEEKLY_COUNT, count);
		return WEEKLY_COUNT;
	}

	private static String getDescription(String subjectCode, String classCode, String group, String practiceGroup,
			ArrayList<Integer> weekStudy, long startSlot, long endSlot, String dssv, String siso) {
		String weekDes = ScheduleUtils.joinIntArray(", ", weekStudy);
		String slotDes = startSlot + "-" + endSlot;

		String descpription = "";
		if (practiceGroup.equals("")) {
			descpription = String.format(DESCRIPTION, subjectCode, classCode, group, weekDes, slotDes) + "\n"
					+ "<a href=\"" + dssv + "\">Danh sách sinh viên</a>" + "\nSĩ số: " + siso;
		} else {
			descpription = String.format(DESCRIPTION_HAVE_PRACTICE, subjectCode, classCode, group, practiceGroup,
					weekDes, slotDes) + "\n" + "<a href=\"" + dssv + "\">Danh sách sinh viên</a>" + "\nSĩ số: " + siso;
		}
		return descpription;
	}

	//description sv
	private static String getDescriptionStudent(String subjectCode, String classCode, String group,
			String practiceGroup, ArrayList<Integer> weekStudy, int startSlot, int endSlot) {
		String weekDes = ScheduleUtils.joinIntArray(", ", weekStudy);
		String slotDes = startSlot + "-" + endSlot;

		String descpription = "";
		if (practiceGroup.equals("")) {
			descpription = String.format(DESCRIPTION, subjectCode, classCode, group, weekDes, slotDes);
		} else {
			descpription = String.format(DESCRIPTION_HAVE_PRACTICE, subjectCode, classCode, group, practiceGroup,
					weekDes, slotDes);
		}
		return descpription;
	}

	
	private static String getSummary(String subjectName, String subjectCode, String group, String practiceGroup) {
		String summary = "";
		if (practiceGroup.equals("")) {
			summary = subjectName + ", " + subjectCode + "_" + group;
		} else {
			summary = "TH " + subjectName + ", " + subjectCode + "_" + group + "_" + practiceGroup;
		}

		return summary;
	}

	private static Date getStartTime(long week, long day, int slot) {
		Date semesterDate = semesterStart; // lay ngay băt dau cua ky hoc

		String timeStr = DateTimeConstant.STARTTIME.get(slot);
		Date date = ScheduleUtils.findDay(semesterDate, week, day);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateStr = dateFormat.format(date);
		// create datetime
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateTimeFormat.setTimeZone(TimeZone.getTimeZone(CalendarConstant.TIME_ZONE));
		try {
			date = dateTimeFormat.parse(dateStr + " " + timeStr);
		} catch (ParseException e) {
			message += "Lỗi khi parse StartTime";
			throw new CustomException(message);
		}
		// set time zone

		return date;
	}

	private static Date getEnd_Time(int week, int day, int slot) {
		Date semesterDate = semesterStart; // lay ngay băt dau cua ky hoc

		String timeStr = DateTimeConstant.ENDTIME.get(slot);
		Date date = ScheduleUtils.findDay(semesterDate, week, day);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateStr = dateFormat.format(date);
		// create datetime
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateTimeFormat.setTimeZone(TimeZone.getTimeZone(CalendarConstant.TIME_ZONE));
		try {
			date = dateTimeFormat.parse(dateStr + " " + timeStr);
		} catch (ParseException e) {
			message += "Lỗi khi parse EndTime";
			throw new CustomException(message);
		}
		return date;
	}

	private static ArrayList<String> getWeekOfSemesEvent(Semester semester) {
		String semeseterName = semester.getName();

		ArrayList<String> events = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(semester.getStartDate());
		int monthStart = calendar.get(Calendar.MONTH);
		if (monthStart >= 4 && monthStart < 6) {
			for (int i = 0; i < 12; i++) {
				// set date
				Calendar calen = Calendar.getInstance();
				calen.setTime(semester.getStartDate());
				// compute
				calen.set(Calendar.DAY_OF_MONTH, calen.get(Calendar.DAY_OF_MONTH) + i * 7 + 0);// thêm vào mỗi thứ 2
				Date start = calen.getTime();
				calen.set(Calendar.DAY_OF_MONTH, calen.get(Calendar.DAY_OF_MONTH) + 1);
				Date end = calen.getTime();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String startStr = formatter.format(start);
				String endStr = formatter.format(end);
				// start, end, description, visibility
				String event = String.format(ExGoogleEvent.weekStudyEvent, "Tuần " + (i + 1), startStr, endStr,
						semeseterName, "default");
				events.add(event);
			}
		} else {
			for (int i = 0; i < 19; i++) {

				// set date
				Calendar calen = Calendar.getInstance();
				calen.setTime(semester.getStartDate());
				// compute
				calen.set(Calendar.DAY_OF_MONTH, calen.get(Calendar.DAY_OF_MONTH) + i * 7 + 0);// thêm vào mỗi thứ 2
				Date start = calen.getTime();
				calen.set(Calendar.DAY_OF_MONTH, calen.get(Calendar.DAY_OF_MONTH) + 1);
				Date end = calen.getTime();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String startStr = formatter.format(start);
				String endStr = formatter.format(end);

				String event = String.format(ExGoogleEvent.weekStudyEvent, "Tuần " + (i + 1), startStr, endStr,
						semeseterName, "default");
				events.add(event);
			}
		}

		return events;
	}

	private static String getEXDATE(ArrayList<Integer> excepWeek, int day, int startSlot) {
		ArrayList<String> EXDATE_Arr = new ArrayList<>();
		String timeStr = DateTimeConstant.STARTTIME.get(startSlot);
		for (int i = 0; i < excepWeek.size(); i++) {
			// RDATE.add(MyUtils.formatyyMMddTHHmmss(findDay(date, weekStudy.get(i), day)));
			Date result = ScheduleUtils.findDay(semesterStart, excepWeek.get(i), day);
			String dateSTr = new SimpleDateFormat("yyyy/MM/dd").format(result);

			// create datetime
			try {
				result = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(dateSTr + " " + timeStr);
			} catch (ParseException e) {
				message += "Lỗi khi parse EXDATE";
				throw new CustomException(message);
			}
			// format datetime
			EXDATE_Arr.add(ScheduleUtils.formatyyMMddTHHmmss(result));
		}
		String EXDATE = String.join(",", EXDATE_Arr);
		EXDATE = String.format(CalendarConstant.EXDATE, CalendarConstant.TIME_ZONE, EXDATE);

		return EXDATE;
	}
}
