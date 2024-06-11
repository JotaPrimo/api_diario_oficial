package com.api.diario_oficial.api_diario_oficial.web.dtos.interfaces;

public interface IRecordSearch {
    boolean isEmpty();

    default boolean isAnyFieldFilled() {
        return !isEmpty();
    }

}