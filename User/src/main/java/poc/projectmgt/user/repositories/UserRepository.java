package poc.projectmgt.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import poc.projectmgt.user.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {
	Boolean existsByEmail(String email);
	User findByEmail(String email);
	List<User> findAll();
}
