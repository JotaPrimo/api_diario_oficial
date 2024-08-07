package com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental;

import com.api.diario_oficial.api_diario_oficial.entity.OrgaoGovernamental;

public record OrgaoGovernamentalResponseDTO(
        Long id,
        String nome,
        String cnpj,
        String createdAt,
        String updatedAt
) {

    public static OrgaoGovernamentalResponseDTO fromEntity(OrgaoGovernamental orgaoGovernamental) {
        return new OrgaoGovernamentalResponseDTO(
                orgaoGovernamental.getId(),
                orgaoGovernamental.getNome(),
                orgaoGovernamental.getCnpj(),
                orgaoGovernamental.getCreatedAtFormatado(),
                orgaoGovernamental.getUpdatedAtFormatado());
    }
}
