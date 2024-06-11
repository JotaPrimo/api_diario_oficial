package com.api.diario_oficial.api_diario_oficial.services.filters.usuarios;

import java.util.function.Predicate;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;

class EmailPredicate implements Predicate<Usuario> {
    private final String email;

    public EmailPredicate(String email) {
        this.email = email;
    }

    @Override
    public boolean test(Usuario user) {
        return UtilsValidators.stringIsNullOrEmpty(email) || user.getEmail().toLowerCase().contains(email.toLowerCase());
    }
}