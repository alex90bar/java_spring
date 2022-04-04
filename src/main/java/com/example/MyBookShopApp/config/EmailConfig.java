package com.example.MyBookShopApp.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

  @Value("${appEmail.email}")
  private String email;

  @Value("${appEmail.password}")
  private String password;

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(465);
    mailSender.setUsername(email);
    mailSender.setPassword(password);

    Properties props = mailSender.getJavaMailProperties();

    props.put("mail.transport.protocol", "smtps");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.ssl.enable", "true");
    props.put("mail.debug", "true");

    props.put("mail.protocol", "smtp");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
    props.put("mail.default-encoding", "UTF-8");
    props.put("mail.smtp.port", "465");

    mailSender.setJavaMailProperties(props);
    return mailSender;
  }


}
