package com.epam.esm.exception;

/**
 * Thrown to indicate unknown sort param was used .
 */
public class UnknownSortParamException extends RuntimeException {

    /**
     * Sort param value .
     */
    private final String sortParamValue;

    /**
     * Constructs an <code>UnknownSortParamException</code>
     * with used unknown sort param value .
     *
     * @param sortParamValue {@link #sortParamValue}
     */
    public UnknownSortParamException(String sortParamValue) {
        this.sortParamValue = sortParamValue;
    }

    /**
     * Retrieves sort param value .
     *
     * @return sort param value
     */
    public String getSortParamValue() {
        return sortParamValue;
    }

    /**
     * Retrieves error message key .
     *
     * @return error message key of exception
     */
    public String getErrorMessageKey() {
        return "unknown_sort_param";
    }
}
