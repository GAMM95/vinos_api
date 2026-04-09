package com.gamm.vinos_api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {
  private final SimpMessagingTemplate messagingTemplate;

  public void notifyDashboardUpdate() {
    messagingTemplate.convertAndSend("/topic/dashboard", "update");
  }

  public void notifyMercaderiaUpdate(){
    messagingTemplate.convertAndSend("/topic/mercaderia", "update");
  }

  public void notifyCompraUpdate(){
    messagingTemplate.convertAndSend("/topic/compras", "update");
  }

  public void notifyCajaUpdate(){
    messagingTemplate.convertAndSend("/topic/caja", "update");
  }

  public void notifyVentaUpdate(){
    messagingTemplate.convertAndSend("/topic/ventas", "update");
  }

  public void notifyPrecioUpdate(){
    messagingTemplate.convertAndSend("/topic/precio", "update");
  }

  public void notifyUsuarioUpdate(){
    messagingTemplate.convertAndSend("/topic/usuario", "update");
  }
}
