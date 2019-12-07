package vn.edu.vnua.dse.stcalendar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.edu.vnua.dse.stcalendar.model.Calendar;
import vn.edu.vnua.dse.stcalendar.model.CalendarDetail;
import vn.edu.vnua.dse.stcalendar.model.Semester;

@Repository("calendarDetailRepository")
public interface CalendarDetailRepository extends JpaRepository<CalendarDetail, Long>{
	Optional<CalendarDetail> findByCalendarAndSemester(Calendar calendar, Semester semester);
	Optional<CalendarDetail> findByCalendar(Calendar calendar);
	//
	Optional<CalendarDetail> findByCalendar_IdAndSemester_Id(Long calendId, String semesterId);
	
	@Modifying
	@Query(value="DELETE FROM calendar_detail WHERE id = ?1 ", nativeQuery = true)
	void delete(Long id);
	

}
