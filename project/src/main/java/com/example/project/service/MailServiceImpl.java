package com.example.project.service;

import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.config.MailProperties;
import com.example.project.util.Constants;

import freemarker.template.Configuration;

@Service
public class MailServiceImpl implements MailService {
	
    private final MailProperties mailProperties;
    
    private final Configuration templates;

    @Autowired
    MailServiceImpl(MailProperties mailProperties, Configuration templates){
        this.mailProperties = mailProperties;
        this.templates = templates;
    }

	@Override
	public int sendOtpToMail(String mailId) {
		
        String subject = Constants.subject;
        int otp = generateOtpNumberForEmail();
        String body = Constants.body + "-" + otp;
		boolean mailFlag = sendMail(mailId, subject, body);
		if(mailFlag){
			return otp;
		}else{
			return 0;
		}
	}

    public boolean sendMail(String toMail, String subject, String body) {
        try {
            Properties props = System.getProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", mailProperties.getSmtp().getPort());
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);
            session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mailProperties.getFrom(), mailProperties.getFromName()));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
            msg.setSubject(subject);
            msg.setContent(body, "text/html");

            Transport transport = session.getTransport();
            transport.connect(mailProperties.getSmtp().getHost(), mailProperties.getSmtp().getUsername(), mailProperties.getSmtp().getPassword());
            transport.sendMessage(msg, msg.getAllRecipients());
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        return false;
    }

    public int generateOtpNumberForEmail(){
    	Random random = new Random();
    	int otp = 10000 + random.nextInt(90000);
    	return otp;
   }
}
