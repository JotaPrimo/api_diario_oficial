package com.api.diario_oficial.api_diario_oficial;

import com.api.diario_oficial.api_diario_oficial.auth.Credentials;
import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.jwt.JwtUserDetails;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioLoginDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AutenticacaoIT {

    @Autowired
    TestRestTemplate restTemplate;

    public static JwtUserDetails getValidCredentialsLogin() {
        Usuario usuario = new Usuario();
        usuario.setUsername(Credentials.TEST_LOGIN_VALID);
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


    @SneakyThrows
    @Test
    public void shouldReturnBearerTokenWithValidCredentialsWithStatus200() {
        // cenario
        UsuarioLoginDTO loginDTO = new UsuarioLoginDTO(Credentials.TEST_LOGIN_VALID, Credentials.TEST_PASSWORD);
        log.error("USERNAME " + loginDTO.username());
        log.error("PASSWORD " + loginDTO.password());

        // ação
        ResponseEntity<String> response = restTemplate.postForEntity(ApiPath.AUTENTICAR, loginDTO, String.class);
        JSONObject jsonObject = new JSONObject(response.getBody());
        log.info("JSON response " + jsonObject.toString());
        log.error("RESPONSE " + response);

        // assertions
       assertThat(response.getBody()).isNotEmpty();
       assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
       assertThat(jsonObject.has("token")).isTrue();
       assertThat(jsonObject.getString("token")).isNotEmpty().withFailMessage("O token veio vazio");
    }

    @SneakyThrows
    @Test
    public void shouldReturnUnprocessableEntityWhenInvalidCredentialsWithStatus400() {
        // cenario
        UsuarioLoginDTO loginDTO = new UsuarioLoginDTO(Credentials.TEST_LOGIN_INVALID, Credentials.TEST_PASSWORD);

        // ação
        ResponseEntity<String> response = restTemplate.postForEntity(ApiPath.AUTENTICAR, loginDTO, String.class);
        JSONObject jsonObject = new JSONObject(response.getBody());

        // assertions
        assertThat(response.getBody()).isNotEmpty();
        assertThat(jsonObject.has("token")).isFalse();
        assertThat(jsonObject.getString("status")).toString().equalsIgnoreCase(HttpStatus.BAD_REQUEST.toString());
        assertThat(jsonObject.has("statusText")).isTrue();
        assertThat(jsonObject.getString("statusText")).isEqualTo("Bad Request");
        assertThat(jsonObject.has("message")).isTrue();
        assertThat(jsonObject.getString("message")).isNotEmpty().withFailMessage("Credenciais Inválidas");
    }

}
