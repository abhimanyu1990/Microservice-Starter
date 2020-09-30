package poc.projectmgt.user.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import poc.projectmgt.enums.DefaultRoles;
import poc.projectmgt.user.entities.Organization;
import poc.projectmgt.user.entities.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege,Long>{
	Page<Privilege> findAll(Pageable pageable);
	List<Privilege> findAllByAccessLevel(DefaultRoles accessLevel);
	List<Privilege> findAllByOrganization(Organization organization);
	List<Privilege> findAllByOrganizationAndAccessLevel(Organization organization,DefaultRoles accessLevel);
 }
