package com.api.diario_oficial.api_diario_oficial.services.interfaces;

import com.api.diario_oficial.api_diario_oficial.entity.Endereco;
import com.api.diario_oficial.api_diario_oficial.web.dtos.enderecos.EnderecoSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEnderecoService extends IServiceBase<Endereco, Long> {

    Page<Endereco> search(EnderecoSearchDTO searchDto, Pageable pageable);

}
