package com.epam.esm.mapper;

import com.epam.esm.model.TagEntityModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagRowMapper implements RowMapper<TagEntityModel> {

    @Override
    public TagEntityModel mapRow(ResultSet resultSet, int rowNumber)
            throws SQLException {
        return new TagEntityModel(resultSet.getLong("id"),
                resultSet.getString("name"));
    }
}
