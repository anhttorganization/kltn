package vn.edu.vnua.dse.stcalendar.repository.endpoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.edu.vnua.dse.stcalendar.model.Role;

@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
public interface RoleRepositoryApi extends JpaRepository<Role, Long>{

}
