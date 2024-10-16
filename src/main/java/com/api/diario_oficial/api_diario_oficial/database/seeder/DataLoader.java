package com.api.diario_oficial.api_diario_oficial.database.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioSeeder usuarioSeeder;

    @Autowired
    private EnderecoSeeder enderecoSeeder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=======================================================");
        System.out.println("=======================================================");
        System.out.println("=======================================================");
        System.out.println("=======================================================");
        usuarioSeeder.seedUsuarios();
        // enderecoSeeder.seed();
    }
}
