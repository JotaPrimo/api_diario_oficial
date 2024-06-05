package com.api.diario_oficial.api_diario_oficial.web.controllers.auth;

import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.exceptions.handler.ErrorMessage;
import com.api.diario_oficial.api_diario_oficial.jwt.JwtToken;
import com.api.diario_oficial.api_diario_oficial.jwt.JwtUserDetailsService;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioLoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPath.ROOT)
public class AutenticacaoController {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDTO dto, HttpServletRequest request) {
        log.info("Processo de autenticação pelo login {}", dto.username());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.username(), dto.password());

            authenticationManager.authenticate(authenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(dto.username());

            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            log.warn("Bad Credentials from username '{}'", dto.username());
        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
    }
}
