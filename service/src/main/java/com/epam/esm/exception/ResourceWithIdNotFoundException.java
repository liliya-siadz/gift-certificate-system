package com.epam.esm.exception;

public class ResourceWithIdNotFoundException extends RuntimeException {
    private final Long resourceId;
    private final String resourceName;

    public ResourceWithIdNotFoundException(String resourceName, Long resourceId) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
    }

    public String getErrorMessageKey() {
        return "not_found.with_id";
    }

    public Long getResourceId() {
        return resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }
}
