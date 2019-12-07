package vn.edu.vnua.dse.stcalendar.crawling;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import vn.edu.vnua.dse.stcalendar.common.AppConstant;
import vn.edu.vnua.dse.stcalendar.common.AppUtils;

@Service
public class ScheduleUtils {
	public static WebDriver openChrome() throws IOException {
		Properties prop = AppUtils.MyProperties(AppConstant.CALENDAR_APP_PRO);
		String driverName = prop.getProperty("crawling.driverName");
		
		String os = System.getProperty("os.name");
		String driverPath="";
		if(os.contains("Windows")) {
			driverPath = prop.getProperty("crawling.driverPath", null);				//chay tren window			
		}else {
			driverPath = prop.getProperty("crawling.driverPathLinux", null);//chay tren linux			
		}
		System.setProperty(driverName, driverPath);
		
//		DesiredCapabilities chrome=DesiredCapabilities.chrome();
//		chrome.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//		return new ChromeDriver(chrome);
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--no-sandbox");
		String chromePath = prop.getProperty("crawling.chromePath", null);
		options.setBinary(chromePath);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		ChromeDriver driver = new ChromeDriver(capabilities);
		return driver;
	}

	public static String readFile(String file) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(file);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}
	
	public static String readResourceFile(String file) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		Resource resource = new ClassPathResource(file);
//		File x = resource.getFile();
		InputStream stream = resource.getInputStream();
		//FileInputStream stream = new FileInputStream(file);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}

	public static void injectJQuery(WebDriver driver, String jQueryStr) throws IOException {
		String jQueryLoader = ScheduleUtils.readFile(jQueryStr);
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeAsyncScript(jQueryLoader);
	}
	
	public static void injectResourceJQuery(WebDriver driver, String jQueryStr) throws IOException {
		String jQueryLoader = ScheduleUtils.readResourceFile(jQueryStr);
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeAsyncScript(jQueryLoader);
	}

	public static int getDay(String day) {
		int result = -1;
		String[] daysOfWeek = new String[] { "Hai", "Ba", "Tư", "Năm", "Sáu", "Bảy", "CN" };
		for (int i = 0; i < daysOfWeek.length; i++) {
			if (day.equals(daysOfWeek[i])) {
				result = i;
			}
		}
		return result;
	}

	public static ArrayList<Integer> getWeek(String week) {
		ArrayList<Integer> weeks = new ArrayList<>();

		for (int i = 0; i < week.toCharArray().length; i++) {
			if ('-' != week.toCharArray()[i]) {
				weeks.add(i + 1);
			}
		}
		return weeks;
	}
	
	public static ArrayList<Integer> getFullWeek(String week) {
		ArrayList<Integer> studyWeeks = getWeek(week);
		ArrayList<Integer> fullWeeks = new ArrayList<>();
		
		int minWeek = Collections.min(studyWeeks);
		int maxWeek = Collections.max(studyWeeks);
		for(int i = minWeek; i <= maxWeek; i++) {//full week
			fullWeeks.add(i);
		}
		
		return fullWeeks;
	}
	
	public static ArrayList<Integer> getExceptWeek(String week) {
		ArrayList<Integer> studyWeeks = getWeek(week);
		ArrayList<Integer> exceptWeeks = getFullWeek(week);
		
		exceptWeeks.removeAll(studyWeeks);
		return exceptWeeks;
	}
	public static String joinIntArray(String delimiter, ArrayList<Integer> numbers) {
		StringBuilder builder = new StringBuilder();
		// Append all Integers in StringBuilder to the StringBuilder.
		for (int number : numbers) {
			builder.append(number);
			builder.append(delimiter);
		}
		// Remove last delimiter with setLength.
		builder.setLength(builder.length() - delimiter.length());
		return builder.toString();
	}

	public static String formatyyMMddTHHmmss(Date date) {
		String fomat = "yyyyMMdd'T'HHmmss";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fomat);

		String dateStr = simpleDateFormat.format(date);

		return dateStr;
	}

	public static Date findDay(Date startSemester, int week, int day) {
		// set date
		Calendar calen = Calendar.getInstance();
		calen.setTime(startSemester);
		// compute
		calen.set(Calendar.DAY_OF_MONTH, calen.get(Calendar.DAY_OF_MONTH) + (week - 1) * 7 + day);

		return calen.getTime();
	}

}
