package vn.edu.vnua.dse.stcalendar.repository.endpoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.edu.vnua.dse.stcalendar.model.SlotTime;

@RepositoryRestResource(collectionResourceRel = "slottimes", path = "slottimes")
public interface SlotTimeRepositoryApi extends JpaRepository<SlotTime, Long>{

}
