package com.epam.esm.dao.builder;

import com.epam.esm.model.GiftCertificateEntityModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Description of Gift Certificate param to update,
 * stores param's value and param's type .
 * <p>
 * Class is used as value of returning map in method
 * {@link GiftCertificateQueryBuilder#findUpdateParams(GiftCertificateEntityModel)}
 */
@Data
@AllArgsConstructor
public class FieldDescription {

    /**
     * New value of updating param .
     */
    private Object value;

    /**
     * Sql type code of updating param, see {@link java.sql.Types} .
     */
    private int sqlTypeCode;
}
