package com.api.diario_oficial.api_diario_oficial.validation.rules.usuario.update;

import com.api.diario_oficial.api_diario_oficial.database.repository.IUsuarioRepository;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.UserNameUniqueViolationException;

public class UsernameValidForUpdateValidator implements IUsuarioUpdateValidators {

    private IUsuarioRepository usuarioRepository;

    public UsernameValidForUpdateValidator(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validar(Usuario usuario) {
        if(usuarioRepository.existsByUsernameAndIdNot(usuario.getUsername(), usuario.getId())) {
            throw new UserNameUniqueViolationException("Username inválido");
        }
    }
}
