package com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.enums.StatusUsuario;
import com.api.diario_oficial.api_diario_oficial.validation.custom.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCreateDTO(

        @NotBlank(message = "Username é um campo obrigatório")
        @Size(min = 5, max = 250, message = "Username deve ter entre {min} e {max} caracteres")
        String username,

        @NotBlank(message = "Email é um campo obrigatório")
        @Size(min = 5, max = 250, message = "Email deve ter entre {min} e {max} caracteres")
        String email,

        @NotBlank(message = "Password é um campo obrigatório")
        @Size(min = 8, max = 10, message = "Password deve ter entre {min} e {max} caracteres")
        String password,

        @NotBlank(message = "Role é um campo obrigatório")
        @ValueOfEnum(enumClass = Role.class, message = "Role inválida")
        String role
) {
    public static Usuario toEntity(UsuarioCreateDTO usuarioCreateDTO) {
        Role role1 = Role.valueOf(usuarioCreateDTO.role);
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioCreateDTO.username);
        usuario.setEmail(usuarioCreateDTO.email);
        usuario.setPassword(usuarioCreateDTO.password);
        usuario.setStatusUsuario(StatusUsuario.INATIVO);
        usuario.setRole(role1);
        return usuario;
    }

    public static UsuarioCreateDTO getNewInstance() {
        return new UsuarioCreateDTO("", "", "", "");
    }

}