package poc.projectmgt.user.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import poc.projectmgt.constraintvalidator.UserUniqueEmail;
import poc.projectmgt.enums.UserStatus;
import poc.projectmgt.user.utils.UserSequenceIdGenerator;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BasicEntity {

	private static final long serialVersionUID = 1L;

	// entity properties
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
	@GenericGenerator(name = "user_id_generator", strategy = "poc.projectmgt.user.utils.UserSequenceIdGenerator", parameters = {
			@Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = UserSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = ""),
			@Parameter(name = UserSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private Long id;

	@NotNull(message = "{validation.firstname.notnull}")
	private String firstName;

	private String middleName;

	private String lastName;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	private boolean isAccountLocked;

	@JsonIgnore
	private String password;

	@Email(message = "{validation.email}")
	@Column(unique = true)
	@UserUniqueEmail(message = "{validation.email.unique}")
	private String email;

	@ManyToMany
	@JoinTable(name="users_roles", joinColumns={@JoinColumn(referencedColumnName="id")}
     , inverseJoinColumns={@JoinColumn(referencedColumnName="id")})
	private Set<Role> roles;

	@ManyToMany
	@JoinTable(name="organizations_users", joinColumns={@JoinColumn(referencedColumnName="id")}
     , inverseJoinColumns={@JoinColumn(referencedColumnName="id")})
	private Set<Organization> organizations;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_id", nullable = true, insertable = true, updatable = true)
	private Profile profile;

}
