package poc.projectmgt.user.entities;


import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import poc.projectmgt.enums.DefaultRoles;

@Entity
@Table(name="privileges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Privilege extends BasicEntity {

	 	
	private static final long serialVersionUID = 1L;

		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	 
	 	@NotNull(message="{validation.privilege.name.notnull}")
	    private String name;
	 	@NotNull(message="{validation.privilege.value.notnull}")
	    private String value;
	    private String description;
	    
	    @NotNull(message="{validation.privilege.access.level.notnull}")
	    @Enumerated(EnumType.STRING)
	    private DefaultRoles accessLevel;

	    @ManyToMany(fetch = FetchType.LAZY, mappedBy="privileges")
	    @JsonIgnore
	    private Set<Role> roles;
	    
	    @ManyToOne
		@JoinColumn(name = "organization_id", nullable = false)
		private Organization organization;
		
	}