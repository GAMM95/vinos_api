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

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  // Inyectamos la base de la URL y el endpoint desde properties
  @Value("${app.url.base}")
  private String appBaseUrl;

  @Value("${app.recovery.endpoint}")
  private String appRecoveryEndpoint;

//  @Override
//  public void enviarRecuperacion(String destinatario, String token) throws MessagingException {
//    // Construimos la URL dinámica de recuperación
//    String urlRecuperacion = appBaseUrl + appRecoveryEndpoint + "?token=" + token;
//
//    String asunto = "Recuperación de contraseña";
//    String cuerpo = """
//        <html>
//        <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
//            <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
//                <h2 style="color: #333;">Recuperación de contraseña</h2>
//                <p>Hola,</p>
//                <p>Has solicitado recuperar tu contraseña. Haz clic en el siguiente botón para establecer una nueva contraseña:</p>
//                <p style="text-align: center; margin: 30px 0;">
//                    <a href="%s" style="background-color: #007bff; color: #ffffff; padding: 12px 20px; text-decoration: none; border-radius: 5px; display: inline-block;">
//                        Restablecer contraseña
//                    </a>
//                </p>
//                <p>Este enlace expira en 1 hora.</p>
//                <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;">
//                <p style="font-size: 12px; color: #888;">Si no solicitaste este cambio, puedes ignorar este correo.</p>
//            </div>
//        </body>
//        </html>
//        """.formatted(urlRecuperacion);
//
//    MimeMessage message = mailSender.createMimeMessage();
//    MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
//
//    helper.setTo(destinatario);
//    helper.setSubject(asunto);
//    helper.setText(cuerpo, true); // false = texto plano | true = html
//
//    try {
//      helper.setFrom("vinocascas9596@gmail.com", "Viña Cascas");
//    } catch (java.io.UnsupportedEncodingException e) {
//      // Manejo simple: usamos solo la dirección si falla el encoding
//      helper.setFrom("vinocascas9596@gmail.com");
//    }
//
//    mailSender.send(message);
//  }

  @Override
  public void enviarRecuperacion(String destinatario, String token) throws MessagingException {
    String urlRecuperacion = appBaseUrl + appRecoveryEndpoint + "?token=" + token;

    String cuerpoHtml = """
        <html>
        <body style="font-family: Arial, sans-serif; background-color: #fbf4f8; padding: 20px;">
            <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 30px; border-radius: 10px; box-shadow: 0 0 15px rgba(0,0,0,0.1); border-top: 5px solid #800000;">
                <img src='cid:logoCascas' alt='Viña Cascas' style='width:150px; display:block; margin:auto; margin-bottom: 20px;'>
                <h2 style="color: #800000; text-align: center;">Recuperación de contraseña</h2>
                <p style="color: #333;">Hola,</p>
                <p style="color: #333;">Has solicitado recuperar tu contraseña. Haz clic en el siguiente botón para establecer una nueva contraseña:</p>
                <p style="text-align: center; margin: 30px 0;">
                    <a href="%s" style="background-color: #800000; color: #FFFFFF; font-weight: bold; padding: 12px 25px; text-decoration: none; border-radius: 5px; display: inline-block;">
                        Restablecer contraseña
                    </a>
                </p>
                <p style="color: #333;">Este enlace expira en 1 hora.</p>
                <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;">
                <p style="font-size: 12px; color: #888; text-align: center;">Si no solicitaste este cambio, puedes ignorar este correo.</p>
            </div>
        </body>
        </html>
    """.formatted(urlRecuperacion);

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); // true = HTML

    helper.setTo(destinatario);
    helper.setSubject("Recuperación de contraseña");
    helper.setText(cuerpoHtml, true);

    // Adjuntar logo inline
    ClassPathResource logo = new ClassPathResource("assets/logo_viñaCascas.png");
    helper.addInline("logoCascas", logo);

    try {
      helper.setFrom("vinocascas9596@gmail.com", "Viña Cascas");
    } catch (java.io.UnsupportedEncodingException e) {
      helper.setFrom("vinocascas9596@gmail.com");
    }

    mailSender.send(message);
  }

}
