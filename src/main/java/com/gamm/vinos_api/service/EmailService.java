package com.gamm.vinos_api.service;

import jakarta.mail.MessagingException;

public interface EmailService {
  void enviarRecuperacion(String destinatario, String token) throws MessagingException;
}
