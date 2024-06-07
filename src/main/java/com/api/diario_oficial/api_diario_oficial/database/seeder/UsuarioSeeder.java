package com.api.diario_oficial.api_diario_oficial.database.seeder;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.services.implementations.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class UsuarioSeeder {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void seedUsuarios() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Usuario usuario = new Usuario();
            usuario.setUsername("usuario" + i + "@gmail.com");
            usuario.setPassword(passwordEncoder.encode("12345678"));
            usuario.setEmail("email" + i + "@email");
            usuario.setRole(Role.ROLE_COLABORADOR);
            usuarioService.save(usuario);
        });
    }
}
