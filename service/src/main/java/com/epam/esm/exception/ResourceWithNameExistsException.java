package com.epam.esm.exception;

/**
 * Thrown to indicate that resource with attribute name,
 * that must be unique value is already exist .
 */
public class ResourceWithNameExistsException extends RuntimeException {

    /**
     * Name of resource .
     */
    private final String resourceName;

    /**
     * Name value attribute of resource
     */
    private final String nameValue;

    /**
     * Constructs an <code>ResourceIsAlreadyExist</code>
     * with name of resource, value of attribute 'name' and cause.
     *
     * @param resourceName {@link #resourceName}
     * @param nameValue    {@link #nameValue}
     * @param cause        the cause (which is saved for later retrieval by the
     *                     {@link Throwable#getCause()} method).  (A <tt>null</tt> value
     *                     is permitted, and indicates that the cause is nonexistent or
     *                     unknown.)
     */
    public ResourceWithNameExistsException(String resourceName, String nameValue, Throwable cause) {
        super(cause);
        this.resourceName = resourceName;
        this.nameValue = nameValue;
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
     * Retrieves name value attribute of resource
     *
     * @return name value attribute of resource
     */
    public String getNameValue() {
        return nameValue;
    }

    /**
     * Retrieves error message key .
     *
     * @return error message key of exception
     */
    public String getErrorMessageKey() {
        return "already_exist_with_name";
    }
}
