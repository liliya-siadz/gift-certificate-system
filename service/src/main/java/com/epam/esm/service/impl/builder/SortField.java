package com.epam.esm.service.impl.builder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents accessible values for processing sorting Gift Certificates in search operations .
 * <p>
 *
 * @see com.epam.esm.service.GiftCertificateService#search(List, String, String, String, String, Integer, Integer)
 */
class SortField {

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
