package vn.edu.vnua.dse.stcalendar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
	private String username;
	private String fullName;
	private String faculty;
	private String clazz;
	private String studentId;

}
