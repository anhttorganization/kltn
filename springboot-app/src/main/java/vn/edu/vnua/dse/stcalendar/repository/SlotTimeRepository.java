package vn.edu.vnua.dse.stcalendar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.vnua.dse.stcalendar.model.SlotTime;

@Repository("slotTimeRepository")
public interface SlotTimeRepository extends JpaRepository<SlotTime, Long> {
	
    Optional<SlotTime> findById(Long id);
    
}
