package vn.edu.vnua.dse.stcalendar.vo;

import java.util.Collection;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.vnua.dse.stcalendar.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoVo {
		private Long id;

		private String username;
		
		private String firstName;
		
		private String lastName;
		
		private String avatar;
		
		private String faculty;
		
		private String clazz;
		
		private Date createdAt;

		private Date updatedAt;
		
		private String role;
}
