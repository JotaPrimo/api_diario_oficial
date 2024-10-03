package com.api.diario_oficial.api_diario_oficial.web.controllers;

import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.services.implementations.OrgaoGovernamentalServiceImpl;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IOrgaoGovernamentalService;
import com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental.OrgaoGovernamentalResponseDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental.OrgaoGovernamentalSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(ApiPath.ORGAO_GOVERNAMENTAL)
public class OrgaoGovernamentalController {

    private final IOrgaoGovernamentalService service;

    @Autowired
    public OrgaoGovernamentalController(OrgaoGovernamentalServiceImpl orgaoGovernamentalService) {
        this.service = orgaoGovernamentalService;
    }

    @GetMapping
    public Page<OrgaoGovernamentalResponseDTO> list(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cnpj
    ) {
        OrgaoGovernamentalSearchDto searchDto = OrgaoGovernamentalSearchDto.resolveEnderecoSearchDTO(id, nome, cnpj);
        Pageable pageable = PageRequest.of(page, size);

        return service.search(searchDto, pageable).map(OrgaoGovernamentalResponseDTO::fromEntity);
    }
}
