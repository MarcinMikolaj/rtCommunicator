package project.rtc.utils;

import javax.mail.MessagingException;

public interface MailSenderService {
	
	public void sendMessage(String to, String subject, String text) throws MessagingException;

}
