package com.api.diario_oficial.api_diario_oficial.services.filters.usuarios;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;

import java.util.function.Predicate;

public class UsernamePredicate implements Predicate<Usuario> {

    private final String username;

    public UsernamePredicate(String username) {
        this.username = username;
    }

    @Override
    public boolean test(Usuario user) {
        return !UtilsValidators.stringIsNullOrEmpty(username) && (user.getCliente() != null && user.getCliente().getNome().toLowerCase().contains(username.toLowerCase()));
    }

}
