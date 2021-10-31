package com.epam.esm.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response body object of exception handling mechanism ,
 * is used in class {@link ControllerAdvice} .
 */
@Data
@AllArgsConstructor
public class CustomResponse {

    /**
     * Error code of handled exception .
     */
    private int errorCode;

    /**
     * Error message of handled exception (might be parametrized) .
     */
    private String errorMessage;
}
