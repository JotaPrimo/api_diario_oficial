package com.api.diario_oficial.api_diario_oficial.web.controllers;

import com.api.diario_oficial.api_diario_oficial.config.ApiPath;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(ApiPath.ROLES)
public class RoleController {

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = Arrays.asList(Role.values());
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/get-roles-for-clientes")
    public ResponseEntity<List<Role>> getRolesForClientes() {
        List<Role> roles = Role.getRolesForClientes();
        return ResponseEntity.ok(roles);
    }
}
