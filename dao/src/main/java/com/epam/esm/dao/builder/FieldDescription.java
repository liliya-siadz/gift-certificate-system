package com.epam.esm.dao.builder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldDescription {
    private Object value;
    private int sqlTypeCode;
}
