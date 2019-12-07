package vn.edu.vnua.dse.stcalendar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.vnua.dse.stcalendar.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long>{
	public Optional<User> findByUsername(String username);
}
