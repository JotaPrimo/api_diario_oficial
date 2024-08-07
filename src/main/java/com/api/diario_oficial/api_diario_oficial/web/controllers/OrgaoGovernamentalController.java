package com.api.diario_oficial.api_diario_oficial.web.controllers;

import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.entity.OrgaoGovernamental;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IOrgaoGovernamentalService;
import com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental.OrgaoGovernamentalCreateDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental.OrgaoGovernamentalResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPath.ORGAO_GOVERNAMENTAL)
public class OrgaoGovernamentalController {

    private IOrgaoGovernamentalService orgaoGovernamentalService;

    public OrgaoGovernamentalController(@Qualifier("orgaoGovernamentalServiceImpl") IOrgaoGovernamentalService orgaoGovernamentalService) {
        this.orgaoGovernamentalService = orgaoGovernamentalService;
    }

    @PostMapping
    public ResponseEntity<OrgaoGovernamentalResponseDTO> store(@RequestBody @Valid OrgaoGovernamentalCreateDTO createDTO) {
        OrgaoGovernamental orgaoGovernamentalToSave = OrgaoGovernamentalCreateDTO.toEntity(createDTO);

        OrgaoGovernamental orgaoGovernamentalSaved = orgaoGovernamentalService.save(orgaoGovernamentalToSave);

        return ResponseEntity.status(HttpStatus.CREATED).body(OrgaoGovernamentalResponseDTO.fromEntity(orgaoGovernamentalSaved));
    }
}
