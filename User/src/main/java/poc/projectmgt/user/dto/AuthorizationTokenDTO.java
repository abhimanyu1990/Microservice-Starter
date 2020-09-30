package poc.projectmgt.user.dto;

import org.springframework.stereotype.Component;

@Component
public class AuthorizationTokenDTO {
	String authToken;
	String email;

	public AuthorizationTokenDTO(String email,String authToken) {
		this.email=email;
		this.authToken=authToken;
	}
	
	public AuthorizationTokenDTO() {
		
	}
	public String getAuthToken() {
		return authToken;
	}

	public void setToken(String authToken) {
		this.authToken = authToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
