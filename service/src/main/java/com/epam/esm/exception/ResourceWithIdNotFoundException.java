package com.epam.esm.exception;

/**
 * Thrown to indicate that resource with specified id is not found .
 */
public class ResourceWithIdNotFoundException extends RuntimeException {

    /**
     * Id of resource .
     */
    private final Long resourceId;

    /**
     * Name of resource .
     */
    private final String resourceName;

    /**
     * Constructs an <code>ResourceWithIdNotFoundException</code>
     * with name of resource and id of resource .
     *
     * @param resourceName {@link #resourceName}
     * @param resourceId   {@link #resourceId}
     */
    public ResourceWithIdNotFoundException(String resourceName, Long resourceId) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
    }

    /**
     * Retrieves error message key .
     *
     * @return error message key of exception
     */
    public String getErrorMessageKey() {
        return "not_found.with_id";
    }

    /**
     * Retrieves id of resource .
     *
     * @return id of resource
     */
    public Long getResourceId() {
        return resourceId;
    }

    /**
     * Retrieves name of resource .
     *
     * @return name of resource
     */
    public String getResourceName() {
        return resourceName;
    }
}
