package vn.edu.vnua.dse.stcalendar.repository.endpoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.edu.vnua.dse.stcalendar.model.User;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepositoryApi extends JpaRepository<User, Long>{

}
