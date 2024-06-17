package com.api.diario_oficial.api_diario_oficial.web.dtos.clientes;

import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioSearchDTO;

public record ClienteSearchDTO(
        Long id,
        String nome,
        String username,
        String email,
        Long orgaoGovernamentalId
) {
    public static boolean isEmpty(ClienteSearchDTO dto) {
        return (UtilsValidators.longIsNullOrZero(dto.id)) &&
                (UtilsValidators.stringIsNullOrEmpty(dto.username)) &&
                (UtilsValidators.stringIsNullOrEmpty(dto.email)) &&
                (UtilsValidators.longIsNullOrZero(dto.orgaoGovernamentalId));
    }

    public static ClienteSearchDTO getNewEmptyInstance() {
        return new ClienteSearchDTO(null, "", "", "", null);
    }

    public static ClienteSearchDTO resolveClienteSearchDTO(Long id, String nome, String username, String email, Long orgaoGovernamentalId) {
        ClienteSearchDTO dadosParaBusca = new ClienteSearchDTO(id, nome, username, email, orgaoGovernamentalId);
        if (isEmpty(dadosParaBusca)) {
            return getNewEmptyInstance();
        }
        return dadosParaBusca;
    }
}
