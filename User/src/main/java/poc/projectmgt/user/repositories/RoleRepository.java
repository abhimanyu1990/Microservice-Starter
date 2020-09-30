package poc.projectmgt.user.repositories;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import poc.projectmgt.user.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
	Page<Role> findAll(Pageable page);
	Set<Role> findByName(String name);
}
