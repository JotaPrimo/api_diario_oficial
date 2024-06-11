package com.api.diario_oficial.api_diario_oficial.services.filters.usuarios;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;

import java.util.function.Predicate;

class RolePredicate implements Predicate<Usuario> {
    private final String role;

    public RolePredicate(String role) {
        this.role = role;
    }

    @Override
    public boolean test(Usuario user) {
        return !UtilsValidators.stringIsNullOrEmpty(role) && user.getStatusUsuario().name().equalsIgnoreCase(role);
    }
}