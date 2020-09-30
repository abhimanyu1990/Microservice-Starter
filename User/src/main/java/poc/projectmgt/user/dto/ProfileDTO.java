package poc.projectmgt.user.dto;



import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
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
