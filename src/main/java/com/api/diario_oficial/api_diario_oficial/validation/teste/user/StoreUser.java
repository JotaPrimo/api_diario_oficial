package com.api.diario_oficial.api_diario_oficial.validation.teste.user;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.EntityNotFoundException;
import com.api.diario_oficial.api_diario_oficial.services.implementations.UsuarioServiceImpl;

public class StoreUser {

    private Usuario usuario;

    private UsuarioServiceImpl usuarioService;

    public StoreUser(Usuario usuario) {
        this.usuario = usuario;
    }

    public void applyValidate() throws Exception {

        if (usuario == null) {
            throw new EntityNotFoundException("Usuario não pode ser null");
        }

        this.validadarUsername()
                .validadarEmail()
                .validadarPassword();
    }

    public StoreUser validadarUsername() throws Exception {
        if (usuario.getUsername() != null && usuario.getUsername().isBlank()) {
            return this;
        }
        throw new Exception("username não pode ser null");
    }

    public StoreUser validadarEmail() throws Exception {
        if (usuario.getEmail() != null && usuario.getEmail().isBlank()) {
            return this;
        }
        throw new Exception("Email não pode ser null");
    }

    public StoreUser validadarPassword() throws Exception {
        if (usuario.getPassword() != null && usuario.getPassword().isBlank()) {
            return this;
        }
        throw new Exception("Password não pode ser null");
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
