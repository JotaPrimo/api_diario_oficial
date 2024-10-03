package com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental;

import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;

public record OrgaoGovernamentalSearchDto(
        Long id,
        String nome,

        String cnpj
) {
    public static boolean isEmpty(OrgaoGovernamentalSearchDto dto) {
        return (UtilsValidators.longIsNullOrZero(dto.id)) &&
                (UtilsValidators.stringIsNullOrEmpty(dto.nome)) &&
                (UtilsValidators.stringIsNullOrEmpty(dto.cnpj));
    }

    public static OrgaoGovernamentalSearchDto getNewEmptyInstance() {
        return new OrgaoGovernamentalSearchDto(null, "", "");
    }

    public static OrgaoGovernamentalSearchDto resolveEnderecoSearchDTO(Long id, String nome, String cnpj) {
        OrgaoGovernamentalSearchDto dadosParaBusca = new OrgaoGovernamentalSearchDto(id, nome, cnpj);
        if (isEmpty(dadosParaBusca)) {
            return getNewEmptyInstance();
        }
        return dadosParaBusca;
    }
}
