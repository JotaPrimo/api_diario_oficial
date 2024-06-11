package com.api.diario_oficial.api_diario_oficial.services.filters.usuarios;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioSearchDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioFilter {

    public List<Usuario> filterUsers(UsuarioSearchDTO searchDto, List<Usuario> usuarios) {
        return usuarios.stream()
                .filter(new IdPredicate(searchDto.id())).collect(Collectors.toList());
                        // .and(new UsernamePredicate(searchDto.username()))
                        // .and(new EmailPredicate(searchDto.email()))
                        // .and(new StatusUsuarioPredicate(searchDto.statusUsuario()))
                        // .and(new RolePredicate(searchDto.role())))

    }
}
