package rw.dyna.ecommerce.v1.servicesImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;
import rw.dyna.ecommerce.v1.exceptions.BadRequestException;
import rw.dyna.ecommerce.v1.models.Mail;
import org.thymeleaf.context.Context;
import rw.dyna.ecommerce.v1.models.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String appEmail;

    @Value("${spring.mail.appname}")
    private String appName;

    @Value("${spring.mail.clientHost}")
    private String clientHost;

    public MailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendAccountVerificationEmail (User user) throws MessagingException {
        String link = clientHost + "/verify-email?email=" + user.getEmail() + "&code=" + user.getActivationCode();

        Mail mail = new Mail(
                appName,
                "Welcome to "+appName+", Verify your email to continue",
                user.getFirstName(), user.getEmail(), "verify-email",link);
        sendEmail(mail);
    }

    @Async
    public void sendResetPassword(User user){
        String link = clientHost + "/reset-password?email=" + user.getEmail() + "&token=" + user.getResetPasswordToken();
        Mail mail = new Mail(appName, "Verify Your email to complete password reset", user.getFirstName(), user.getEmail(), "verify-email", link);
        sendEmail(mail);
    }


    public void sendEmail(Mail mail){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariable("app_name", appName);
            context.setVariable("data", mail.getData());
            context.setVariable("name", mail.getFullNames());
            context.setVariable("otherData", mail.getOtherData());
            message.setFrom();

            String html = templateEngine.process(mail.getTemplate(), context);

            messageHelper.setTo(mail.getToEmail());
            messageHelper.setText(html, true);
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setFrom(appEmail);

            mailSender.send(message);
        }catch (MessagingException e){
            throw  new BadRequestException("Failed to send an email!");
        }
    }

}
