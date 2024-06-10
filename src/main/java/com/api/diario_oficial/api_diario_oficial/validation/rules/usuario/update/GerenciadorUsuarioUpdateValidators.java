package com.api.diario_oficial.api_diario_oficial.validation.rules.usuario.update;

import com.api.diario_oficial.api_diario_oficial.database.repository.IUsuarioRepository;
import com.api.diario_oficial.api_diario_oficial.validation.rules.IBaseManagerValidators;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GerenciadorUsuarioUpdateValidators implements IBaseManagerValidators {

    private IUsuarioRepository usuarioRepository;

    private List<IUsuarioUpdateValidators> usuarioValidators;

    public GerenciadorUsuarioUpdateValidators(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<IUsuarioUpdateValidators> getUsuarioValidators() {
        return usuarioValidators;
    }

    public void setUsuarioValidators(List<IUsuarioUpdateValidators> usuarioValidators) {
        this.usuarioValidators = usuarioValidators;
    }

    @Override
    @PostConstruct
    public void defineListValidators() {
        List<IUsuarioUpdateValidators> updateValidators = new ArrayList<>();
        updateValidators.add(new UserExistsValidator(usuarioRepository));

        setUsuarioValidators(updateValidators);
    }

}
