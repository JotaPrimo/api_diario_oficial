package com.api.diario_oficial.api_diario_oficial.database.seeder;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.services.implementations.UsuarioServiceImpl;
import com.github.javafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class UsuarioSeeder {

    private UsuarioServiceImpl usuarioService;

    private PasswordEncoder passwordEncoder;


    public UsuarioSeeder(UsuarioServiceImpl usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    public void seedUsuarios() {
        Faker faker = new Faker();
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Usuario usuario = new Usuario();
            usuario.setUsername(faker.name().username());
            usuario.setPassword(passwordEncoder.encode("12345678"));
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setRole(Role.ROLE_COLABORADOR);
            usuarioService.save(usuario);
        });
    }

}
