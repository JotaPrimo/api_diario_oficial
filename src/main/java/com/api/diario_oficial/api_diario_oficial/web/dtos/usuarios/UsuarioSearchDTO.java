package com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios;

import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;

public record UsuarioSearchDTO(
        Long id,
        String username,
        String email,
        String statusUsuario,
        String role
)  {

    public static boolean isEmpty(UsuarioSearchDTO dto) {
        return (UtilsValidators.longIsNullOrZero(dto.id)) &&
                (UtilsValidators.stringIsNullOrEmpty(dto.username)) &&
                (UtilsValidators.stringIsNullOrEmpty(dto.email)) &&
                (UtilsValidators.stringIsNullOrEmpty(dto.statusUsuario)) &&
                (UtilsValidators.stringIsNullOrEmpty(dto.role));
    }

    public static UsuarioSearchDTO getNewEmptyInstance() {
        return new UsuarioSearchDTO(null, "", "", "", "");
    }

    public static UsuarioSearchDTO resolveUsuarioSearchDTO(Long id, String username, String email, String statusUsuario, String role) {
        UsuarioSearchDTO dadosParaBusca = new UsuarioSearchDTO(id, username, email, statusUsuario, role);
        if (isEmpty(dadosParaBusca)) {
            return getNewEmptyInstance();
        }
        return dadosParaBusca;
    }
}