package com.epam.esm.exception;

/**
 * Thrown to indicate that requested page of resources
 * doesn't exist .
 */
public class PageNotExistException extends RuntimeException {
    /**
     * Name of resource .
     */
    private final String resourceName;

    /**
     * Page number, that doesn't exist
     */
    private final int pageNumber;

    /**
     * Constructs <code>PageNotFoundException</code> with
     * passed name of resource and page number .
     *
     * @param resourceName {@link #resourceName}
     * @param pageNumber {@link #pageNumber}
     */
    public PageNotExistException(String resourceName, int pageNumber) {
        this.resourceName = resourceName;
        this.pageNumber = pageNumber;
    }

    /**
     * Retrieves resource name .
     *
     * @return resource name
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Retrieves page number .
     *
     * @return page number
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Retrieves error message key .
     *
     * @return error message key of exception
     */
    public String getErrorMessageKey() {
        return "page_not_exist";
    }
}
