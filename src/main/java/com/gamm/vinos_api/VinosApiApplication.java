package com.gamm.vinos_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync   // ← necesario para que @Async funcione
public class VinosApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VinosApiApplication.class, args);
    }

}
