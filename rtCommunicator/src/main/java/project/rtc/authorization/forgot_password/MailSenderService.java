package project.rtc.authorization.forgot_password;

import javax.mail.MessagingException;

public interface MailSenderService {
	
	public void sendMessage(String to, String subject, String text) throws MessagingException;

}
