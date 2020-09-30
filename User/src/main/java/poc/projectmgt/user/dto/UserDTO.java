package poc.projectmgt.user.dto;


import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import poc.projectmgt.constraintvalidator.UserUniqueEmail;
import poc.projectmgt.customexceptions.GenericBadRequestException;
import poc.projectmgt.enums.UserStatus;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
	
	@ApiModelProperty(required = false) 
	private Long id;

	@NotNull(message = "{validation.firstname.notnull}")
	@ApiModelProperty(required = true) 
	private String firstName;

	private String middleName;

	
	private String lastName;

	@ApiModelProperty(hidden=true)
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@ApiModelProperty(hidden=true)
	private boolean isAccountLocked;

	@ApiModelProperty(value="Password must have length between 8-20 character. Password must be Alphanumeric. "
			+ "It must have atleast 1 capital and small letter.Password must have one special charater:  (?=.*[@#$%^&-+=()]")
	private String password;
	
	
	private String confirmPassword;

	@ApiModelProperty(required = true) 
	@Email(message = "{validation.email}")
	@Column(unique = true)
	@UserUniqueEmail(message = "{validation.email.unique}")
	private String email;

	
	
	public boolean passwordMatch() {
		if (password != null && password.equals(confirmPassword)) {
			return true;
		}
		throw new GenericBadRequestException("Password and Confirm Password doesn't match");
	}
	
	

}
