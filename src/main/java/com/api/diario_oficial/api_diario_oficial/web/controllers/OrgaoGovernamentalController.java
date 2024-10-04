package com.api.diario_oficial.api_diario_oficial.web.controllers;

import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.entity.OrgaoGovernamental;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IOrgaoGovernamentalService;
import com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental.OrgaoGovernamentalCreateDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental.OrgaoGovernamentalResponseDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental.OrgaoGovernamentalSearchDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPath.ORGAO_GOVERNAMENTAL)
public class OrgaoGovernamentalController {

    private IOrgaoGovernamentalService orgaoGovernamentalService;

    @Autowired
    public OrgaoGovernamentalController(IOrgaoGovernamentalService orgaoGovernamentalService) {
        this.orgaoGovernamentalService = orgaoGovernamentalService;
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

        return orgaoGovernamentalService.search(searchDto, pageable).map(OrgaoGovernamentalResponseDTO::fromEntity);
    }

    @PostMapping
    public ResponseEntity<OrgaoGovernamentalResponseDTO> store(@RequestBody @Valid OrgaoGovernamentalCreateDTO createDTO) {
        OrgaoGovernamental orgaoGovernamentalToSave = OrgaoGovernamentalCreateDTO.toEntity(createDTO);

        OrgaoGovernamental orgaoGovernamentalSaved = orgaoGovernamentalService.save(orgaoGovernamentalToSave);

        return ResponseEntity.status(HttpStatus.CREATED).body(OrgaoGovernamentalResponseDTO.fromEntity(orgaoGovernamentalSaved));
    }
}
