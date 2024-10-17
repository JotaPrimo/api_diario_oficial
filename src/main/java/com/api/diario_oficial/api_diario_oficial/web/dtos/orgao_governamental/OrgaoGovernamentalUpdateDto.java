package com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental;

import com.api.diario_oficial.api_diario_oficial.entity.OrgaoGovernamental;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record OrgaoGovernamentalUpdateDto(
        @NotBlank(message = "Nome é um campo obrigatório")
        @Size(min = 5, max = 250, message = "Nome deve ter entre {min} e  {max} caracteres")
        String nome,

        @CNPJ
        @NotBlank(message = "CNPJ é um campo obrigatório")
        String cnpj
) {
    public static OrgaoGovernamental transferData(OrgaoGovernamentalUpdateDto dto, OrgaoGovernamental orgaoGovernamental) {
        // transferindo os dados do dto para a entity, antes de atualizar
        orgaoGovernamental.setNome(dto.nome);
        orgaoGovernamental.setCnpj(dto.cnpj);
        return orgaoGovernamental;
    }
}
