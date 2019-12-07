package vn.edu.vnua.dse.stcalendar.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.edu.vnua.dse.stcalendar.model.Semester;

@Repository("semesterRepository")
public interface SemesterRepository extends JpaRepository<Semester, String> {
	Optional<Semester> findById(String id);
	
	@Query(
	value = "SELECT * FROM semester ORDER BY startDate DESC LIMIT 3", 
	nativeQuery = true)
	ArrayList<Semester> fin3CurrentSemester();


}
