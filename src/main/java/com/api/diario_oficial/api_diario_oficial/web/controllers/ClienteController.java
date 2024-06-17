package com.api.diario_oficial.api_diario_oficial.web.controllers;

import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IClienteService;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IUsuarioService;
import com.api.diario_oficial.api_diario_oficial.web.dtos.clientes.ClienteResponseDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.clientes.ClienteSearchDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(ApiPath.CLIENTES)
public class ClienteController {

    private IClienteService clienteService;

    private IUsuarioService usuarioService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public Page<ClienteResponseDTO> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long orgaoGovernamentalId
    ) {

        Usuario usuario = usuarioService.findOrFailByUsername(username);
        ClienteSearchDTO searchDTO = new ClienteSearchDTO(id, nome, username, email, orgaoGovernamentalId);
        Pageable pageable = PageRequest.of(page, size);

        return clienteService
                .search(searchDTO, pageable)
                .map(cliente -> ClienteResponseDTO.fromEntity(cliente, UsuarioResponseDTO.fromEntity(usuario)));
    }

}
