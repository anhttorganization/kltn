package vn.edu.vnua.dse.stcalendar.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkEventVo {
	private String day;
	private String date;
	private String start;
	private String end;
	private String local;
	private String content;
	private String component;
	private String leadder;
}
