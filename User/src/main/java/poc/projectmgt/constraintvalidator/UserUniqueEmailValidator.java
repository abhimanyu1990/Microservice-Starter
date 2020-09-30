package poc.projectmgt.constraintvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import poc.projectmgt.user.services.UserService;

public class UserUniqueEmailValidator implements ConstraintValidator<UserUniqueEmail,String> {

	@Autowired
	UserService userService;
	
	@Override
    public void initialize(UserUniqueEmail unique) {
        unique.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !(userService != null && userService.existsByEmail(email));
    }
}
