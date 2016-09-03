package pl.kielce.tu.worldyouthday.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.user.RegisterEmail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {


    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void send(RegisterEmail registerEmail) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(registerEmail.to());
        helper.setFrom(mailUsername);
        message.setSubject(registerEmail.subject(), "UTF-8");
        message.setText(registerEmail.text(), "UTF-8");

        mailSender.send(message);
    }
}
