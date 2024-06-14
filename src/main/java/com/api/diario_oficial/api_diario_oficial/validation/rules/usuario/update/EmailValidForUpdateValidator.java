package com.api.diario_oficial.api_diario_oficial.validation.rules.usuario.update;

import com.api.diario_oficial.api_diario_oficial.database.repository.IUsuarioRepository;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.UserNameUniqueViolationException;

public class EmailValidForUpdateValidator implements IUsuarioUpdateValidators {

    private IUsuarioRepository usuarioRepository;

    public EmailValidForUpdateValidator(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validar(Usuario usuario) {
        if(usuarioRepository.existsByEmailAndIdNot(usuario.getEmail(), usuario.getId())) {
            throw new UserNameUniqueViolationException("Email inválido");
        }
    }
}
