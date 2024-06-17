package com.api.diario_oficial.api_diario_oficial.web.dtos.enderecos;

import com.api.diario_oficial.api_diario_oficial.entity.Endereco;

public record EnderecoCreateDTO(
        String logradouro,
        String cidade,
        String estado,
        String cep
) {
    public Endereco toEntity() {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(logradouro);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setCep(cep);
        return endereco;
    }
}
