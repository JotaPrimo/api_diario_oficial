package com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.StatusUsuario;
import com.api.diario_oficial.api_diario_oficial.utils.DateFormats;

import java.util.ArrayList;
import java.util.List;

public record UsuarioResponseDTO(
        Long id,

        String nome,

        String email,

        StatusUsuario statusUsuario,

        String createdAt,
        String updatedAt
) {
    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(), usuario.getEmail(),
                usuario.getStatusUsuario(),
                usuario.getCreatedAt().format(DateFormats.DMYHMS),
                usuario.getUpdatedAt() != null ? usuario.getUpdatedAt().format(DateFormats.DMYHMS) : null );
    }

    public static List<UsuarioResponseDTO> convertList(List<Usuario> usuarios) {
        List<UsuarioResponseDTO> usuarioResponseDTOS = new ArrayList<>();
        usuarios.forEach(usuario -> usuarioResponseDTOS.add(fromEntity(usuario)));
        return usuarioResponseDTOS;
    }
}
