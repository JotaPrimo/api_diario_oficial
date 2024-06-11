package com.api.diario_oficial.api_diario_oficial.services.filters.usuarios;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;

import java.util.function.Predicate;

class IdPredicate implements Predicate<Usuario> {
    private final Long id;

    public IdPredicate(Long id) {
        this.id = id;
    }

    @Override
    public boolean test(Usuario user) {
        return !UtilsValidators.longIsNullOrZero(id) && user.getId().equals(id);
    }
}