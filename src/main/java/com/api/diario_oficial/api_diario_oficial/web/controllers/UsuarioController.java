package com.api.diario_oficial.api_diario_oficial.web.controllers;

import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.events.UsuarioCriadoEvent;
import com.api.diario_oficial.api_diario_oficial.jwt.JwtUserDetails;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IUsuarioService;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioCreateDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioResponseDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioSearchDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String statusUsuario,
            @RequestParam(required = false) String role
    ) {
        UsuarioSearchDTO searchDTO = UsuarioSearchDTO.resolveUsuarioSearchDTO(id, username, email, statusUsuario, role);
        Pageable pageable = PageRequest.of(page, size);
        return usuarioService
                .search(searchDTO, pageable)
                .map(UsuarioResponseDTO::fromEntity);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> store(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuarioToSave = UsuarioCreateDTO.toEntity(usuarioCreateDTO);
        usuarioService.validateBeforeSave(usuarioToSave);
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

        usuarioService.validateBeforeUpdate(usuarioToUpdate);
        usuarioService.update(usuarioToUpdate);

        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(usuarioToUpdate));
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Map<String, String>> ativar(@PathVariable Long id) {
        usuarioService.ativarUsuario(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário ativado com sucesso");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Map<String, String>> inativar(@PathVariable Long id) {
        // @AuthenticationPrincipal JwtUserDetails jwtUserDetails passar como parametro
        // @AuthenticationPrincipal JwtUserDetails jwtUserDetails retorna os dados do usuario logado
        usuarioService.inativarUsuario(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário inativado com sucesso");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> delete(@PathVariable Long id) {
        Usuario usuarioToDelete = usuarioService.findOrFail(id);
        usuarioService.delete(usuarioToDelete);
        return ResponseEntity.noContent().build();
    }
}
