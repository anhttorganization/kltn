package vn.edu.vnua.dse.stcalendar.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private String eventId;
	
	@Column(name = "subject_id")
	private String subjectId;
	
	@Column(name = "subject_group")
	private String subjectGroup;
	
	@Column(name = "clazz")
	private String clazz;
	
	@Column(name = "practice_group")
	private String practiceGroup;
	
	@Column(name = "credit")
	private int credit;
	
	@Column(name = "start_slot")
	private int startSlot;
	
	@Column(name = "end_slot")
	private int endSlot;
	
	@Column(name = "status")
	private boolean status;
	
	
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	// referents
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "evt_calendar_id") // thông qua khóa ngoại calendar_id
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Calendar calendar;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "calen_detail_id") // thông qua khóa ngoại calendar_detail_id
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private CalendarDetail calendarDetail;
	// functions
}
