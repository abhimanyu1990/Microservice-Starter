package poc.projectmgt.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import poc.projectmgt.user.entities.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
	
	public Organization findByOrganizationName(String name);

}
