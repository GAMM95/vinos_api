package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;
  private final SpringTemplateEngine templateEngine;

  @Value("${app.frontend.url}")
  private String frontendUrl;

  @Value("${app.recovery.endpoint}")
  private String appRecoveryEndpoint;

  @Override
  public void enviarRecuperacion(String destinatario, String token) throws MessagingException {

    String urlRecuperacion = frontendUrl + appRecoveryEndpoint + "?token=" + token;

    Context context = new Context();
    context.setVariable("urlRecuperacion", urlRecuperacion);

    String cuerpoHtml = templateEngine.process("email-recuperacion", context);

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

    helper.setTo(destinatario);
    helper.setSubject("Recuperación de contraseña");
    helper.setText(cuerpoHtml, true);

    helper.addInline(
        "logoCascas",
        new ClassPathResource("assets/logo_viñaCascas.png")
    );

    try {
      helper.setFrom("vinocascas9596@gmail.com", "Viña Cascas");
    } catch (Exception e) {
      helper.setFrom("vinocascas9596@gmail.com");
    }

    mailSender.send(message);
  }
}