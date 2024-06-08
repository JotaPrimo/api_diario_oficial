package com.api.diario_oficial.api_diario_oficial.auth;

import com.api.diario_oficial.api_diario_oficial.enums.Role;

import java.time.LocalDateTime;

public class Credentials {

    // Constantes para dados de teste
    public static final String TEST_USERNAME = "jughead_jones";
    public static final String TEST_USERNAME_INVALID = "nes";
    public static final String TEST_EMAIL = "jughead_jones@gmail.com";
    public static final String TEST_PASSWORD = "12345678";
    public static final String TEST_PASSWORD_INVALID = "678";
    public static final LocalDateTime TEST_CREATED_AT = LocalDateTime.now();
    public static final Role TEST_ROLE = Role.ROLE_COLABORADOR;

    // autenticação
    public static final String TEST_LOGIN_INVALID = "000000";
    public static final String TEST_LOGIN_VALID = "asia.graham";
    public static final String TEST_PASSWORD_VALID = "12345678";
    
}
