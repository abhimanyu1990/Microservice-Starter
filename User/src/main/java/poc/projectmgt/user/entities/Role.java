package poc.projectmgt.user.entities;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BasicEntity {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message="{validation.role.name.notnull}")
	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;
	
	@NotNull(message="{validation.role.name.representation.notnull}")
	private String representationName;

	@ManyToMany(fetch = FetchType.LAZY,mappedBy="roles")
	@JsonIgnore
	private Set<User> users;
	
	@ManyToOne
	@JoinColumn(name = "organization_id", nullable = false)
	private Organization organization;
	
	@ManyToMany
	@JoinTable(name="roles_privileges", joinColumns={@JoinColumn(referencedColumnName="id")}
    , inverseJoinColumns={@JoinColumn(referencedColumnName="id")})
	private Set<Privilege> privileges;


}
