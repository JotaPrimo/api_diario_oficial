package com.api.diario_oficial.api_diario_oficial;

import com.api.diario_oficial.api_diario_oficial.auth.Credentials;
import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.jwt.JwtUserDetails;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
// @Sql(scripts = "/sql/usuarios/usuarios-truncate.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {


    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private TestRestTemplate restTemplate;


    public static JwtUserDetails getValidCredentialsLogin() {
        Usuario usuario = new Usuario();
        usuario.setUsername(Credentials.TEST_LOGIN_VALID);
        usuario.setPassword(Credentials.TEST_PASSWORD_VALID);
        usuario.setRole(Role.ROLE_ADMIN);

        return new JwtUserDetails(usuario);
    }

    public static JwtUserDetails getInvalidCredentialsLogin() {
        Usuario usuario = new Usuario();
        usuario.setUsername(Credentials.TEST_LOGIN_INVALID);
        usuario.setPassword(Credentials.TEST_PASSWORD_VALID);
        usuario.setRole(Role.ROLE_ADMIN);

        return new JwtUserDetails(usuario);
    }

    @Test
    void shouldReturnForbiden403WhenUserOrPasswordInvalid() {
        // dados validos para login
        JwtUserDetails jwtUserDetails = getInvalidCredentialsLogin();

       ResponseEntity<String> loginResponse = restTemplate.postForEntity(ApiPath.LOGIN, jwtUserDetails, String.class);
        String jwtToken = loginResponse.getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(ApiPath.USUARIOS, HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @SneakyThrows
    @Test
    void shouldReturnListUsersWhenUserAndPasswordValid() {
        // Login and get JWT token
        JwtUserDetails jwtUserDetails = getValidCredentialsLogin();

        ResponseEntity<String> loginResponse = restTemplate.postForEntity(ApiPath.AUTENTICAR, jwtUserDetails, String.class);
        JSONObject loginResponseJon = new JSONObject(loginResponse.getBody());

        // Use JWT token to access protected endpoint
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + loginResponseJon.getString("token"));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(ApiPath.USUARIOS, HttpMethod.GET, entity, String.class);
        JSONObject responseJsonList = new JSONObject(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();

        // verificandopaginação
        assertThat(responseJsonList.has("content")).isTrue();
        assertThat(responseJsonList.has("pageable")).isTrue();
        assertThat(responseJsonList.has("totalPages")).isTrue();
        assertThat(responseJsonList.getString("totalPages")).asInt().isEqualTo(6);
        assertThat(responseJsonList.has("totalElements")).isTrue();
        assertThat(responseJsonList.getString("totalElements")).asInt().isEqualTo(53);
        assertThat(responseJsonList.has("last")).isTrue();
        assertThat(responseJsonList.has("first")).isTrue();
        assertThat(responseJsonList.has("size")).isTrue();
        assertThat(responseJsonList.has("number")).isTrue();
        assertThat(responseJsonList.has("sort")).isTrue();
        assertThat(responseJsonList.has("numberOfElements")).isTrue();
        assertThat(responseJsonList.has("empty")).isTrue();

        // verificando se a lista não é vazia
        JSONArray contentArray = responseJsonList.getJSONArray("content");
        assertThat(contentArray).isNotNull();
    }



}
