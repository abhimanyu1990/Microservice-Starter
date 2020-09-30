package poc.projectmgt.constraintvalidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import javax.validation.Constraint;
import javax.validation.Payload;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = UserUniqueEmailValidator.class)
@Retention(RUNTIME)
public @interface UserUniqueEmail {
    String message();
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}