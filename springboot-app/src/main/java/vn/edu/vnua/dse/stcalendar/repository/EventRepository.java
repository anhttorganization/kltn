package vn.edu.vnua.dse.stcalendar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.vnua.dse.stcalendar.model.CalendarDetail;
import vn.edu.vnua.dse.stcalendar.model.Event;

@Repository("eventRepository")
public interface EventRepository extends JpaRepository<Event, Long> {
	Optional<Event> findByEventId(String eventId);

	Optional<Event> findByCalendarDetail(CalendarDetail calendarDetail);

	
//	@Transactional
//	Long deleteByCalenDetail(CalendarDetail calenDetail);
//	
//	@Query(value = "select * from event e where e.calenDetailId = ?1", nativeQuery = true)
//	Optional<Event> findByCalenId(String calenDetailId);
//
//	@Modifying
//	@Transactional
//	@Query("delete from Event e where e.calenDetail = ?1")
//	void deleteByCalendarDetail(CalendarDetail calenDetail);

}
