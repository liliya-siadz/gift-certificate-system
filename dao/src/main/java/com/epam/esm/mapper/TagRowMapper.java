package com.epam.esm.mapper;

import com.epam.esm.model.TagEntityModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps database result set to entity model of Tag .
 */
@Component
public class TagRowMapper implements RowMapper<TagEntityModel> {

    /**
     * Maps each row of result set to Tag entity model .
     *
     * @param resultSet result set to extract params
     * @param rowNumber row number of current row
     * @return entity model of Gift Certificate with parameters from result set .
     * @throws SQLException if an SQLException is encountered getting column values
     */
    @Override
    public TagEntityModel mapRow(ResultSet resultSet, int rowNumber)
            throws SQLException {
        return new TagEntityModel(resultSet.getLong("id"),
                resultSet.getString("name"));
    }
}
