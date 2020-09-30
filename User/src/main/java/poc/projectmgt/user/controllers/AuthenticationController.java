package poc.projectmgt.user.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poc.projectmgt.customexceptions.GenericAuthenticationException;
import poc.projectmgt.user.dto.AuthorizationTokenDTO;
import poc.projectmgt.user.dto.LoginDTO;
import poc.projectmgt.user.services.UserService;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {
	
	 @Autowired
	 private UserService userService;
		
	  @PostMapping("/signin")
	  public AuthorizationTokenDTO signin(@Valid @RequestBody LoginDTO login) {
		  String authToken = userService.signin(login.getEmail(), login.getPassword());
		  if(authToken != null) {
			  return new AuthorizationTokenDTO(login.getEmail(),authToken);
		  }
		  
	      throw new GenericAuthenticationException("Email or password doesn't exist.");
	  }
}
