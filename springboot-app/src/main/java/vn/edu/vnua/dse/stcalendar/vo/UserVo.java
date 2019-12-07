package vn.edu.vnua.dse.stcalendar.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
	private String username;
	private String firstName;
	private String lastName;
	private String avartar;
	private String faculty;
	private String clazz;
	private String password;
	private Long roleId;
}
