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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-truncate.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@AutoConfigureMockMvc
public class UsuarioIT {

    @Autowired
    private MockMvc mockMvc;

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

    @SneakyThrows
    public HttpEntity<String> getValidHeaderAuthentication() {
        // Login and get JWT token
        JwtUserDetails jwtUserDetails = getValidCredentialsLogin();

        ResponseEntity<String> loginResponse = restTemplate.postForEntity(ApiPath.AUTENTICAR, jwtUserDetails, String.class);
        JSONObject loginResponseJon = new JSONObject(loginResponse.getBody());

        // Use JWT token to access protected endpoint
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + loginResponseJon.getString("token"));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return entity;
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

        HttpEntity<String> entity = getValidHeaderAuthentication();

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

    @Test
    @SneakyThrows
    void shouldReturnUserWithStatus200WhenCredentialsAreValid() {

        HttpEntity<String> entity = getValidHeaderAuthentication();

        ResponseEntity<String> response = restTemplate.exchange(ApiPath.USUARIOS + "/9", HttpMethod.GET, entity, String.class);
        JSONObject responseJson = new JSONObject(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseJson).isNotNull();
        assertThat(responseJson.has("id")).isTrue();
        assertThat(responseJson.getInt("id")).isEqualTo(9);
        assertThat(responseJson.has("nome")).isTrue();
        assertThat(responseJson.getString("nome")).isEqualTo("kelley.turcotte");
        assertThat(responseJson.has("email")).isTrue();
        assertThat(responseJson.getString("email")).isEqualTo("marcus.bode@hotmail.com");
        assertThat(responseJson.has("statusUsuario")).isTrue();
        assertThat(responseJson.getString("statusUsuario")).isEqualTo("INATIVO");
        assertThat(responseJson.has("createdAt")).isTrue();
        assertThat(responseJson.getString("createdAt")).isEqualTo("07/06/2024 09:20:19");
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundWithStatus404WhenIdNotExists() {

        HttpEntity<String> entity = getValidHeaderAuthentication();

        ResponseEntity<String> response = restTemplate.exchange(ApiPath.USUARIOS + "/9999", HttpMethod.GET, entity, String.class);
        JSONObject responseJson = new JSONObject(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseJson).isNotNull();
        assertThat(responseJson.has("path")).isTrue();
        assertThat(responseJson.getString("path")).isEqualTo("/api/v1/usuarios/9999");
        assertThat(responseJson.has("method")).isTrue();
        assertThat(responseJson.getString("method")).isEqualTo(HttpMethod.GET.toString());
        assertThat(responseJson.has("status")).isTrue();
        assertThat(responseJson.getInt("status")).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(responseJson.has("statusText")).isTrue();
        assertThat(responseJson.getString("statusText")).isEqualTo("Not Found");
        assertThat(responseJson.has("message")).isTrue();
        assertThat(responseJson.getString("message")).isEqualTo("Usuario com id '9999' não foi encontrado");
    }


}
