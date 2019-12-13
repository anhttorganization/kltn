package vn.edu.vnua.dse.stcalendar.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.edu.vnua.dse.stcalendar.crawling.ScheduleUtils;
import vn.edu.vnua.dse.stcalendar.exceptions.CustomException;
import vn.edu.vnua.dse.stcalendar.vo.WorkEventVo;

@Controller
@RequestMapping
public class WorkScheduleController {
	
	@RequestMapping(value = "api/calendar/working/create", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<ArrayList<WorkEventVo>> create(){
		//lay lich
		WebDriver driver;
		try {
			driver = ScheduleUtils.openChrome();
		} catch (IOException e1) {
			String message = "Lỗi khi mở trình duyệt Chrome";
			throw new CustomException(message);
		}

		// driver.manage().window().setPosition(new Point(-1000, -1000));
		driver.get("https://vphv.vnua.edu.vn");

		WebDriverWait wait = new WebDriverWait(driver, 25);
		
		try {
			ScheduleUtils.injectResourceJQuery(driver, "js/MyJQuery.js");
		} catch (IOException e1) {
			driver.close();
			driver.quit();
			String message = "Lỗi khi inject Jquery";
			throw new CustomException(message);
		}
		
		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		String code;
		//			code = ScheduleUtils.readResourceFile("js/getSchedule.js");
		code = "var oTable= $('.ListEventCalendar')[0];\r\n" + 
				"var data = [...oTable.rows].map(t => [...t.children].map(t => $(t).find('>div').length !== 0 ? [...t.children].map(u => u.innerText) : t.innerText))\r\n" + 
				"data.shift()\r\n" + 
				"var time = \"\";\r\n" + 
				"var data2 = data.map(row => {if(row.length == 3) time = row[0]; if(row.length == 2) row.unshift(time); return row})\r\n" + 
				"var data3 = [];\r\n" + 
				"\r\n" + 
				"data2.map(a => {if(a[1][0]!=\"\") data3.push(a)})\r\n" + 
				"data3.map(a => a.splice(1,1,...a[1]))\r\n" + 
				"\r\n" + 
				"data3.map(a=>{\r\n" + 
				"	\r\n" + 
				"	//lấy giờ\r\n" + 
				"	var str = a[1];\r\n" + 
				"	var res = str.match(/([0-1][0-9]|2[0-4]):[0-5][0-9]/g);\r\n" + 
				"	a.splice(1, 0, ...res);\r\n" + 
				"	a[3] =	a[3].replace(/([0-1][0-9]|2[0-4]):[0-5][0-9] - ([0-1][0-9]|2[0-4]):[0-5][0-9]: /g, '');	\r\n" + 
				"	if( a.length == 6) \r\n" + 
				"	{\r\n" + 
				"		a.splice(1,0,a[1]);\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"	//lấy ngày\r\n" + 
				"	a.splice(1,0,a[0].match(/([1-2]?[0-9]|3[0-1])\\/(1[0-2]|[0-9])/g)[0] + '/' + new Date().getFullYear())\r\n" + 
				"	\r\n" + 
				"	return a;\r\n" + 
				"})\r\n" + 
				"\r\n" + 
				"return data3;";
				ArrayList<ArrayList<String>> scheduleJson = (ArrayList<ArrayList<String>>) jse.executeScript(code);
				
				ArrayList<WorkEventVo> lst = new ArrayList<WorkEventVo>();
				for(ArrayList<String> event : scheduleJson) {
					WorkEventVo vo = WorkEventVo.builder()
							.day(event.get(0))
							.date(event.get(1))
							.start(event.get(2))
							.end(event.get(3))
							.local(event.get(4))
							.content(event.get(5))
							.component(event.get(6))
							.leadder(event.get(7))
							.build();
					
					lst.add(vo);
				}
				driver.close();
				driver.quit();

		return ResponseEntity.ok().body(lst);
	}
}




//service

