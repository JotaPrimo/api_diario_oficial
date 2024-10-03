package com.api.diario_oficial.api_diario_oficial.services.filters;

import com.api.diario_oficial.api_diario_oficial.entity.Endereco;
import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;
import com.api.diario_oficial.api_diario_oficial.web.dtos.enderecos.EnderecoSearchDTO;
import org.springframework.data.jpa.domain.Specification;

public class EnderecoSearchSpecification {

    public static Specification<Endereco> toSpecification(EnderecoSearchDTO dto) {
        return Specification.where(hasId(dto.id()))
                .and(hasLogradouro(dto.logradouro()))
                .and(hasCidade(dto.cidade()))
                .and(hasEstado(dto.estado()))
                .and(hasCep(dto.cep()));
    }

    private static Specification<Endereco> hasId(Long id) {
        return (root, query, cb) -> (id == null) ? cb.conjunction() : cb.equal(root.get("id"), id);
    }

    private static Specification<Endereco> hasLogradouro(String logradouro) {
        return (root, query, cb) ->
                (UtilsValidators.stringIsNullOrEmpty(logradouro))
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("logradouro")), "%" + logradouro.toLowerCase() + "%");
    }

    private static Specification<Endereco> hasCidade(String cidade) {
        return (root, query, cb) ->
                UtilsValidators.stringIsNullOrEmpty(cidade)
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("cidade")), "%" + cidade.toLowerCase() + "%");
    }

    private static Specification<Endereco> hasEstado(String estado) {
        return (root, query, cb) -> UtilsValidators.stringIsNullOrEmpty(estado) ? cb.conjunction() : cb.equal(root.get("estado"), estado);
    }

    private static Specification<Endereco> hasCep(String cep) {
        return (root, query, cb) -> UtilsValidators.stringIsNullOrEmpty(cep) ? cb.conjunction() : cb.like(cb.lower(root.get("cep")), "%" + cep.toLowerCase() + "%");
    }

}
