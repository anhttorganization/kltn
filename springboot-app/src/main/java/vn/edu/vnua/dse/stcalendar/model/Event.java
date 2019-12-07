package vn.edu.vnua.dse.stcalendar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
	// fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "event_id")
	private Long eventId;
	
	@Column(name = "subject_id")
	private String subjectId;
	
	@Column(name = "subject_group")
	private Long subjectGroup;
	
	@Column(name = "clazz")
	private String clazz;
	
	@Column(name = "practice_group")
	private Long practiceGroup;
	
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	// referents
	@ManyToOne
	@JoinColumn(name = "calendar_id") // thông qua khóa ngoại user_id
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Calendar calendar;
	
	@ManyToOne
	@JoinColumn(name = "calen_detail_id") // thông qua khóa ngoại user_id
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private CalendarDetail calendarDetail;
	// functions
}
