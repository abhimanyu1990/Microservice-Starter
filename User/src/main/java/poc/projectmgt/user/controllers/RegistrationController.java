package poc.projectmgt.user.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import poc.projectmgt.customexceptions.GenericCustomException;
import poc.projectmgt.customexceptions.GenericNotFoundException;
import poc.projectmgt.user.dto.UserDTO;
import poc.projectmgt.user.entities.User;
import poc.projectmgt.user.mapper.UserMapper;
import poc.projectmgt.user.services.UserService;

@RestController
@RequestMapping("/api/v1")
public class RegistrationController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Anyone can register using email and password")
	@PostMapping("/register")
	public UserDTO register(@Valid @RequestBody UserDTO userDTO) {
		LOGGER.debug("Request--> register user: "+userDTO.toString());
		User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
		if (userDTO.passwordMatch()) {
			user = userService.addUser(user);
		}

		if (user != null)
			return UserMapper.INSTANCE.userToUserDTO(user);
		throw new GenericCustomException("Not able to fullfil the request. Kindly try later or contact support");
	}

	@PostMapping("/verifyemail/{token}")
	@ApiOperation(value = "Use to verify whether email exists or not. Return object {'isEmailVerified':boolean,'message':String}")
	public Map<String,Object> verifyEmail(@Valid @PathVariable String token) {
		 if(userService.validateEmailVerificationToken(token)) {
			 HashMap<String,Object> map = new HashMap<>();
			 map.put("message","Your email has been successfully verified");
			 map.put("isEmailVerified", true);
			 return map;
		 }
		 throw new GenericNotFoundException("Token is invalid.");
	}

}
