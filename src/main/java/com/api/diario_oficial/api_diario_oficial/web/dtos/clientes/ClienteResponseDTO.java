package com.api.diario_oficial.api_diario_oficial.web.dtos.clientes;

import com.api.diario_oficial.api_diario_oficial.entity.Cliente;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClienteResponseDTO(
        Long id,

        String username,

        String email,

        UsuarioResponseDTO usuario,

        String createdAt,

        String updatedAt
) {

    public static ClienteResponseDTO fromEntity(Cliente cliente, UsuarioResponseDTO usuarioResponseDTO) {
        return new ClienteResponseDTO(0L, "", "", usuarioResponseDTO, "", "");
    }
}
