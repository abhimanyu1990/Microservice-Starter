package poc.projectmgt.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
	@Email(message="{validation.email}")
	@NotBlank(message="{validation.email.notblank}")
	String email;
	@NotBlank(message="{validation.password.notblank}")
	String password;
	
}
