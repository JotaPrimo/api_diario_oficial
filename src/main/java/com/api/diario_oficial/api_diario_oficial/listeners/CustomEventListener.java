package com.api.diario_oficial.api_diario_oficial.listeners;

import com.api.diario_oficial.api_diario_oficial.events.UsuarioCriadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomEventListener {

    @EventListener
    public void handleCustomEvent(UsuarioCriadoEvent event) {
        System.out.println("Received custom event - " + event.getUsuario().getUsername());
    }

}
