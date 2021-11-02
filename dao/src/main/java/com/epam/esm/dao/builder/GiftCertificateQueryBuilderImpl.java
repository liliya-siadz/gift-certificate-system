package com.epam.esm.dao.builder;

import com.epam.esm.exception.UnknownSortParamException;
import com.epam.esm.model.GiftCertificateEntityModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * Implementation of interface {@link GiftCertificateQueryBuilder}
 * for building search and update sql queries for Gift Certificate .
 */
@Component
public class GiftCertificateQueryBuilderImpl implements GiftCertificateQueryBuilder {
    private static final String UPDATE_PARAMS_DELIMITER = ",";
    private static final String UPDATE_COLUMN_AND_VALUE_DELIMITER = " = ?";
    private static final String UPDATE_QUERY_PREFIX = "UPDATE gift_certificate SET ";
    private static final String SET_LAST_UPDATE_DATE_VALUE_TO_NOW_PARAM =
            "last_update_date= (SELECT now()::timestamp)";
    private static final String SEARCH_QUERY_PREFIX =
            "SELECT distinct gift_certificate.create_date,  gift_certificate.id,"
                    + " gift_certificate.name, gift_certificate.description, gift_certificate.price,"
                    + " gift_certificate.duration, gift_certificate.last_update_date"
                    + " FROM gift_certificates_tags"
                    + " JOIN tag"
                    + " ON gift_certificates_tags.tag_id = tag.id"
                    + " JOIN gift_certificate"
                    + " ON gift_certificates_tags.gift_certificate_id = gift_certificate.id ";
    private static final String SEARCH_PREDICATE_PARAMS_DELIMITER = " AND ";

    @Override
    public String buildUpdateQuery(long id, GiftCertificateEntityModel entity) {
        Map<String, FieldDescription> paramsForUpdate = findUpdateParams(entity);
        if (!paramsForUpdate.isEmpty()) {
            StringJoiner params = new StringJoiner(UPDATE_PARAMS_DELIMITER);
            paramsForUpdate.forEach((columnName, columnValue)
                    -> params.add(columnName + UPDATE_COLUMN_AND_VALUE_DELIMITER));
            return UPDATE_QUERY_PREFIX + params + UPDATE_PARAMS_DELIMITER
                    + SET_LAST_UPDATE_DATE_VALUE_TO_NOW_PARAM + " WHERE id=" + id;
        } else {
            return EMPTY_STRING;
        }
    }

    @Override
    public String buildSearchQuery(String tagName, String name, String description, String sort) {
        return SEARCH_QUERY_PREFIX + formSearchPredicate(tagName, name, description) + formSortClause(sort);
    }

    @Override
    public Map<String, FieldDescription> findUpdateParams(GiftCertificateEntityModel entity) {
        Map<String, FieldDescription> params = new LinkedHashMap<>();
        String name = entity.getName();
        String description = entity.getDescription();
        BigDecimal price = entity.getPrice();
        int duration = entity.getDuration();
        LocalDateTime createDate = entity.getCreateDate();
        if (name != null) {
            params.put("name", new FieldDescription(name, Types.VARCHAR));
        }
        if (description != null) {
            params.put("description", new FieldDescription(description, Types.VARCHAR));
        }
        if (price != null) {
            params.put("price", new FieldDescription(price, Types.NUMERIC));
        }
        if (duration != 0) {
            params.put("duration", new FieldDescription(duration, Types.SMALLINT));
        }
        if (createDate != null) {
            params.put("createDate", new FieldDescription(createDate, Types.TIMESTAMP));
        }
        return params;
    }

    private String formSortClause(String sortParamValue) {
        if (sortParamValue != null) {
            String directionFlag = sortParamValue.substring(0, 1);
            Optional<SortDirection> sortDirection
                    = SortDirection.getValueByDirectionFlag(directionFlag);
            String sortFieldName = (sortDirection.isPresent())
                    ? sortParamValue.substring(1)
                    : sortParamValue;
            if (Arrays.stream(SortField.values()).anyMatch(
                    value -> value.name().toLowerCase().equals(sortFieldName))) {
                return " ORDER BY " + sortFieldName + " " + (sortDirection.map(Enum::name).orElseGet(SortDirection.ASC::name));
            } else {
                throw new UnknownSortParamException(sortFieldName);
            }
        }
        return EMPTY_STRING;
    }

    private String formSearchPredicate(String tagName, String name, String description) {
        StringJoiner predicate = new StringJoiner(SEARCH_PREDICATE_PARAMS_DELIMITER);
        if (tagName != null) {
            predicate.add("tag.name = '" + tagName + "'");
        }
        if (name != null) {
            predicate.add("gift_certificate.name LIKE '%" + name + "%'");
        }
        if (description != null) {
            predicate.add("gift_certificate.description LIKE '%" + description + "%'");
        }
        return (predicate.length() == 0)
                ? EMPTY_STRING
                : ("WHERE " + predicate);
    }
}
