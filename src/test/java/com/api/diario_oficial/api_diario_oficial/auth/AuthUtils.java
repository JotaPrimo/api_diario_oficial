package com.api.diario_oficial.api_diario_oficial.auth;

import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.jwt.JwtUserDetails;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AuthUtils {

    private static final RestTemplate restTemplate = new RestTemplate();

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
    public static String getValidToken() {
        JwtUserDetails jwtUserDetails = getValidCredentialsLogin();

        ResponseEntity<String> loginResponse = restTemplate.postForEntity(ApiPath.AUTENTICAR, jwtUserDetails, String.class);
        JSONObject loginResponseJson = new JSONObject(loginResponse.getBody());

        return "Bearer " + loginResponseJson.getString("token");
    }

    @SneakyThrows
    public static HttpEntity<String> getValidHeaderAuthentication() {
        // Login and get JWT token
        JwtUserDetails jwtUserDetails = getValidCredentialsLogin();

        ResponseEntity<String> loginResponse = restTemplate.postForEntity(ApiPath.AUTENTICAR, jwtUserDetails, String.class);
        JSONObject loginResponseJson = new JSONObject(loginResponse.getBody());

        // Use JWT token to access protected endpoint
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + loginResponseJson.getString("token"));
        return new HttpEntity<>(headers);
    }
}
