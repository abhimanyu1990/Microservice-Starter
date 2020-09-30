package poc.projectmgt.user.entities;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import poc.projectmgt.constraintvalidator.UniqueOrganizationName;

@Entity
@Table(name="organizations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Organization extends BasicEntity {
	
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	Long id;
	
	@NotNull(message="{validation.organization.name.notnull}")
	@UniqueOrganizationName(message = "{validation.organization.name.duplicate}")
	private String organizationName;
	
	@ManyToMany(fetch = FetchType.LAZY,mappedBy="organizations")
	@JsonIgnore
	private Set<User> users;

}
