package com.tr.d_teknoloji.notebook.enums;

import lombok.Getter;

@Getter
public enum DataStatus {
    DELETED("Silinmiş", "001"),
    ACTIVE("Aktif", "002");

    private final String name;
    private final String code;

    DataStatus(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static DataStatus fromNameOrCodeValue(String value) {
        for (DataStatus status : DataStatus.values()) {
            if (status.name.equalsIgnoreCase(value) || status.code.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Bilinmeyen veri durumu değeri: " + value);
    }
}
