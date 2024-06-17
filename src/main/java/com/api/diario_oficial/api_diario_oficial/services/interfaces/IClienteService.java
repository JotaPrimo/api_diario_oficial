package com.api.diario_oficial.api_diario_oficial.services.interfaces;

import com.api.diario_oficial.api_diario_oficial.entity.Cliente;
import com.api.diario_oficial.api_diario_oficial.web.dtos.clientes.ClienteSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface IClienteService extends IServiceBase<Cliente, Long> {

    Page<Cliente> search(ClienteSearchDTO searchDto, Pageable pageable);
}
