package com.api.diario_oficial.api_diario_oficial.events;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import org.springframework.context.ApplicationEvent;

public class UsuarioCriadoEvent extends ApplicationEvent {

    private final Usuario usuario;

    public UsuarioCriadoEvent(Object source, Usuario usuario) {
        super(source);
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
