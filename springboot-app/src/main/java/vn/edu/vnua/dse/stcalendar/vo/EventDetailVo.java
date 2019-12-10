package vn.edu.vnua.dse.stcalendar.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDetailVo {
	private Long id;

	private String eventId;
	
	private String subjectId;
	
	private String subjectGroup;
	
	private String clazz;
	
	private String practiceGroup;
	
	private int credit;
	
	private int startSlot;
	
	private int endSlot;
	
	private int day;
	
	private String weeks;
	
	private String subjectName;

	private String location;
	
	private String ddsv;
	
	private String siSo;

	
}
