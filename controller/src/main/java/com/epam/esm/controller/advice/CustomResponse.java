package com.epam.esm.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomResponse {
    private int errorCode;
    private String errorMessage;
}
