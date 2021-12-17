package com.epam.esm.entity.security;

public enum Privilege {
<<<<<<< HEAD
    GIFT_CERTIFICATES_CREATE("gift_certificates:create"),
    GIFT_CERTIFICATES_DELETE("gift_certificates:delete"),
    GIFT_CERTIFICATES_UPDATE("gift_certificates:update"),
    ORDERS_READ("orders:read"),
    TAGS_CREATE("tags:create"),
    TAGS_DELETE("tags:delete"),
    USERS_READ("users:read"),
    ORDERS_READ_BY_ID("orders:read"),
    ORDERS_CREATE("orders:create");
=======
    TAGS_CREATE("tags:create"),
    TAGS_DELETE("tags:delete"),
    CERTIFICATES_CREATE("certificates:create"),
    CERTIFICATES_UPDATE("certificates:update"),
    CERTIFICATES_DELETE("certificates:delete"),
    USERS_READ("users:read"),
    ORDERS_CREATE("orders:create"),
    ORDERS_READ("orders:read"),
    AUTH_SIGN_UP("auth:sign_up");
>>>>>>> 79b8fef (1) Jwt token created; 2) Signup and login functions realized)

    private final String action;

    Privilege(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
