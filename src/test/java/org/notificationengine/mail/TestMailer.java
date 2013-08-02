package org.notificationengine.mail;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.notificationengine.mail.Mailer;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import static org.junit.Assert.assertTrue;

public class TestMailer {

	private Mailer mailer;
	
	@Before
	public void init() {
		
		SimpleMailMessage templateMessage = new SimpleMailMessage();
		templateMessage.setFrom("rgirodon@sqli.com");
		templateMessage.setSubject("Notification Engine Test Mail");
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("rgirodon@sqli.com");
		mailSender.setPassword("*****");
		
		Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.timeout", "8500");
        mailSender.setJavaMailProperties(properties);
		
		this.mailer = new Mailer();
		
		this.mailer.setMailSender(mailSender);
		
		this.mailer.setTemplateMessage(templateMessage);
	}
	
	@Test
	public void testSendMail() {

        assertTrue(this.mailer.sendMail("rgirodon@sqli.com", "Default Test Mail Content."));
	}

    @Test
    public void testSendMailWithSubject() {

        assertTrue(this.mailer.sendMail("rgirodon@sqli.com", "Mail with a different subject", "NotificationEngine Test with custom subject"));
    }

    @Test
    public void testSendMailWithSubjectAndFromField() {

        // TODO : this test is not failing but "from" field is the admin one.

        assertTrue(this.mailer.sendMail("rgirodon@sqli.com",
                "Mail with a different subject and from field",
                "Notification Engine - Custom subject and from fields",
                "rgirodon2000@yahoo.fr"));

    }

}
