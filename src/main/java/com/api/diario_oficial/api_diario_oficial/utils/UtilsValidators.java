package com.api.diario_oficial.api_diario_oficial.utils;

public class UtilsValidators {

    public static boolean stringIsNullOrEmpty(String str) {
        return str == null || str.isBlank();
    }

    public static boolean integerIsNullOrZero(Integer number) {
        return number == null || number == 0;
    }

    public static boolean longIsNullOrZero(Long number) {
        return number == null || number == 0;
    }

}
