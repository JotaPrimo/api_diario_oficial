package com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioLoginDTO(
        @NotBlank
        String username,

        @NotBlank
        @Size(min = 8, max = 8)
        String password
) {

}
