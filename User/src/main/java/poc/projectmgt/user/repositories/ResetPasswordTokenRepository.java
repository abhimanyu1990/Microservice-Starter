package poc.projectmgt.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import poc.projectmgt.user.entities.ResetPasswordToken;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
	
	public ResetPasswordToken findByEmail(String email);
	public ResetPasswordToken findByToken(String resetToken);

}
