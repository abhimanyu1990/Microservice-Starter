package poc.projectmgt.user.services;


import poc.projectmgt.user.dto.ResetPasswordDTO;
import poc.projectmgt.user.entities.User;

public interface UserService {
	public boolean existsByEmail(String email);
	public User addUser(User user);
	public String signin(String email, String password);
	public User findUserByEmail(String email);
	public boolean validateEmailVerificationToken(String emailToken);
	public void generateResetPasswordToken(String email);
	public void resetPassword(String resetToken, ResetPasswordDTO resetPasswordDTO);
}
