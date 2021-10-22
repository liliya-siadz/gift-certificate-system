package com.epam.esm.dao.builder;

import java.util.Optional;

enum SortDirection {
    ASC("1"),
    DESC("0");

    private final String directionFlag;

    SortDirection(String directionFlag) {
        this.directionFlag = directionFlag;
    }

    public String getDirectionFlag() {
        return directionFlag;
    }

    public static Optional<SortDirection> getValueByDirectionFlag(String directionFlag) {
        for (SortDirection value : values()) {
            if (value.getDirectionFlag().equals(directionFlag)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
