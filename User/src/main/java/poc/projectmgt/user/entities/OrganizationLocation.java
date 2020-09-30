package poc.projectmgt.user.entities;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="organization_locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@NotBlank(message="{validation.addressline1.notnull}")
	private String addressLine1;
	private String addressLine2;
	@NotBlank(message="{validation.city.notnull}")
	private String city;
	@NotBlank(message="{validation.country.notnull}")
	private String country;
	@NotBlank(message="{validation.pincode.notnull}")
	private String pincode;
	
	@NotNull
	private String locationName;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Organization organization;
}
