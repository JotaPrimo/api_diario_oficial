package com.api.diario_oficial.api_diario_oficial.services.filters;

import com.api.diario_oficial.api_diario_oficial.entity.Endereco;
import com.api.diario_oficial.api_diario_oficial.entity.OrgaoGovernamental;
import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;
import com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental.OrgaoGovernamentalSearchDto;
import org.springframework.data.jpa.domain.Specification;

public class OrgaoGovernamentalSearchSpecification {
    public static Specification<OrgaoGovernamental> toSpecification(OrgaoGovernamentalSearchDto dto) {
        return Specification.where(hasId(dto.id()))
                .and(hasNome(dto.nome()))
                .and(hasCnpj(dto.cnpj()));
    }

    private static Specification<OrgaoGovernamental> hasId(Long id) {
        return (root, query, cb) -> (id == null) ? cb.conjunction() : cb.equal(root.get("id"), id);
    }

    private static Specification<OrgaoGovernamental> hasNome(String nome) {
        return (root, query, cb) ->
                (UtilsValidators.stringIsNullOrEmpty(nome))
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    private static Specification<OrgaoGovernamental> hasCnpj(String cnpj) {
        return (root, query, cb) ->
                (UtilsValidators.stringIsNullOrEmpty(cnpj))
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("cnpj")), cnpj.toLowerCase());
    }
}
