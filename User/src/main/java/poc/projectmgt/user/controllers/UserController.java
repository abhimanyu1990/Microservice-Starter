package poc.projectmgt.user.controllers;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poc.projectmgt.customexceptions.GenericCustomException;
import poc.projectmgt.customexceptions.GenericNotFoundException;
import poc.projectmgt.user.dto.ResetPasswordDTO;
import poc.projectmgt.user.dto.UserDTO;
import poc.projectmgt.user.entities.User;
import poc.projectmgt.user.mapper.UserMapper;
import poc.projectmgt.user.repositories.UserRepository;
import poc.projectmgt.user.services.UserService;




@RestController
@RequestMapping("/api/v1")
public class UserController {
	 
	@Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	 UserMapper userMapper;
	 
	
    @Transactional
    @PreAuthorize("isMemberAndHaveAuthority(#id,'PERMISSION_READ_ANY_USER')")
	@GetMapping(value = "/org/{id}/users")
	public List<User> getUsers(@PathVariable Long id) {
		return userRepository.findAll();
		
	}
	
	
	@GetMapping(value = "/users/{id}")
	public User getUsersById(@PathVariable Long id) {
		return userRepository.findById(id).orElseThrow(() ->  new GenericNotFoundException("User not found with Id: "+id));
	
	}
	
	
	@PostMapping("/user")
	public UserDTO create( @RequestBody @Valid UserDTO userDTO) {
		User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
		user = userService.addUser(user);
		if(user != null) {
			return UserMapper.INSTANCE.userToUserDTO(user);
		}
	    throw new GenericCustomException("Not able to fullfil the request. Kindly try later or contact support");
	  }
	
	
	
	@PostMapping("/forgotpassword/{email}")
	public Map<String,Object> forgotPasssword(@PathVariable("email") @NotNull String email) {
		userService.generateResetPasswordToken(email);
		HashMap<String,Object> map = new HashMap<>();
		map.put("isScuccess", true);
		map.put("message","Please check your email for reset password link.");
		return map;
	}
	
	
	@PostMapping("/resetpassword/{resetToken}")
	public Map<String,Object> resetPassword(@PathVariable("resetToken") @NotNull String resetToken,@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
		
		userService.resetPassword(resetToken,resetPasswordDTO);
		HashMap<String,Object> map = new HashMap<>();
		map.put("isScuccess", true);
		map.put("message","Your Password is reset successfully");
		return map;
	}
	 

}

