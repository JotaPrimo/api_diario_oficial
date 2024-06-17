package com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental;

public record OrgaoGovernamentalResponseDTO(
        Long id,
        String nome,
        String cnpj,
        String createdAt,
        String updatedAt
) {

}
