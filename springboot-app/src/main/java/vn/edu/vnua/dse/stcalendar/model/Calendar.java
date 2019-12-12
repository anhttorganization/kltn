package vn.edu.vnua.dse.stcalendar.model;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "calendar")
@Data
@NoArgsConstructor
public class Calendar {
	
	public Calendar(User user, String studentId, String calendarId, boolean type,  Date createdAt, Date updateAt, CalendarDetail... calendarDetails) {
		this.user = user;
		this.studentId = studentId;
		this.calendarId = calendarId;
		this.type = type;
		for (CalendarDetail calendarDetail : calendarDetails)
			calendarDetail.setCalendar(this);
		this.calendarDetails = Stream.of(calendarDetails).collect(Collectors.toSet());
		this.createdAt = createdAt;
		this.updatedAt = updateAt;
	}
	
	public Calendar(User user, String studentId, String calendarId, boolean type,  Date createdAt, Date updateAt) {
		this.user = user;
		this.studentId = studentId;
		this.calendarId = calendarId;
		this.type = type;
		this.createdAt = createdAt;
		this.updatedAt = updateAt;
	}
	
	

	public Calendar(String studentId, String calendarId, boolean type, Date createdAt, Date updatedAt, User user,
			Collection<Event> events) {
		super();
		this.studentId = studentId;
		this.calendarId = calendarId;
		this.type = type;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.user = user;
		this.events = events;
	}



	// fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "student_id")
	private String studentId;

	@Column(name = "gg_calendar_id")
	private String calendarId;

	@Column(name = "type")
	private boolean type;

	@Column(name = "status")
	private boolean status;
	
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	// referents
	@ManyToOne
	@JoinColumn(name = "user_id") // thông qua khóa ngoại user_id
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private User user;

	@OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude 
    private Collection<Event> events;
	
	@OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude 
    private Collection<CalendarDetail> calendarDetails;
	// functions
	
	public void setCalendarDetails(CalendarDetail... calendarDetails) {
		for (CalendarDetail calendarDetail : calendarDetails)
			calendarDetail.setCalendar(this);
	}
	
	public void addCalendarDetails(CalendarDetail calendarDetail) {
		calendarDetail.setCalendar(this);
		this.calendarDetails.add(calendarDetail);
	}
	
	public void removeCalendarDetails(CalendarDetail calendarDetail)
	{
		this.calendarDetails.remove(calendarDetail);
	}
}
