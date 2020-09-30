package poc.projectmgt.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import poc.projectmgt.user.entities.EmailVerificationToken;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
	
	public EmailVerificationToken findByEmail(String email);
	public EmailVerificationToken findByToken(String token);

}
