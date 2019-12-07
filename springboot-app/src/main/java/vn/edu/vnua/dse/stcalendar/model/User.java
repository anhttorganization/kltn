package vn.edu.vnua.dse.stcalendar.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	// fields
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")//email
	private String username;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "avatar")
	private String avatar;
	
	@Column(name = "faculty")
	private String faculty;
	
	@Column(name = "clazz ")
	private String clazz;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "enabled")
	private boolean enabled;
	
	@Column(name = "gg_refresh_token")
	private String ggRefreshToken;
	
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	// referents
	 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	    @EqualsAndHashCode.Exclude
	    @ToString.Exclude 
	    @JsonIgnore
	    private Collection<Calendar> calendars;
	 
	 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // Quan hệ 1-n với đối tượng ở dưới (Calendar) (1 user có nhiều calendar)
	    // MappedBy trỏ tới tên biến User ở trong Post.
	    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
	    @ToString.Exclude // Không sử dụng trong toString()
	    @JsonIgnore
	    private Collection<Post> posts;
	 
		@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	    // Quan hệ n-n với đối tượng ở dưới (Role) (1 người có thể có nhiều quền)
	    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
	    @ToString.Exclude // Không sử dụng trong toString()
	    
	    @JoinTable(name = "user_role", //Tạo ra một join Table tên là "user_role"
	            joinColumns = @JoinColumn(name = "user_id"),  // TRong đó, khóa ngoại chính là user_id trỏ tới class hiện tại (Role)
	            inverseJoinColumns = @JoinColumn(name = "role_id") //Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới (Person)
	    )
	    private Collection<Role> roles;
	// functions
}
