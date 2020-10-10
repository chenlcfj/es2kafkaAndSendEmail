package es.log_reprocess;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Slf4j
@Component
public class EmailServiceImpl implements EmailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.mail.username}")
    private String FROM;

    @Autowired
    private JavaMailSender javaMailSender;

    /* TODO 简单邮件发送 */
    @Override
    public void sendSimpleMail(String[] to, String[] cc, String subject, String content) {
        logger.info("发送简单邮件");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM);
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(content);
        try {
            javaMailSender.send(message);
            logger.info("简单邮件发送成功");
        } catch (Exception e) {
            logger.error("简单邮件发送异常!", e);
        }
    }

    /* TODO 发送HTML邮件 */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            //true表示需要创建一个 multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(FROM);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(message);
            logger.info("HTML邮件发送成功");
        } catch (MessagingException e) {
            logger.error("HTML邮件发送异常!", e);
        }
    }

    /* TODO 发送带附件邮件 */
    @Override
    public void sendFileMail(String to, String subject, String content, String filePath) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(FROM);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            javaMailSender.send(message);
            logger.info("带附件邮件发送成功");
        } catch (MessagingException e) {
            logger.error("带附件邮件发送异常!", e);
        }
    }

    /* TODO 发送静态资源邮件 */
    @Override
    public void sendStaticResourceMail(String to, String subject, String content, String resourcePath, String resourceId) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(FROM);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource resource = new FileSystemResource(new File(resourcePath));
            helper.addInline(resourceId, resource);

            javaMailSender.send(message);
            logger.info("静态资源邮件发送成功");
        } catch (MessagingException e) {
            logger.error("静态资源邮件发送异常!", e);
        }
    }
}