package com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.StatusUsuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record UsuarioResponseDTO(
        Long id,

        String nome,

        String email,

        StatusUsuario statusUsuario,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(), usuario.getEmail(), usuario.getStatusUsuario(), usuario.getCreatedAt(), usuario.getUpdatedAt());
    }

    public static List<UsuarioResponseDTO> convertList(List<Usuario> usuarios) {
        List<UsuarioResponseDTO> usuarioResponseDTOS = new ArrayList<>();
        usuarios.forEach(usuario -> usuarioResponseDTOS.add(fromEntity(usuario)));
        return usuarioResponseDTOS;
    }
}
