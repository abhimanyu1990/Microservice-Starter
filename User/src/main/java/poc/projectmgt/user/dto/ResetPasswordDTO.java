package poc.projectmgt.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import poc.projectmgt.customexceptions.GenericBadRequestException;

@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordDTO {

	@NotBlank(message = "{validation.password.notblank}")
	private String newPassword;
	private String confirmNewPassword;

	public boolean passwordMatch() {
		if (newPassword != null && newPassword.equals(confirmNewPassword)) {
			return true;
		}
		throw new GenericBadRequestException("New Password and Confirm New Password doesn't match");
	}

}
