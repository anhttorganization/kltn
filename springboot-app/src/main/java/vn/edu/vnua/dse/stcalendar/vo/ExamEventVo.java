package vn.edu.vnua.dse.stcalendar.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamEventVo {

    private Long id;
    private String eventId;

    private String subjectCode;
    private String subjectName;
    private String combined;
    private String examTeam;
    private int startSlot;
    private int endSlot;
    private String date;
    private String location;

}
