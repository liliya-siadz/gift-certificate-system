package com.epam.esm.entity.security;

public enum Privilege {
    TAGS_CREATE("tags:create"),
    TAGS_DELETE("tags:delete"),
    CERTIFICATES_CREATE("certificates:create"),
    CERTIFICATES_UPDATE("certificates:update"),
    CERTIFICATES_DELETE("certificates:delete"),
    USERS_READ("users:read"),
    ORDERS_CREATE("orders:create"),
    ORDERS_READ("orders:read"),
    AUTH_SIGN_UP("auth:sign_up");

    private final String action;

    Privilege(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
