package com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.StatusUsuario;
import com.api.diario_oficial.api_diario_oficial.utils.DateFormats;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UsuarioResponseDTO(
        Long id,

        String username,

        String email,

        String role,

        StatusUsuario statusUsuario,

        String createdAt,

        String updatedAt
) {
    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(), usuario.getEmail(),
                usuario.getRole().name(), usuario.getStatusUsuario(),
                usuario.getCreatedAt().format(DateFormats.DMYHMS),
                usuario.getUpdatedAt().format(DateFormats.DMYHMS));
    }

    public static List<UsuarioResponseDTO> convertList(List<Usuario> usuarios) {
        List<UsuarioResponseDTO> usuarioResponseDTOS = new ArrayList<>();
        usuarios.forEach(usuario -> usuarioResponseDTOS.add(fromEntity(usuario)));
        return usuarioResponseDTOS;
    }
}
