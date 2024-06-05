package com.api.diario_oficial.api_diario_oficial.enums;

import java.util.ArrayList;
import java.util.List;

public enum StatusUsuario {
    ATIVO,
    INATIVO,
    SUSPENSO;

    public static List<StatusUsuario> getAllEnumStatus() {
        List<StatusUsuario> listStatus = new ArrayList<>();
        listStatus.add(ATIVO);
        listStatus.add(INATIVO);
        listStatus.add(SUSPENSO);
        return listStatus;
    }
}