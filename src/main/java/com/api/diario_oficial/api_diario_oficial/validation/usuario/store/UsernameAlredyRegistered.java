package com.api.diario_oficial.api_diario_oficial.validation.usuario.store;

import com.api.diario_oficial.api_diario_oficial.database.repository.IUsuarioRepository;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.UserNameUniqueViolationException;

import java.util.Optional;

public class UsernameAlredyRegistered implements IUsuarioCreateValidator {

    private IUsuarioRepository usuarioRepository;

    public UsernameAlredyRegistered(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validate(Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(usuario.getUsername());

        if (usuarioOptional.isPresent()) {
            throw new UserNameUniqueViolationException("Username já cadastrado");
        }
    }
}
