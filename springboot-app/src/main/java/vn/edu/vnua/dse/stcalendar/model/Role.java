package vn.edu.vnua.dse.stcalendar.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
	// fields
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "id")
		private Long id;
		
		@Column(name = "name")
		private String name;
		
		  // mappedBy trỏ tới tên biến persons ở trong Address.
	    @ManyToMany(mappedBy = "roles")
	    // LAZY để tránh việc truy xuất dữ liệu không cần thiết. Lúc nào cần thì mới query
	    @EqualsAndHashCode.Exclude
	    @ToString.Exclude
	    @JsonIgnore
	    private Collection<User> users;
}
