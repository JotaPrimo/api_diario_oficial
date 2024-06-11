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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Usuários", description = "Contém todas as operações relariva ao recurso de usuários ")
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
    @Operation(summary = "Listar usuários", description = "Recurso para listar e filtrae usuários",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
            })
    public Page<UsuarioResponseDTO> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "dir", defaultValue = "asc") String dir,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String statusUsuario,
            @RequestParam(required = false) String role
    ) {
        UsuarioSearchDTO searchDTO = UsuarioSearchDTO.resolveUsuarioSearchDTO(id, username, email, statusUsuario, role);

        Sort.Direction direction = dir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        return usuarioService
                .search(searchDTO, pageable)
                .map(UsuarioResponseDTO::fromEntity);
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuario", description = "Recurso para criar um novo usuario",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "Email já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "409", description = "Username inválido",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
            })
    public ResponseEntity<UsuarioResponseDTO> store(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuarioToSave = UsuarioCreateDTO.toEntity(usuarioCreateDTO);
        Usuario usuarioSaved = usuarioService.save(usuarioToSave);

        UsuarioCriadoEvent event = new UsuarioCriadoEvent(this, usuarioSaved);
        eventPublisher.publishEvent(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponseDTO.fromEntity(usuarioSaved));
    }

    @Operation(summary = "Buscar usuario por id", description = "Recurso para buscar usuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> show(@PathVariable Long id) {
        Usuario usuario = usuarioService.findOrFail(id);
        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(usuario));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar usuario por id", description = "Recurso para Atualizar usuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
            })
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario usuarioToUpdate = UsuarioUpdateDTO.toEntity(usuarioUpdateDTO, usuarioService.findOrFail(id));

        usuarioService.update(usuarioToUpdate);

        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(usuarioToUpdate));
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar usuario por id", description = "Recurso para ativar usuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso ativar com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
            })
    public ResponseEntity<Map<String, String>> ativar(@PathVariable Long id) {
        usuarioService.ativarUsuario(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário ativado com sucesso");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar usuario por id", description = "Recurso para inativar usuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso inativar com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
            })
    public ResponseEntity<Map<String, String>> inativar(@PathVariable Long id, @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        // @AuthenticationPrincipal JwtUserDetails jwtUserDetails retorna os dados do usuario logado
        usuarioService.inativarUsuario(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário inativado com sucesso");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuario por id", description = "Recurso para deletar usuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso deletar com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
            })
    public ResponseEntity<UsuarioResponseDTO> delete(@PathVariable Long id) {
        Usuario usuarioToDelete = usuarioService.findOrFail(id);
        usuarioService.delete(usuarioToDelete);
        return ResponseEntity.noContent().build();
    }

}
