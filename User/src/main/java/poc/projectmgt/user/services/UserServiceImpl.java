package poc.projectmgt.user.services;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import poc.projectmgt.JwtTokenProvider;
import poc.projectmgt.customexceptions.GenericAuthenticationException;
import poc.projectmgt.customexceptions.GenericBadRequestException;
import poc.projectmgt.customexceptions.GenericNotFoundException;
import poc.projectmgt.enums.DefaultRoles;
import poc.projectmgt.enums.UserStatus;
import poc.projectmgt.user.dto.ResetPasswordDTO;
import poc.projectmgt.user.entities.EmailVerificationToken;
import poc.projectmgt.user.entities.ResetPasswordToken;
import poc.projectmgt.user.entities.Role;
import poc.projectmgt.user.entities.User;
import poc.projectmgt.user.repositories.EmailVerificationTokenRepository;
import poc.projectmgt.user.repositories.ResetPasswordTokenRepository;
import poc.projectmgt.user.repositories.RoleRepository;
import poc.projectmgt.user.repositories.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Value("${email.verificationtoken.expiry}")
	int emailVerificationTokenExpiryDurationInhours;
	
	@Value("${resetpassword.verificationtoken.expiry}")
	int resetPasswordTokenExpiryDurationInhours;
	
	@Value("${service.url}")
	String serviceUrl;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private EmailVerificationTokenRepository emailVerificationTokenRepository;
	
	@Autowired
	private ResetPasswordTokenRepository resetPasswordTokenRepository;
	
	@Autowired
	private SendNotificationService sendNotificationService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public User addUser(User user) {
		validatePassword(user.getPassword());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		addDefaultRole(user);
		user.setStatus(UserStatus.ACTIVE);
		user.setAccountLocked(true);
		generatEmailVerificationToken(user.getEmail());
		return userRepository.saveAndFlush(user);
	}
	
	private User addDefaultRole(User user) {
		Set<Role> roles = roleRepository.findByName(DefaultRoles.valueOf("ROLE_USER").toString());
		user.setRoles(roles);
		return user;
		
	}
	
	private void generatEmailVerificationToken(String email) {
		EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
		Date date = new Date();
		long emailVerificationTokenExpiryDurationInMilliSeconds = emailVerificationTokenExpiryDurationInhours*3600000L;
		long expiryTime = date.getTime()+emailVerificationTokenExpiryDurationInMilliSeconds;
		date.setTime(expiryTime);
		emailVerificationToken.setExpiryDate(date);
		emailVerificationToken.setToken(UUID.randomUUID().toString());
		emailVerificationToken.setEmail(email);
		emailVerificationTokenRepository.save(emailVerificationToken);
		sendEmailVerificationLink(emailVerificationToken.getToken(), email);
	}
	
	private void sendEmailVerificationLink(String token, String email) {
		String emailVerificationURL=serviceUrl+"/api/v1/verifyemail/"+token;
		LOGGER.info("Email Verification Url -->"+emailVerificationURL);
		String subject = "Verify your email";
		String emailBody =" Please click on below Url to verify your email \n "+emailVerificationURL+".";
		sendNotificationService.sendEmail(email,subject ,emailBody);
	}

	@Override
	public String signin(String email, String password) {
			try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			User user  = userRepository.findByEmail(email);
			if(user.isAccountLocked()) {
				throw new GenericAuthenticationException("Account is locked. Please reverify your email");
			}
			return jwtTokenProvider.createToken(email);
		} catch (AuthenticationException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new GenericAuthenticationException(e.getMessage());
		}
	}

	@Override
	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new GenericNotFoundException("User not found Exception");
		}
		return user;
		
	}
	
	@Override
	public boolean validateEmailVerificationToken(String token) {
		EmailVerificationToken emailVerificationToken = emailVerificationTokenRepository.findByToken(token);
		Date date = new Date();
		if(emailVerificationToken == null) {
			throw new GenericNotFoundException("Email verification token doesn't exist or your account is already verified.");
		}
		if(date.before(emailVerificationToken.getExpiryDate())) {
			User user = userRepository.findByEmail(emailVerificationToken.getEmail());
			user.setAccountLocked(false);
			userRepository.save(user);
			emailVerificationTokenRepository.delete(emailVerificationToken);
			return true;
		}
		return false;
	}

	
	private void generateResetPasswordLink(String resetToken,String email) {
		String resetPasswordURL=serviceUrl+"/api/v1/resetPassword/"+resetToken;
		LOGGER.info("Reset Password Link -->"+resetPasswordURL);
		String subject = "Reset Password Link";
		String emailBody =" Please click on below Url to reset your password\n "+resetPasswordURL+".";
		sendNotificationService.sendEmail(email,subject ,emailBody);
	}

	@Override
	public void generateResetPasswordToken(String email) {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new GenericNotFoundException("User not found.");
		}
		ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
		String resetToken = UUID.randomUUID().toString();
		Date date = new Date();
		long resetPasswordTokenExpiryDurationInMilliSeconds = resetPasswordTokenExpiryDurationInhours*3600000L;
		long expiryTime = date.getTime()+resetPasswordTokenExpiryDurationInMilliSeconds;
		date.setTime(expiryTime);
		resetPasswordToken.setEmail(user.getEmail());
		resetPasswordToken.setExpiryDate(date);
		resetPasswordToken.setToken(resetToken);
		resetPasswordTokenRepository.save(resetPasswordToken);
		generateResetPasswordLink(resetToken,email);
		
	}

	@Override
	public void resetPassword(String resetToken, ResetPasswordDTO resetPasswordDTO) {
		if(resetPasswordDTO.passwordMatch()) {
			ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(resetToken);
			Date date = new Date();
			if(resetPasswordToken != null && date.before(resetPasswordToken.getExpiryDate()) && validatePassword(resetPasswordDTO.getNewPassword())) {
				User user = userRepository.findByEmail(resetPasswordToken.getEmail());
				user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
				userRepository.save(user);
				resetPasswordTokenRepository.delete(resetPasswordToken);
				return;
			}
			throw new GenericBadRequestException("Token is invalid");
		}
	}
	
	
	private boolean validatePassword(String password) {
		boolean isValidPassword = true;
		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
		Pattern p = Pattern.compile(regex);
		if (password == null) {
			isValidPassword = false;
		} else {
			Matcher m = p.matcher(password);
			isValidPassword = m.matches();
		}
		if (isValidPassword) {
			return isValidPassword;
		}
		throw new GenericBadRequestException("Password validation failed. Example value 'StrongP@ssword12'");
	}

	
}
