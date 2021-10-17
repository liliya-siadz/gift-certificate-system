package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.TagEntityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class TagDaoImpl implements TagDao {
    private static final String FIND_TAG_BY_ID_QUERY =
            "SELECT id, name from tag where id = ?";
    private static final String FIND_ALL_TAGS_QUERY =
            "SELECT id, name from tag";
    private static final String CREATE_TAG_QUERY =
            "INSERT INTO tag (name) VALUES (?)";
    private static final String DELETE_TAG_QUERY =
            "DELETE FROM tag WHERE id = ?";
    private static final String COUNT_TAG_BOUNDS =
            "SELECT count(id) from gift_certificates_tags where tag_id = ? ";
    private static final String GET_IS_TAG_EXIST_QUERY =
            "SELECT EXISTS(SELECT 1 FROM tag WHERE id = ?)";

    private final JdbcTemplate jdbcTemplate;

    private final TagRowMapper tagRowMapper;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate,
                      TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
    }

    @Override
    public TagEntityModel findById(long id) {
        if (isExist(id)) {
            return jdbcTemplate.queryForObject(FIND_TAG_BY_ID_QUERY,
                    tagRowMapper, id);
        } else {
            throw new EntityNotFoundException("Tag with id= " + id + "not found!");
        }
    }

    @Override
    public List<TagEntityModel> findAll() {
        return jdbcTemplate.query(FIND_ALL_TAGS_QUERY, tagRowMapper);
    }

    @Override
    public long create(TagEntityModel entityModel) {
        if ((entityModel == null) || (entityModel.getName() == null)) {
            throw new IllegalArgumentException("Entity of tag or name of tag is null!");
        }
        KeyHolder newTagRecordKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement =
                    con.prepareStatement(CREATE_TAG_QUERY, new String[]{"id"});
            preparedStatement.setString(1, entityModel.getName());
            return preparedStatement;
        }, newTagRecordKeyHolder);
        long tagGeneratedId = Objects.requireNonNull(
                newTagRecordKeyHolder.getKey()).longValue();
        return tagGeneratedId;
    }

    @Override
    public TagEntityModel delete(long id) {
        if (isExist(id)) {
            TagEntityModel tagEntityModel = findById(id);
            jdbcTemplate.update(DELETE_TAG_QUERY, id);
            return tagEntityModel;
        } else {
            throw new EntityNotFoundException(
                    "Tag with id = " + id + " wasn't found!");
        }
    }

    private boolean isBound(long id) {
        return quantityOfBounds(id) > 0;
    }

    private Long quantityOfBounds(long id) {
        return jdbcTemplate.queryForObject(COUNT_TAG_BOUNDS, Long.class, id);
    }

    private Boolean isExist(long id) {
        return jdbcTemplate.queryForObject(
                GET_IS_TAG_EXIST_QUERY, Boolean.class, id);
    }
}
