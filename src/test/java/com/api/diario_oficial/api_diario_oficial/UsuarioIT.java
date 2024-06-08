package com.api.diario_oficial.api_diario_oficial;

import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.utils.DataUtil;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioResponseDTO;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-truncate.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {

    @Autowired
    WebTestClient webTestClient;

    // Constantes para dados de teste
    private static final String TEST_USERNAME = "jughead_jones";
    private static final String TEST_USERNAME_INVALID = "nes";
    private static final String TEST_EMAIL = "jughead_jones@gmail.com";
    private static final String TEST_PASSWORD = "12345678";
    private static final String TEST_PASSWORD_INVALID = "678";
    private static final LocalDateTime TEST_CREATED_AT = LocalDateTime.now();
    private static final Role TEST_ROLE = Role.ROLE_COLABORADOR;

    @Test
    public void createUser_withUserNameAndPasswordValid_returnStatusCreated() {
        // cen�rio
        Usuario usuario = new Usuario();
        usuario.setUsername(TEST_USERNAME);
        usuario.setEmail(TEST_EMAIL);
        usuario.setPassword(TEST_PASSWORD);
        usuario.setCreatedAt(TEST_CREATED_AT);
        usuario.setRole(TEST_ROLE);

        //
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

        // assetion
        Assertions.assertThat(responseBody).isNotNull().withFailMessage("responseBody est� null");
        Assertions.assertThat(responseBody.id()).isNotNull().withFailMessage("Id n�o deveria ser null");
        Assertions.assertThat(responseBody.nome()).isEqualTo(TEST_USERNAME).withFailMessage("Username diferente de jughead_jones");
        Assertions.assertThat(responseBody.email()).isEqualTo(TEST_EMAIL).withFailMessage("Email diferente");
    }

    @Test
    public void createUser_withPasswordInvalid_returnUnprocessableEntity() {
        // cen�rio
        Usuario usuario = new Usuario();
        usuario.setUsername(TEST_USERNAME);
        usuario.setEmail(TEST_EMAIL);
        usuario.setPassword(TEST_PASSWORD_INVALID);
        usuario.setCreatedAt(TEST_CREATED_AT);
        usuario.setRole(TEST_ROLE);

        // A��o
        WebTestClient.BodyContentSpec responseBody = webTestClient
                .post()
                .uri(ApiPath.USUARIOS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(usuario)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody();

        // assetion
        Assertions.assertThat(responseBody.jsonPath("$.path")).isEqualTo(ApiPath.USUARIOS).withFailMessage("path inv�lido");
        Assertions.assertThat(responseBody.jsonPath("$.method")).isEqualTo("POST").withFailMessage("method inv�lido");
        Assertions.assertThat(responseBody.jsonPath("$.errors.password")).isEqualTo("Password deve ter entre 8 e 10 caracteres").withFailMessage("method inv�lido");


    }

}
