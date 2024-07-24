package com.api.diario_oficial.api_diario_oficial.database.seeder;

import com.api.diario_oficial.api_diario_oficial.database.repository.IEnderecoRepository;
import com.api.diario_oficial.api_diario_oficial.entity.Endereco;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class EnderecoSeeder {

    @Autowired
    private IEnderecoRepository enderecoRepository;

    public List<String> getSiglasEstado() {
        List<String> siglasEstados = new ArrayList<>();
        siglasEstados.add("AC");
        siglasEstados.add("AL");
        siglasEstados.add("AP");
        siglasEstados.add("AM");
        siglasEstados.add("BA");
        siglasEstados.add("CE");
        siglasEstados.add("DF");
        siglasEstados.add("ES");
        siglasEstados.add("GO");
        siglasEstados.add("MA");
        siglasEstados.add("MT");
        siglasEstados.add("MS");
        siglasEstados.add("MG");
        siglasEstados.add("PA");
        siglasEstados.add("PB");
        siglasEstados.add("PR");
        siglasEstados.add("PE");
        siglasEstados.add("PI");
        siglasEstados.add("RJ");
        siglasEstados.add("RN");
        siglasEstados.add("RS");
        siglasEstados.add("RO");
        siglasEstados.add("RR");
        siglasEstados.add("SC");
        siglasEstados.add("SP");
        siglasEstados.add("SE");
        siglasEstados.add("TO");

        return siglasEstados;
    }

    public int randonNumber() {
        Random random = new Random();
       return random.nextInt(12) + 1;
    }

    private static String gerarCepsAleatorios() {
        Random random = new Random();
        int prefixo = 10000 + random.nextInt(90000);
        int sufixo = 100 + random.nextInt(900);
        return String.format("%05d-%03d", prefixo, sufixo);
    }

    public void seed() {
        Faker faker = new Faker();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Endereco endereco = new Endereco();
            endereco.setCep(gerarCepsAleatorios());
            endereco.setLogradouro(faker.address().streetName());
            endereco.setCidade(faker.address().cityName());

            endereco.setEstado(getSiglasEstado().get(randonNumber()));

            enderecoRepository.save(endereco);
        });
    }

}
