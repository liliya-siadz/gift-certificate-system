package com.epam.esm.mapper;

import com.epam.esm.model.GiftCertificateEntityModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificateEntityModel> {
    private static final String ISO_8601_DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

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

