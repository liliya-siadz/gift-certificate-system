package com.epam.esm.mapper;

import com.epam.esm.model.GiftCertificateEntityModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.epam.esm.util.DateTimeUtil.convertToDateTimeIso8601;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificateEntityModel> {

    @Override
    public GiftCertificateEntityModel mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        LocalDateTime createDate = convertToDateTimeIso8601(resultSet.getTimestamp("create_date"));
        LocalDateTime lastUpdateDate = convertToDateTimeIso8601(resultSet.getTimestamp("last_update_date"));
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
