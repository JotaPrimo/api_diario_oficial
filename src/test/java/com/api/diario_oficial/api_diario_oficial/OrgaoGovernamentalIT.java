package com.api.diario_oficial.api_diario_oficial;

import com.api.diario_oficial.api_diario_oficial.auth.AuthUtils;
import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.entity.OrgaoGovernamental;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental.OrgaoGovernamentalUpdateDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrgaoGovernamentalIT {

    // updateUser_shouldReturnUpdatedUser_whenAllFieldsAreValid
    // updateOrder_shouldFail_whenOrderDoesNotExist
    // updateCustomer_shouldReturnBadRequest_whenEmailIsInvalid
    //   updateProduct_shouldReturnForbidden_whenUserHasNoPermission


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @SneakyThrows
    void shouldReturnOrgaoGovernamentalCreated_WithSatus201_WhenValidData() {

        OrgaoGovernamental orgaoGovernamental = new OrgaoGovernamental("Prefeitura Municipal de Campo Grande", "82819492000106");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", AuthUtils.getValidHttpHeadersAuthentication().trim());

        HttpEntity<OrgaoGovernamental> request = new HttpEntity<>(orgaoGovernamental, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(ApiPath.HTTP + ApiPath.ORGAO_GOVERNAMENTAL, request, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotBlank();
        assertThat(jsonObject.has("cnpj")).isTrue();
        assertThat(jsonObject.getString("cnpj")).isNotEmpty();
        assertThat(jsonObject.getString("cnpj")).isEqualTo("82819492000106");
        assertThat(jsonObject.has("nome")).isTrue();
        assertThat(jsonObject.getString("nome")).isNotEmpty();
        assertThat(jsonObject.getString("nome")).isEqualTo("Prefeitura Municipal de Campo Grande");
    }

    @Test
    @SneakyThrows
    void updateOrgaoGovernamental_shouldReturnOrgaoGovernamental_WhenAllFieldsAreValid() {
        // cenario
        Long idUpdate = 139L;
        OrgaoGovernamentalUpdateDto orgaoGovernamentalUpdateDto =
                new OrgaoGovernamentalUpdateDto("Prefeitura Municipal de Aquidauana", "22959123000197");

        // ação
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", AuthUtils.getValidHttpHeadersAuthentication().trim());

        String url = ApiPath.HTTP + ApiPath.ORGAO_GOVERNAMENTAL + "/" + idUpdate;

        HttpEntity<OrgaoGovernamentalUpdateDto> request = new HttpEntity<>(orgaoGovernamentalUpdateDto, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        JSONObject jsonObject = new JSONObject(response.getBody());

        // assertion
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotBlank();
        assertThat(jsonObject.has("nome")).isTrue();
        assertThat(jsonObject.get("nome")).isEqualTo("Prefeitura Municipal de Aquidauana");
        assertThat(jsonObject.has("cnpj")).isTrue();
        assertThat(jsonObject.get("cnpj")).isEqualTo("22959123000197");
    }
}
