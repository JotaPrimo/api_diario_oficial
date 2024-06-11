package com.api.diario_oficial.api_diario_oficial.services.filters.usuarios;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;

import java.util.function.Predicate;

public class StatusUsuarioPredicate implements Predicate<Usuario> {

    private final String statusUsuario;

    public StatusUsuarioPredicate(String statusUsuario) {
        this.statusUsuario = statusUsuario;
    }

    @Override
    public boolean test(Usuario user) {
        return !UtilsValidators.stringIsNullOrEmpty(statusUsuario) && user.getStatusUsuario().name().equalsIgnoreCase(statusUsuario);
    }

}
