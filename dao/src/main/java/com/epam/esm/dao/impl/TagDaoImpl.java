package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.TagEntityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    private static final String GET_IS_TAG_EXIST_QUERY =
            "SELECT EXISTS(SELECT 1 FROM tag WHERE id = ?)";
    private static final String GET_IS_TAG_BOUND_TO_GIFT_CERTIFICATE_QUERY =
            "SELECT EXISTS(SELECT 1 FROM gift_certificates_tags"
                    + " WHERE  tag_id = ? AND  gift_certificate_id = ?);";
    private static final String BOUND_TAG_TO_GIFT_CERTIFICATE_QUERY =
            "INSERT INTO gift_certificates_tags (tag_id, gift_certificate_id)"
                    + " VALUES (?, ?)";
    private static final String UNBOUND_TAG_FROM_GIFT_CERTIFICATE_QUERY =
            "DELETE FROM gift_certificates_tags WHERE tag_id = ? AND  gift_certificate_id = ?";
    private static final String FIND_ALL_TAGS_BOUND_WITH_GIFT_CERTIFICATE_QUERY =
            "SELECT tag.id, tag.name"
                    + " FROM gift_certificates_tags"
                    + " JOIN tag"
                    + " ON gift_certificates_tags.tag_id = tag.id"
                    + " WHERE gift_certificates_tags.gift_certificate_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper rowMapper;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate,
                      TagRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public Optional<TagEntityModel> findById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    FIND_TAG_BY_ID_QUERY, rowMapper, id));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<TagEntityModel> findAll() {
        return jdbcTemplate.query(FIND_ALL_TAGS_QUERY, rowMapper);
    }

    @Override
    public List<TagEntityModel> findAllTagsBoundToGiftCertificate(long certificateId) {
        return jdbcTemplate.query(FIND_ALL_TAGS_BOUND_WITH_GIFT_CERTIFICATE_QUERY,
                preparedStatement -> preparedStatement.setLong(1, certificateId), rowMapper);
    }

    @Override
    public long create(TagEntityModel entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Tag entity is null!");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement =
                    con.prepareStatement(CREATE_TAG_QUERY, new String[]{"id"});
            preparedStatement.setString(1, entity.getName());
            return preparedStatement;
        }, keyHolder);
        long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return generatedId;
    }

    @Override
    public boolean delete(long id) {
        int quantityOfRowsAffected = jdbcTemplate.update(DELETE_TAG_QUERY, id);
        return quantityOfRowsAffected >= 1;
    }

    @Override
    public boolean isExist(long id) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                GET_IS_TAG_EXIST_QUERY, Boolean.class, id));
    }

    @Override
    public void boundTagToGiftCertificate(long id, long giftCertificateId) {
        int quantityOfRowsAffected = jdbcTemplate.update(BOUND_TAG_TO_GIFT_CERTIFICATE_QUERY,
                new Object[]{id, giftCertificateId}, new int[]{Types.INTEGER, Types.INTEGER});
    }

    @Override
    public void unboundTagFromGiftCertificate(long id, long giftCertificateId) {
        int quantityOfRowsAffected = jdbcTemplate.update(UNBOUND_TAG_FROM_GIFT_CERTIFICATE_QUERY,
                new Object[]{id, giftCertificateId}, new int[]{Types.INTEGER, Types.INTEGER});
    }

    @Override
    public boolean isTagBoundToGiftCertificate(long id, long giftCertificateId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                GET_IS_TAG_BOUND_TO_GIFT_CERTIFICATE_QUERY, Boolean.class, id, giftCertificateId));
    }
}
