package poc.projectmgt.constraintvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import poc.projectmgt.user.services.OrganizationService;

public class UniqueOrganizationNameValidator implements ConstraintValidator<UniqueOrganizationName,String>{

	@Autowired
	OrganizationService organizationService;
	
	@Override
    public void initialize(UniqueOrganizationName unique) {
        unique.message();
    }
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {	
		return !(organizationService != null && organizationService.isOrganizationExistByName(value));
	}
}
