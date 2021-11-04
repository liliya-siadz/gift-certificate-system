package com.epam.esm.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class is used as response body object
 * of exception handling mechanism in class {@link ControllerAdvice} .
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

    /**
     * Error code of handled exception .
     */
    private int errorCode;

    /**
     * Error message of handled exception (could be parametrized) .
     */
    private String errorMessage;
}
