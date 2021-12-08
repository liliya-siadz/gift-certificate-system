package com.epam.esm.dao.builder.sort;

/**
 * Thrown to indicate that unknown sort field
 * was passed (parameter 'sortDirection' in method
 * {@link com.epam.esm.dao.GiftCertificateDao#search} .
 *
 * <p>
 */
public class UnknownSortFieldException extends RuntimeException {

    /**
     * Unknown sort field value .
     */
    private final String sortFieldValue;

    /**
     * Constructs <code>UnknownSortFieldException</code> exception
     * with sort field value, that unknown .
     *
     * @param sortFieldValue {@link #sortFieldValue}
     */
    public UnknownSortFieldException(String sortFieldValue) {
        this.sortFieldValue = sortFieldValue;
    }

    /**
     * Retrieves unknown sort field value .
     *
     * @return unknown sort field value
     */
    public String getSortFieldValue() {
        return sortFieldValue;
    }
}
