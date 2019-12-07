package vn.edu.vnua.dse.stcalendar.repository.endpoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.edu.vnua.dse.stcalendar.model.Semester;

@RepositoryRestResource(collectionResourceRel = "semesters", path = "semesters")
public interface SemesterRepositoryApi extends JpaRepository<Semester, String> {

}
