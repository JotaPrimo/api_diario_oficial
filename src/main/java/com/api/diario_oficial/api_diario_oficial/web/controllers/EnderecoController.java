package com.api.diario_oficial.api_diario_oficial.web.controllers;

import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IEnderecoService;
import com.api.diario_oficial.api_diario_oficial.web.dtos.enderecos.EnderecoResponseDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.enderecos.EnderecoSearchDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioResponseDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPath.ENDERECOS)
public class EnderecoController {

    private IEnderecoService enderecoService;

    public EnderecoController(IEnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public Page<EnderecoResponseDTO> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String logradouro,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String cep

    ) {
        EnderecoSearchDTO searchDTO = EnderecoSearchDTO.resolveEnderecoSearchDTO(id, logradouro, cidade, estado, cep);
        Pageable pageable = PageRequest.of(page, size);

        return enderecoService
                .search(searchDTO, pageable)
                .map(EnderecoResponseDTO::fromEntity);
    }

}
