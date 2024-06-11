package com.api.diario_oficial.api_diario_oficial;

import com.api.diario_oficial.api_diario_oficial.auth.Credentials;
import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.jwt.JwtUserDetails;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioSearchDTO;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioUpdateDTO;
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
    public String getValidToken() {
        JwtUserDetails jwtUserDetails = getValidCredentialsLogin();

        ResponseEntity<String> loginResponse = restTemplate.postForEntity(ApiPath.AUTENTICAR, jwtUserDetails, String.class);
        JSONObject loginResponseJson = new JSONObject(loginResponse.getBody());

        return "Bearer " + loginResponseJson.getString("token");
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

    @SneakyThrows
    public HttpHeaders getOnlyValidHeaderAuthentication() {
        // Login and get JWT token
        JwtUserDetails jwtUserDetails = getValidCredentialsLogin();

        ResponseEntity<String> loginResponse = restTemplate.postForEntity(ApiPath.AUTENTICAR, jwtUserDetails, String.class);
        JSONObject loginResponseJon = new JSONObject(loginResponse.getBody());

        // Use JWT token to access protected endpoint
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + loginResponseJon.getString("token"));

        return headers;
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

    @Test
    @SneakyThrows
    void shouldReturn204WhenUserIsDeleted() {
        HttpEntity<String> entity = getValidHeaderAuthentication();

        ResponseEntity<String> response = restTemplate.exchange(ApiPath.USUARIOS + "/10", HttpMethod.DELETE, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @SneakyThrows
    void shouldReturn404WhenUserIsNotFound() {
        HttpEntity<String> entity = getValidHeaderAuthentication();

        ResponseEntity<String> response = restTemplate.exchange(ApiPath.USUARIOS + "/100000", HttpMethod.DELETE, entity, String.class);
        JSONObject responseJson = new JSONObject(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseJson).isNotNull();
        assertThat(responseJson.has("path")).isTrue();
        assertThat(responseJson.getString("path")).isEqualTo("/api/v1/usuarios/100000");
        assertThat(responseJson.has("method")).isTrue();
        assertThat(responseJson.getString("method")).isEqualTo(HttpMethod.DELETE.toString());
        assertThat(responseJson.has("status")).isTrue();
        assertThat(responseJson.getInt("status")).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(responseJson.has("statusText")).isTrue();
        assertThat(responseJson.getString("statusText")).isEqualTo("Not Found");
        assertThat(responseJson.has("message")).isTrue();
        assertThat(responseJson.getString("message")).isEqualTo("Usuario com id '100000' não foi encontrado");
    }

    @Test
    @SneakyThrows
    void shouldReturn200AndUpdatedData() {
        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO("kelley.turcotte.editado", "marcus.bode@hotmail.com", "12345678", Role.ROLE_CLIENTE_ADMIN.toString());

        webTestClient.patch()
                .uri(ApiPath.USUARIOS + "/9")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getValidToken())
                .bodyValue(usuarioUpdateDTO)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.id").isEqualTo(9L)
                .jsonPath("$.username").exists()
                .jsonPath("$.username").isEqualTo("kelley.turcotte.editado")
                .jsonPath("$.password").doesNotExist()
                .jsonPath("$.role").exists()
                .jsonPath("$.role").isEqualTo(Role.ROLE_CLIENTE_ADMIN.toString())
                .jsonPath("$.createdAt").exists()
                .jsonPath("$.updatedAt").exists()
                .jsonPath("$.updatedAt").isNotEmpty();
    }

    @Test
    @SneakyThrows
    void shouldReturnMessageSuccessWhenUsuarioAtivado() {
        webTestClient.patch()
                .uri(ApiPath.USUARIOS + "/9/" + "ativar")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getValidToken())
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.message").exists()
                .jsonPath("$.message").isEqualTo("Usuário ativado com sucesso");
    }

    @Test
    @SneakyThrows
    void shouldReturnMessageSuccessWhenUsuarioInativado() {
        webTestClient.patch()
                .uri(ApiPath.USUARIOS + "/9/" + "inativar")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getValidToken())
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.message").exists()
                .jsonPath("$.message").isEqualTo("Usuário inativado com sucesso");
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundWhenUsuarioNotExists() {
        webTestClient.patch()
                .uri(ApiPath.USUARIOS + "/9999/" + "inativar")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getValidToken())
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.message").exists()
                .jsonPath("$.message").isEqualTo("Usuário inativado com sucesso");
    }

    @Test
    @SneakyThrows
    void testSearchUserResponse() {
        webTestClient.get().uri(uriBuilder -> uriBuilder
                        .path(ApiPath.USUARIOS)
                        .queryParam("role", "ROLE_CLIENTE_COLABORADOR")
                        .queryParam("statusUsuario", "INATIVO")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, getValidToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isArray()
                .jsonPath("$.content.length()").isEqualTo(10)
                .jsonPath("$.totalPages").exists()
                .jsonPath("$.totalPages").isEqualTo(1)
                .jsonPath("$.size").exists()
                .jsonPath("$.size").isEqualTo(10)
                .jsonPath("$.totalElements").exists()
                .jsonPath("$.totalElements").isEqualTo(10);
    }

}
