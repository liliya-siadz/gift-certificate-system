package com.epam.esm.mapper;

import com.epam.esm.model.GiftCertificateEntityModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Maps database result set to entity model of Gift Certificate .
 */
@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificateEntityModel> {
    private static final String ISO_8601_DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * Maps each row of result set to Gift Certificate entity model .
     *
     * @param resultSet result set to extract params
     * @param rowNumber row number of current row
     * @return entity model of Gift Certificate with parameters from result set .
     * @throws SQLException if an SQLException is encountered getting column values
     */
    @Override
    public GiftCertificateEntityModel mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        LocalDateTime createDate = resultSet.getTimestamp("create_date").toLocalDateTime();
        createDate.format(ofPattern(ISO_8601_DATE_TIME_FORMAT_PATTERN));
        LocalDateTime lastUpdateDate = resultSet.getTimestamp("last_update_date").toLocalDateTime();
        createDate.format(ofPattern(ISO_8601_DATE_TIME_FORMAT_PATTERN));
        return GiftCertificateEntityModel.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .price(resultSet.getBigDecimal("price"))
                .duration(resultSet.getInt("duration"))
                .createDate(createDate)
                .lastUpdateDate(lastUpdateDate)
                .build();
    }
}

