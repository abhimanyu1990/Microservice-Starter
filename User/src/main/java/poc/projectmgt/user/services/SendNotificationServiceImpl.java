package poc.projectmgt.user.services;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendNotificationServiceImpl implements SendNotificationService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(SendNotificationServiceImpl.class);

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public void sendEmail(String[] toEmail, String[] ccEmail, String[] bccEmail, String subject, String emailBody) {
		JSONObject jsonEmailBody = new JSONObject();
		jsonEmailBody.put("toEmail", toEmail);
		jsonEmailBody.put("ccEmail", ccEmail);
		jsonEmailBody.put("bccEmail", bccEmail);
		jsonEmailBody.put("subject", subject);
		jsonEmailBody.put("body", emailBody);
		redisTemplate.opsForList().leftPush("email", jsonEmailBody.toString());
		
	}

	@Override
	public void sendEmail(String toEmail, String subject, String emailBody) {
		JSONObject jsonEmailBody = new JSONObject();
		jsonEmailBody.put("toEmail", toEmail);
		jsonEmailBody.put("subject", subject);
		jsonEmailBody.put("body", emailBody);
		redisTemplate.opsForList().leftPush("email", jsonEmailBody.toString());	
	}
	

}
