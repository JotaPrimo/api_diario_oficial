package com.api.diario_oficial.api_diario_oficial.database.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioSeeder usuarioSeeder;

    @Override
    public void run(String... args) throws Exception {
        //usuarioSeeder.seedUsuarios();
    }

}
