package com.api.diario_oficial.api_diario_oficial;

import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioResponseDTO;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-truncate.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void createUser_withUserNameAndPasswordValid_returnStatusCreated() {
        Usuario usuario = new Usuario();
        usuario.setUsername("jughead_jones");
        usuario.setEmail("jughead_jones@gmail.com");
        usuario.setPassword("12345678");
        usuario.setRole(Role.ROLE_COLABORADOR);

        UsuarioResponseDTO responseBody = webTestClient
                .post()
                .uri(ApiPath.USUARIOS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(usuario)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UsuarioResponseDTO.class)
                .returnResult()
                .getResponseBody();

        // checando se existe resposta
        Assertions.assertThat(responseBody).isNotNull();

        // checando se id foi gerado
        Assertions.assertThat(responseBody.id()).isNotNull();
        Assertions.assertThat(responseBody.nome()).isEqualTo("jughead_jones");
        Assertions.assertThat(responseBody.email()).isEqualTo("tody@gmail.com");
    }

}
