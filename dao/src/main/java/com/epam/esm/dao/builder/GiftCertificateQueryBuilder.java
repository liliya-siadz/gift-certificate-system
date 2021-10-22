package com.epam.esm.dao.builder;

import com.epam.esm.model.GiftCertificateEntityModel;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

public class GiftCertificateQueryBuilder {
    public static final String EMPTY_STRING = "";

    private GiftCertificateQueryBuilder() {
    }

    public static String buildUpdateQuery(long id, GiftCertificateEntityModel entity) {
        Map<String, FieldDescription> paramsForUpdate = extractUpdateParams(entity);
        if (paramsForUpdate.isEmpty()) {
            return EMPTY_STRING;
        }
        String suffix = "UPDATE gift_certificate SET ";
        String prefix = ", last_update_date= (SELECT now()::timestamp) WHERE id=" + id;
        String paramsDelimiter = ",";
        String columnAndValueDelimiter = " = ?";
        StringJoiner params = new StringJoiner(paramsDelimiter);
        paramsForUpdate.forEach((columnName, columnValue)
                -> params.add(columnName + columnAndValueDelimiter));
        return suffix + params + prefix;
    }

    public static String buildSearchQuery(String tagName, String name, String description, String sort) {
        String prefix = "SELECT distinct gift_certificate.create_date,  gift_certificate.id,"
                + " gift_certificate.name, gift_certificate.description, gift_certificate.price,"
                + " gift_certificate.duration, gift_certificate.last_update_date"
                + " FROM student.gift_certificates_tags"
                + " JOIN student.tag"
                + " ON gift_certificates_tags.tag_id = tag.id"
                + " JOIN student.gift_certificate"
                + " ON gift_certificates_tags.gift_certificate_id = gift_certificate.id ";
        return prefix + formSearchPredicate(tagName, name, description) + formSortClause(sort);
    }

    public static Map<String, FieldDescription> extractUpdateParams(GiftCertificateEntityModel entity) {
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

    private static String formSortClause(String sortParamValue) {
        if (sortParamValue == null) {
            return EMPTY_STRING;
        }
        String directionFlag = sortParamValue.substring(0, 1);
        Optional<SortDirection> sortDirection = SortDirection.getValueByDirectionFlag(directionFlag);
        String sortFieldName = (sortDirection.isPresent())
                ? sortParamValue.substring(1)
                : sortParamValue;
        if (Arrays.stream(SortField.values()).anyMatch(
                value -> value.name().toLowerCase().equals(sortFieldName))) {
            return " ORDER BY " + sortFieldName + " "
                    + (sortDirection.map(Enum::name).orElseGet(SortDirection.ASC::name));
        } else {
            throw new IllegalArgumentException("Sort field with name: " + sortFieldName + " not found!");
        }
    }

    private static String formSearchPredicate(String tagName, String name, String description) {
        StringJoiner predicate = new StringJoiner(" AND ");
        if (tagName != null) {
            predicate.add("tag.name = '" + tagName + "'");
        }
        if (name != null) {
            predicate.add("gift_certificate.name LIKE '%" + name + "%'");
        }
        if (description != null) {
            predicate.add("gift_certificate.description LIKE '%" + description + "%'");
        }
        return (predicate.length() == 0) ? EMPTY_STRING : ("WHERE " + predicate);
    }
}
