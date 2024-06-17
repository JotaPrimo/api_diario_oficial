package com.api.diario_oficial.api_diario_oficial.web.dtos.enderecos;

import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;

public record EnderecoSearchDTO(
        Long id,
        String logradouro,
        String cidade,
        String estado,
        String cep
) {

    public static boolean isEmpty(EnderecoSearchDTO dto) {
        return (UtilsValidators.longIsNullOrZero(dto.id)) &&
                (UtilsValidators.stringIsNullOrEmpty(dto.logradouro)) &&
                (UtilsValidators.stringIsNullOrEmpty(dto.cidade)) &&
                (UtilsValidators.stringIsNullOrEmpty(dto.estado)) &&
                (UtilsValidators.stringIsNullOrEmpty(dto.cep));
    }

    public static EnderecoSearchDTO getNewEmptyInstance() {
        return new EnderecoSearchDTO(null, "", "", "", "");
    }

    public static EnderecoSearchDTO resolveEnderecoSearchDTO(Long id, String logradouro, String cidade, String estado, String cep) {
        EnderecoSearchDTO dadosParaBusca = new EnderecoSearchDTO(id, logradouro, cidade, estado, cep);
        if (isEmpty(dadosParaBusca)) {
            return getNewEmptyInstance();
        }
        return dadosParaBusca;
    }
}
