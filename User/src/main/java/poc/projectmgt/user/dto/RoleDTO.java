package poc.projectmgt.user.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

	private Long id;


	private String name;

	private String description;
	
	
	private String representationName;


	private Set<UserDTO> users;
	

	private Set<PrivilegeDTO> privileges;

}
