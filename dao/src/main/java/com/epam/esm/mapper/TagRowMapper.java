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
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        TagEntityModel tagEntityModel = new TagEntityModel();
        tagEntityModel.setId(id);
        tagEntityModel.setName(name);
        return tagEntityModel;
    }
}
