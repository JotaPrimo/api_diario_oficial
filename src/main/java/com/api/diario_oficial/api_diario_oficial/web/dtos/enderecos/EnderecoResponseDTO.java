package com.api.diario_oficial.api_diario_oficial.web.dtos.enderecos;

import com.api.diario_oficial.api_diario_oficial.entity.Endereco;
import com.api.diario_oficial.api_diario_oficial.utils.DateFormats;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EnderecoResponseDTO(
        Long id,
        String logradouro,
        String cidade,
        String estado,
        String cep,
        String createdAt,
        String updatedAt
) {
    public static EnderecoResponseDTO fromEntity(Endereco endereco) {
        return new EnderecoResponseDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getCreatedAt().format(DateFormats.DMYHMS),
                endereco.getUpdatedAt().format(DateFormats.DMYHMS));
    }

    public static List<EnderecoResponseDTO> convertList(List<Endereco> enderecos) {
        List<EnderecoResponseDTO> enderecoResponseDTOS = new ArrayList<>();
        enderecos.forEach(usuario -> enderecoResponseDTOS.add(fromEntity(usuario)));
        return enderecoResponseDTOS;
    }

}
