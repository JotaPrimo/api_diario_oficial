package com.api.diario_oficial.api_diario_oficial;

import com.api.diario_oficial.api_diario_oficial.auth.AuthUtils;
import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    WebTestClient webClient;

    @SneakyThrows
    @Test
    void shouldReturnListClientes() {
       HttpEntity<String> entity = AuthUtils.getValidHeaderAuthentication();

        ResponseEntity<String> response = restTemplate.exchange(ApiPath.CLIENTES, HttpMethod.GET, entity, String.class);
        JSONObject responseJson = new JSONObject(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseJson.has("content")).isTrue();
        assertThat(responseJson.has("pageable")).isTrue();
        assertThat(responseJson.has("totalPages")).isTrue();
        assertThat(responseJson.has("totalElements")).isTrue();
        assertThat(responseJson.has("last")).isTrue();
        assertThat(responseJson.has("size")).isTrue();

        assertThat(responseJson.getString("content")).isNotEmpty();
        JSONArray jsonArray = responseJson.getJSONArray("content");
        assertThat(jsonArray).isNotNull();
    }

}
