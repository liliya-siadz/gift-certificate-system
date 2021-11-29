package com.epam.esm.exception;

/**
 * Thrown to indicate that resource contains duplicated
 * values in it's related set of resources .
 */
public class ResourceContainsDuplicateValuesException extends RuntimeException {

    /**
     * Name of resource .
     */
    private final String resourceName;

    /**
     * Information about duplicated values .
     */
    private final String duplicatesInfo;

    /**
     * Constructs <code>ResourceContainsDuplicateValuesException</code> class
     * with passed name of resource and information about duplicates .
     *
     * @param resourceName   {@link #resourceName}
     * @param duplicatesInfo {@link #duplicatesInfo}
     */
    public ResourceContainsDuplicateValuesException(String resourceName, String duplicatesInfo) {
        this.resourceName = resourceName;
        this.duplicatesInfo = duplicatesInfo;
    }

    /**
     * Retrieves name of resource .
     *
     * @return name of resource
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Retrieves information about duplicates .
     *
     * @return information about duplicates
     */
    public String getDuplicatesInfo() {
        return duplicatesInfo;
    }

    /**
     * Retrieves error message key .
     *
     * @return error message key of exception
     */
    public String getErrorMessageKey() {
        return "duplicate_values";
    }
}
