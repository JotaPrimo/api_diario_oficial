package com.api.diario_oficial.api_diario_oficial.validation.rules.usuario.update;

import com.api.diario_oficial.api_diario_oficial.database.repository.IUsuarioRepository;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.EntityNotFoundException;

public class UserExistsValidator implements IUsuarioUpdateValidators {

    private IUsuarioRepository usuarioRepository;

    public UserExistsValidator(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validar(Usuario usuario) {
        if (!usuarioRepository.existsById(usuario.getId())) {
            throw new EntityNotFoundException(String.format("Usuário de id %s não encontrado", usuario.getId()));
        }
    }
}
