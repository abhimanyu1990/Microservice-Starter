package poc.projectmgt.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poc.projectmgt.user.entities.Organization;
import poc.projectmgt.user.repositories.OrganizationRepository;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	OrganizationRepository organizationRepository;
	
	@Override
	public boolean isOrganizationExistByName(String orgName) {
		Organization organization = organizationRepository.findByOrganizationName(orgName);
		return (organization != null) ;
	}

}
