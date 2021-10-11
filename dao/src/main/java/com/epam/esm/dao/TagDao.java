package com.epam.esm.dao;

import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.TagEntityModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Data
@NoArgsConstructor
public class TagDao implements Dao<TagEntityModel> {
    private static final String FIND_TAG_BY_ID_QUERY =
            "SELECT id, name from tag where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TagRowMapper tagRowMapper;

    @Override
    public TagEntityModel findById(long id) {
        return jdbcTemplate.queryForObject(
                FIND_TAG_BY_ID_QUERY, tagRowMapper, id);
    }
}
