package com.api.diario_oficial.api_diario_oficial.enums;

import java.util.ArrayList;
import java.util.List;

public enum Role {
    ROLE_ADMIN,
    ROLE_COLABORADOR,
    ROLE_CLIENTE_ADMIN,
    ROLE_CLIENTE_COLABORADOR;

    public static List<Role> getRolesForClientes() {
        List<Role> roles = new ArrayList<>();
        roles.add(ROLE_CLIENTE_ADMIN);
        roles.add(ROLE_CLIENTE_COLABORADOR);
        return roles;
    }

    public static List<Role> getRolesForAdmin() {
        List<Role> roles = new ArrayList<>();
        roles.add(ROLE_ADMIN);
        roles.add(ROLE_CLIENTE_ADMIN);
        roles.add(ROLE_COLABORADOR);
        roles.add(ROLE_CLIENTE_COLABORADOR);
        return roles;
    }
}
