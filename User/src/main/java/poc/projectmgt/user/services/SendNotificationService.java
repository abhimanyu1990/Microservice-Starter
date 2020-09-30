package poc.projectmgt.user.services;

public interface SendNotificationService {
   public void sendEmail(String[] toEmail, String[] ccEmail, String[] bccEmail, String subject, String emailBody );
   public void sendEmail(String toEmail, String subject, String emailBody);
	
}
