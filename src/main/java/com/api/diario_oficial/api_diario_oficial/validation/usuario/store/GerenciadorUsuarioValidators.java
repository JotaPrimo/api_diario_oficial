package com.api.diario_oficial.api_diario_oficial.validation.usuario.store;

import com.api.diario_oficial.api_diario_oficial.database.repository.IUsuarioRepository;
import com.api.diario_oficial.api_diario_oficial.validation.IBaseManagerValidators;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GerenciadorUsuarioValidators implements IBaseManagerValidators {

    private IUsuarioRepository usuarioRepository;

    private List<IUsuarioCreateValidator> usuarioValidators;

    public GerenciadorUsuarioValidators(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<IUsuarioCreateValidator> getUsuarioValidators() {
        return usuarioValidators;
    }

    public void setUsuarioValidators(List<IUsuarioCreateValidator> usuarioValidators) {
        this.usuarioValidators = usuarioValidators;
    }

    @Override
    @PostConstruct
    public void defineListValidators() {
        List<IUsuarioCreateValidator> validators = new ArrayList<>();
        validators.add(new UsuarioCreateValidatorEmail(usuarioRepository));
        validators.add(new UsernameAlredyRegistered(usuarioRepository));

        setUsuarioValidators(validators);
    }

}
