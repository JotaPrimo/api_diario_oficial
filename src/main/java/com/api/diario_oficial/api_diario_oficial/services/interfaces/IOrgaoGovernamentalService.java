package com.api.diario_oficial.api_diario_oficial.services.interfaces;

import com.api.diario_oficial.api_diario_oficial.entity.OrgaoGovernamental;
import com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental.OrgaoGovernamentalSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;



@Component
public interface IOrgaoGovernamentalService extends IServiceBase<OrgaoGovernamental, Long> {
    Page<OrgaoGovernamental> search(OrgaoGovernamentalSearchDto searchDto, Pageable pageable);
}
