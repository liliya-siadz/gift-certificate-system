package com.epam.esm.entity.security;

/**
 * Describes users privileges .
 */
public enum Privilege {
    GIFT_CERTIFICATES_CREATE("gift_certificates:create"),
    GIFT_CERTIFICATES_DELETE("gift_certificates:delete"),
    GIFT_CERTIFICATES_UPDATE("gift_certificates:update"),
    ORDERS_READ("orders:read"),
    TAGS_CREATE("tags:create"),
    TAGS_DELETE("tags:delete"),
    USERS_READ("users:read"),
    ORDERS_READ_BY_ID("orders:read"),
    ORDERS_CREATE("orders:create");

    /**
     * Privilege action's name .
     */
    private final String action;

    /**
     * Constructs <code>Privilege</code> enum .
     *
     * @param action {@link #action}
     */
    Privilege(String action) {
        this.action = action;
    }

    /**
     * Retrieves privilege action's name .
     *
     * @return privilege action's name
     */
    public String getAction() {
        return action;
    }
}
