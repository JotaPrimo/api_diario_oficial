package com.api.diario_oficial.api_diario_oficial.validation.usuario.store;

import com.api.diario_oficial.api_diario_oficial.database.repository.IUsuarioRepository;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.EmailAlreadyRegistered;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioCreateValidatorEmail implements IUsuarioCreateValidator {

    private IUsuarioRepository usuarioRepository;

    public UsuarioCreateValidatorEmail(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validate(Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioOptional.isPresent()) {
            throw new EmailAlreadyRegistered("Email já cadastrado");
        }
    }

}
