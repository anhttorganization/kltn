package vn.edu.vnua.dse.stcalendar.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "study_event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyEvent {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "subject_code")
    private String subjectCode;

    @Column(name = "subject_group")
    private String subjectGroup;

    @Column(name = "clazz")
    private String clazz;

    @Column(name = "practice_group")
    private String practiceGroup;

    @Column(name = "credit")
    private int credit;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Event event;
}
