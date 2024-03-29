package vn.edu.vnua.dse.stcalendar.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "semester")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Semester {
	
	
	public Semester(String id) {
		super();
		this.id = id;
	}

	public Semester(String id, String name, Date startDate) {
		this.id = id;
		this.name = name;
		this.startDate = startDate;
	}

	// fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private String id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "status")
	private boolean status;
	
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	// referents
	@OneToMany(mappedBy = "semester", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude 
    private Collection<CalendarDetail> calendarDetails;
	// functions
}
