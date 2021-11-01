package com.epam.esm.dao.builder;

/**
 * Represents accessible fields for ordering searched Gift Certificates .
 * <p>
 * Value's name could be used as part of param 'sort' in method
 * {@link GiftCertificateQueryBuilder#buildSearchQuery(String, String, String, String)} .
 */
enum SortField {

    /**
     * Part of sort param for ordering by
     * <b>name</b> of Gift Certificate (for ex. 'ORDER BY name') .
     */
    NAME,

    /**
     * Part of sort param for ordering by
     * <b>create date</b> of Gift Certificate (for ex.'ORDER BY create_date') .
     */
    CREATE_DATE
}
