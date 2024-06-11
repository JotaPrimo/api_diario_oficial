package com.api.diario_oficial.api_diario_oficial.services.implementations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PaginationService {

    public static <T> Page<T> paginate(List<T> list, Pageable pageable) {
        int total = list.size();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), total);
        List<T> sublist = list.subList(start, end);
        return new PageImpl<>(sublist, pageable, total);
    }

}
