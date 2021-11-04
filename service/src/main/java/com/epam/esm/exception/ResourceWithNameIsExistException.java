package com.epam.esm.exception;

/**
 * Thrown to indicate that resource with attribute name,
 * that must be unique value is already exist .
 */
public class ResourceWithNameIsExistException extends RuntimeException {

    /**
     * Name of resource .
     */
    private final String resourceName;

    /**
     * Value of attribute 'name' of resource .
     */
    private final String name;

    /**
     * Constructs an <code>ResourceIsAlreadyExist</code>
     * with name of resource, value of attribute 'name' and cause.
     *
     * @param resourceName     {@link #resourceName}
     * @param name             {@link #name}
     * @param cause            the cause (which is saved for later retrieval by the
     *                         {@link Throwable#getCause()} method).  (A <tt>null</tt> value
     *                         is permitted, and indicates that the cause is nonexistent or
     *                         unknown.)
     */
    public ResourceWithNameIsExistException(String resourceName, String name,
                                            Throwable cause) {
        super(cause);
        this.resourceName = resourceName;
        this.name = name;
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
     * Retrieves value of attribute 'name' of resource .
     *
     * @return value of attribute 'name' of resource
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves error message key .
     *
     * @return error message key of exception
     */
    public String getErrorMessageKey() {
        return "exist.with_name";
    }
}
