package project.rtc.utils.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderServiceImpl implements MailSenderService {
	
	private JavaMailSender javaMailSender;
	
	@Autowired
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void sendMessage(String to, String subject, String text) throws MessagingException {
		
		try {
			
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
			
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setText(text);
			mimeMessageHelper.setSubject(subject);
			
			javaMailSender.send(mimeMessage);
			
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
	}

}
