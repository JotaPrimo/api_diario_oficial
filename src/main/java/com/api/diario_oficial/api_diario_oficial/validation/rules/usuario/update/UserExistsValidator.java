package com.api.diario_oficial.api_diario_oficial.validation.rules.usuario.update;

import com.api.diario_oficial.api_diario_oficial.database.repository.IUsuarioRepository;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.EntityNotFoundException;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.InvalidIdException;
import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;

import java.util.Objects;

public class UserExistsValidator implements IUsuarioUpdateValidators {

    private IUsuarioRepository usuarioRepository;

    public UserExistsValidator(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validate(Usuario usuario) {
        if (UtilsValidators.longIsNullOrZero(usuario.getId())) {
            throw new InvalidIdException(String.format("Usuario id %s is required", usuario.getId()));
        }

        if (!usuarioRepository.existsById(usuario.getId())) {
            throw new EntityNotFoundException(String.format("Usuário de id %s não encontrado", usuario.getId()));
        }
    }
}
