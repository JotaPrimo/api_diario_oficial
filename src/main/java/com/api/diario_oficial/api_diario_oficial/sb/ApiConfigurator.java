package com.api.diario_oficial.api_diario_oficial.sb;

import com.api.diario_oficial.api_diario_oficial.database.repository.IClienteRepository;
import com.api.diario_oficial.api_diario_oficial.database.repository.IEnderecoRepository;
import com.api.diario_oficial.api_diario_oficial.database.repository.IOrgaoGovernamentalRepository;
import com.api.diario_oficial.api_diario_oficial.database.repository.IUsuarioRepository;
import com.api.diario_oficial.api_diario_oficial.services.implementations.ClienteServiceImpl;
import com.api.diario_oficial.api_diario_oficial.services.implementations.EnderecoServiceImpl;
import com.api.diario_oficial.api_diario_oficial.services.implementations.OrgaoGovernamentalServiceImpl;
import com.api.diario_oficial.api_diario_oficial.services.implementations.UsuarioServiceImpl;
import com.api.diario_oficial.api_diario_oficial.validation.rules.usuario.store.GerenciadorUsuarioValidators;
import com.api.diario_oficial.api_diario_oficial.validation.rules.usuario.update.GerenciadorUsuarioUpdateValidators;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfigurator {

    @Bean
    public OrgaoGovernamentalServiceImpl orgaoGovernamentalService(IOrgaoGovernamentalRepository orgaoGovernamentalRepository) {
        return new OrgaoGovernamentalServiceImpl(orgaoGovernamentalRepository);
    }

    @Bean
    public ClienteServiceImpl clienteService(IClienteRepository clienteRepository) {
        return new ClienteServiceImpl(clienteRepository);
    }

    @Bean
    public EnderecoServiceImpl enderecoService(IEnderecoRepository enderecoRepository) {
        return new EnderecoServiceImpl(enderecoRepository);
    }

    @Bean
    public UsuarioServiceImpl usuarioService(IUsuarioRepository usuarioRepository) {
        return new UsuarioServiceImpl(usuarioRepository, gerenciadorUsuarioValidators(usuarioRepository), gerenciadorUsuarioUpdateValidators(usuarioRepository));
    }

    @Bean
    public GerenciadorUsuarioValidators gerenciadorUsuarioValidators(IUsuarioRepository usuarioRepository) {
        return  new GerenciadorUsuarioValidators(usuarioRepository);
    }

    @Bean
    public GerenciadorUsuarioUpdateValidators gerenciadorUsuarioUpdateValidators(IUsuarioRepository usuarioRepository) {
        return  new GerenciadorUsuarioUpdateValidators(usuarioRepository);
    }

}
