package com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental;

import com.api.diario_oficial.api_diario_oficial.entity.Endereco;
import com.api.diario_oficial.api_diario_oficial.entity.OrgaoGovernamental;
import com.api.diario_oficial.api_diario_oficial.utils.DateFormats;
import com.api.diario_oficial.api_diario_oficial.web.dtos.enderecos.EnderecoResponseDTO;

public record OrgaoGovernamentalResponseDTO(
        Long id,
        String nome,
        String cnpj,
        String created_at,
        String updated_at
) {

    public static OrgaoGovernamentalResponseDTO fromEntity(OrgaoGovernamental orgaoGovernamental) {
        return new OrgaoGovernamentalResponseDTO(
                orgaoGovernamental.getId(),
                orgaoGovernamental.getNome(),
                orgaoGovernamental.getCnpj(),
                orgaoGovernamental.getCreatedAt().toString(),
                null);
    }
}
