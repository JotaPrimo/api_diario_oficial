package com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios;

import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;
import com.api.diario_oficial.api_diario_oficial.web.dtos.interfaces.IRecordSearch;

public record UsuarioSearchDTO(
        Long id,
        String username,
        String email,
        String statusUsuario,
        String role
) implements IRecordSearch {

    @Override
    public boolean isEmpty() {
        return (UtilsValidators.longIsNullOrZero(id)) &&
                (UtilsValidators.stringIsNullOrEmpty(username)) &&
                (UtilsValidators.stringIsNullOrEmpty(email)) &&
                (UtilsValidators.stringIsNullOrEmpty(statusUsuario)) &&
                (UtilsValidators.stringIsNullOrEmpty(role));
    }

    @Override
    public boolean isAnyFieldFilled() {
        return !isEmpty();
    }

    public static UsuarioSearchDTO getNewEmptyInstance() {
        return new UsuarioSearchDTO(null, "", "", "", "");
    }


    public static UsuarioSearchDTO resolveUsuarioSearchDTO(UsuarioSearchDTO usuarioSearchDTO) {
        if (usuarioSearchDTO.isEmpty()) {
            return getNewEmptyInstance();
        }
        return usuarioSearchDTO;
    }
}