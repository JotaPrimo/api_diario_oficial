package com.api.diario_oficial.api_diario_oficial.web.controllers;

import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.entity.OrgaoGovernamental;
import com.api.diario_oficial.api_diario_oficial.services.implementations.OrgaoGovernamentalServiceImpl;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IOrgaoGovernamentalService;
import com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental.OrgaoGovernamentalResponseDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental.OrgaoGovernamentalSearchDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<OrgaoGovernamentalResponseDTO>> list(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) int size,
            @ModelAttribute OrgaoGovernamentalSearchDto searchDto
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrgaoGovernamentalResponseDTO> listagem = service.search(searchDto, pageable).map(OrgaoGovernamentalResponseDTO::fromEntity);

        return ResponseEntity.status(HttpStatus.OK).body(listagem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrgaoGovernamentalResponseDTO> findById(@PathVariable @Min(0) Long id) {
        OrgaoGovernamental orgaoGovernamental = service.findOrFail(id);
        OrgaoGovernamentalResponseDTO responseDTO = OrgaoGovernamentalResponseDTO.fromEntity(orgaoGovernamental);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(0) Long id) {
        OrgaoGovernamental orgaoGovernamental = service.findOrFail(id);
        service.delete(orgaoGovernamental);

        return ResponseEntity.noContent().build();
    }
}
