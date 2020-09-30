package poc.projectmgt.user.dto;

import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import poc.projectmgt.enums.DefaultRoles;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeDTO {
	
	private Long id;
	 
 	@NotNull(message="{validation.privilege.name.notnull}")
    private String name;
 	@NotNull(message="{validation.privilege.value.notnull}")
    private String value;
    private String description;
    
    @NotNull(message="{validation.privilege.access.level.notnull}")
    @Enumerated(EnumType.STRING)
    private DefaultRoles accessLevel;

    
    private Set<RoleDTO> roles;
	
	
}
