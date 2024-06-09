package com.api.diario_oficial.api_diario_oficial.web.controllers;

import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.events.UsuarioCriadoEvent;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IUsuarioService;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioCreateDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioResponseDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPath.USUARIOS)
public class UsuarioController {

    private IUsuarioService usuarioService;
    private ApplicationEventPublisher eventPublisher;

    public UsuarioController(@Qualifier("usuarioServiceImpl") IUsuarioService usuarioService, ApplicationEventPublisher eventPublisher) {
        this.usuarioService = usuarioService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping
    public Page<UsuarioResponseDTO> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return usuarioService.findAll(pageable).map(UsuarioResponseDTO::fromEntity);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> store(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuarioToSave = UsuarioCreateDTO.toEntity(usuarioCreateDTO);
        Usuario usuarioSaved = usuarioService.save(usuarioToSave);

        UsuarioCriadoEvent event = new UsuarioCriadoEvent(this, usuarioSaved);
        eventPublisher.publishEvent(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponseDTO.fromEntity(usuarioSaved));
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> show(@PathVariable Long id) {
        Usuario usuario = usuarioService.findOrFail(id);
        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(usuario));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario usuarioToUpdate = UsuarioUpdateDTO.toEntity(usuarioUpdateDTO, usuarioService.findOrFail(id));

        usuarioService.update(usuarioToUpdate);

        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(usuarioToUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> delete(@PathVariable Long id) {
        Usuario usuarioToDelete = usuarioService.findOrFail(id);
        usuarioService.delete(usuarioToDelete);
        return ResponseEntity.noContent().build();
    }

}
