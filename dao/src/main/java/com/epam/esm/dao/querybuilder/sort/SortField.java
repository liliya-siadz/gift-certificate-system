package com.epam.esm.dao.querybuilder.sort;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents accessible values (sort fields)
 * for parameter 'sortField' in method {@link com.epam.esm.dao.GiftCertificateDao#search} .
 *
 * <p>
 */
public class SortField {

    /**
     * Set of sort fields values .
     */
    public static final Set<String> sortFields = new HashSet<>();
    static {
        sortFields.add("name");
        sortFields.add("createDate");
    }

    private SortField() {
    }
}
