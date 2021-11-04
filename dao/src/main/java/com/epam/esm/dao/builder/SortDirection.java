package com.epam.esm.dao.builder;

import java.util.Optional;

/**
 * Represents accessible sort directions for searched Gift Certificates .
 * <p>
 */
enum SortDirection {

    /**
     * Part of sort param for <b>ascending</b> sorting
     * (for ex. 'ORDER BY [smth] ASC') .
     */
    ASC("1"),

    /**
     * Part of sort param for <b>descending</b> sorting
     * (for ex. 'ORDER BY [smth] DESC') .
     */
    DESC("0");

    /**
     * Could be used as part of param 'sort' in method
     * {@link GiftCertificateQueryBuilder#buildSearchQuery(String, String, String, String)},
     * for ex. if sort param equals '0name', direction flag will be 0
     * and sort direction will be descending .
     */
    private final String directionFlag;

    /**
     * Constructs {@code SortDirection} with specified direction flag .
     *
     * @param directionFlag {@link #directionFlag}
     */
    SortDirection(String directionFlag) {
        this.directionFlag = directionFlag;
    }

    /**
     * Retrieves value of field {@link #directionFlag} .
     *
     * @return {@link #directionFlag}
     */
    public String getDirectionFlag() {
        return directionFlag;
    }

    /**
     * Returns first found {@code SortDirection} value by passed direction flag .
     *
     * @param directionFlag direction flag {@link #directionFlag}
     * @return Optional of first found value with equals direction flag,
     * if no values were found returns Optional.empty()
     */
    public static Optional<SortDirection> getValueByDirectionFlag(String directionFlag) {
        for (SortDirection value : values()) {
            if (value.getDirectionFlag().equals(directionFlag)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
