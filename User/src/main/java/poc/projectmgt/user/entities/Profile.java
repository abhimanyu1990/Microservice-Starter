package poc.projectmgt.user.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends BasicEntity{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_id_generator")
	private Long id;
	
	@NotBlank(message="{validation.addressline1.notnull}")
	private String permanentAddressLine1;

	private String permanentAddressLine2;
	@NotBlank(message="{validation.city.notnull}")
	private String permanentCity;
	@NotBlank(message="{validation.country.notnull}")
	private String permanentCountry;
	@NotBlank(message="{validation.pincode.notnull}")
	private String permanentPincode;
	
	private String about;
	
}
