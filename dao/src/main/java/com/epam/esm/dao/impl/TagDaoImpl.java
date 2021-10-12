package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.TagEntityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TagDaoImpl implements TagDao<TagEntityModel> {
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
