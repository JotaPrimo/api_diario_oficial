package com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.validation.custom.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UsuarioUpdateDTO(

        @NotBlank(message = "Username é um campo obrigatório")
        @Size(min = 5, max = 250, message = "Username deve ter entre {min} e {max} caracteres")
        String username,

        @NotBlank(message = "Email é um campo obrigatório")
        @Size(min = 5, max = 250, message = "Email deve ter entre {min} e {max} caracteres")
        String email,

        @ValueOfEnum(enumClass = Role.class, message = "Informe uma role inválida")
        String role
) {

    public static Usuario toEntity(UsuarioUpdateDTO usuarioUpdateDTO, Usuario usuario) {
        usuario.setUsername(usuarioUpdateDTO.username);
        usuario.setEmail(usuarioUpdateDTO.email);
        usuario.setRole(Role.valueOf(usuarioUpdateDTO.role));
        return usuario;
    }

    public static UsuarioUpdateDTO getNewInstance() {
        return new UsuarioUpdateDTO("", "", "");
    }

}