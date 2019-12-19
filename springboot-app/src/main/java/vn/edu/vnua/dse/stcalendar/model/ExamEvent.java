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
@Table(name = "exam_event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamEvent {
    
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "subject_code")
    private String subjectCode;
    
    @Column(name = "combined")
    private String combined;
    
    @Column(name = "exam_team")
    private String examTeam;
    
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Event event;

}
