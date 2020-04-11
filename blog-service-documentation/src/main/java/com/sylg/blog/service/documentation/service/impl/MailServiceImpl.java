package com.sylg.blog.service.documentation.service.impl;

import com.sylg.blog.service.documentation.common.dto.TemplateContext;
import com.sylg.blog.service.documentation.Factory.TemplateContextFactory;
import com.sylg.blog.service.documentation.domain.BlogUser;
import com.sylg.blog.service.documentation.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @program: blog
 * @description: 邮件发送服务
 * @author: 忆地球往事
 * @create: 2020-03-27 21:23
 **/
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Resource
    private TemplateContextFactory templateContextFactory;

    @Value("${spring.mail.from}")
    private String from;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setFrom(from);
        message.setTo(to);
        message.setSentDate(new Date());
        message.setText(content);
        mailSender.send(message);
    }

    @Override
    public void sendSimpleMail(String to, String subject, String content, String... cc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    @Override
    @Async("mailTaskExecutor")
    public void sendAsyncHtmlMail(BlogUser blogUser,BlogUser blogUser1,String subject, HttpServletRequest request) {
        TemplateContext templateContext = new TemplateContext();
        templateContext.setUsername(blogUser1.getUserName());
        String process = templateContextFactory.process(templateContext);
        try {
            sendHtmlMail(blogUser.getEmail(),subject,process);
        } catch (MessagingException e) {
            log.error("邮件发送失败原因",e);
        }
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setSentDate(new Date());
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content, String... cc) {

    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath){

    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc) {

    }

    @Override
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId) {

    }

    @Override
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc) {

    }
}
