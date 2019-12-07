package vn.edu.vnua.dse.stcalendar.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "calendar_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarDetail {
	// fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "schedule_hash")
	private String scheduleHash;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	// referents
	@ManyToOne
	@JoinColumn(name = "calendar_id") 
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Calendar calendar;
	
	@ManyToOne
	@JoinColumn(name = "semester_id") 
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Semester semester;

	@OneToMany(mappedBy = "calendarDetail", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude 
    private Collection<Event> events;
	//calendarDetail
	// functions
}
