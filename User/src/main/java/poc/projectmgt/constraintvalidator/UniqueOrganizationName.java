package poc.projectmgt.constraintvalidator;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = UniqueOrganizationNameValidator.class)
@Retention(RUNTIME)
public @interface UniqueOrganizationName {
	String message();
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
