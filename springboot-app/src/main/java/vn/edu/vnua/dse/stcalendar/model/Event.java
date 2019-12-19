package vn.edu.vnua.dse.stcalendar.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    public Event(String eventId, StudyEvent studyEvent, Types type) {
	this.eventId = eventId;
	studyEvent.setEvent(this);
	this.status = true;
	this.createdAt = new Date();
	this.updatedAt = new Date();
	this.calendar = null;
	this.calendarDetail = null;
	this.type = type;
    }

    public Event(String eventId, ExamEvent examEvent, Types type) {
	this.eventId = eventId;
	examEvent.setEvent(this);
	this.type = type;
	this.status = true;
	this.createdAt = new Date();
	this.updatedAt = new Date();
	this.calendar = null;
	this.calendarDetail = null;
    }
    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "event_id")
    private String eventId;
    
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Types type;

    @Column(name = "status")
    private boolean status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public String getType() {
	return this.type.name();
    }
    
    public void setType(String type) {
	this.type = Types.valueOf(type);
    }
    

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

    @OneToOne(mappedBy = "event")
    private StudyEvent studyEvent;

    @OneToOne(mappedBy = "event")
    private ExamEvent examEvent;
}
