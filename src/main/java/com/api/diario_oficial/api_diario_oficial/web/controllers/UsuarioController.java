package com.api.diario_oficial.api_diario_oficial.web.controllers;

import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IUsuarioService;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioCreateDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(ApiPath.USUARIOS)
public class UsuarioController {

    private IUsuarioService usuarioService;
    private ApplicationEventPublisher eventPublisher;

    public UsuarioController(@Qualifier("usuarioServiceImpl") IUsuarioService usuarioService, ApplicationEventPublisher eventPublisher) {
        this.usuarioService = usuarioService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> index() {
        List<Usuario> usuarioList = usuarioService.findAllSortedById();
        return ResponseEntity.ok(UsuarioResponseDTO.convertList(usuarioList));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> store(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuarioToSave = UsuarioCreateDTO.toEntity(usuarioCreateDTO);
        Usuario usuarioSaved = usuarioService.save(usuarioToSave);
        eventPublisher.publishEvent(usuarioSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponseDTO.fromEntity(usuarioSaved));
    }

}
