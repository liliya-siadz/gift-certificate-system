package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.builder.FieldDescription;
import com.epam.esm.dao.builder.GiftCertificateQueryBuilder;
import com.epam.esm.mapper.GiftCertificateRowMapper;
import com.epam.esm.model.GiftCertificateEntityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.dao.builder.GiftCertificateQueryBuilder.EMPTY_STRING;
import static com.epam.esm.dao.builder.GiftCertificateQueryBuilder.buildUpdateQuery;
import static com.epam.esm.dao.builder.GiftCertificateQueryBuilder.extractUpdateParams;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String FIND_ALL_GIFT_CERTIFICATES_QUERY =
            "SELECT id, name, description, price, duration, create_date, last_update_date "
                    + "FROM gift_certificate";
    private static final String FIND_GIFT_CERTIFICATE_BY_ID_QUERY =
            "SELECT id, name, description, price, duration, create_date, last_update_date "
                    + "FROM gift_certificate WHERE id = ?";
    private static final String CREATE_GIFT_CERTIFICATE_QUERY =
            "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)"
                    + "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_GIFT_CERTIFICATE_QUERY =
            "DELETE FROM gift_certificate WHERE id = ?";
    private static final String GET_IS_GIFT_CERTIFICATE_EXIST_QUERY =
            "SELECT EXISTS(SELECT 1 FROM gift_certificate WHERE id = ?)";
    private JdbcTemplate jdbcTemplate;
    private GiftCertificateRowMapper rowMapper;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public long create(GiftCertificateEntityModel entity) {
        if ((entity == null)) {
            throw new IllegalArgumentException("Entity of tag or name of tag is null!");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    CREATE_GIFT_CERTIFICATE_QUERY, new String[]{"id"});
            return prepareStatementForCreate(preparedStatement, entity);
        }, keyHolder);
        long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return generatedId;
    }

    @Override
    public List<GiftCertificateEntityModel> findAll() {
        return jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATES_QUERY, rowMapper);
    }

    @Override
    public Optional<GiftCertificateEntityModel> findById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    FIND_GIFT_CERTIFICATE_BY_ID_QUERY, rowMapper, id));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(long id) {
        int quantityOfRowsAffected = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_QUERY, id);
        return quantityOfRowsAffected >= 1;
    }

    @Override
    public GiftCertificateEntityModel update(long id, GiftCertificateEntityModel entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Gift certificate entity is null!");
        }
        String updateQuery = buildUpdateQuery(id, entity);
        if (!updateQuery.equals(EMPTY_STRING)) {
            entity.setLastUpdateDate(LocalDateTime.now());
            Map<String, FieldDescription> paramsForUpdate = extractUpdateParams(entity);
            Object[] args = paramsForUpdate.values()
                    .stream()
                    .map(FieldDescription::getValue)
                    .toArray();
            int[] argsTypes = paramsForUpdate.values()
                    .stream()
                    .mapToInt(FieldDescription::getSqlTypeCode)
                    .toArray();
            jdbcTemplate.update(updateQuery, args, argsTypes);
        }
        return findById(id).get();
    }

    @Override
    public boolean isExist(long id) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                GET_IS_GIFT_CERTIFICATE_EXIST_QUERY, Boolean.class, id));
    }

    @Override
    public List<GiftCertificateEntityModel> search(
            String tagName, String name, String description, String sort) {
        String searchQuery = GiftCertificateQueryBuilder.buildSearchQuery(
                tagName, name, description, sort);
        return jdbcTemplate.query(searchQuery, rowMapper);
    }

    private PreparedStatement prepareStatementForCreate(
            PreparedStatement preparedStatement, GiftCertificateEntityModel entity)
            throws SQLException {
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setString(2, entity.getDescription());
        preparedStatement.setBigDecimal(3, entity.getPrice());
        preparedStatement.setInt(4, entity.getDuration());
        LocalDateTime createDate = entity.getCreateDate();
        if (entity.getCreateDate() == null) {
            LocalDateTime now = LocalDateTime.now();
            now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            createDate = now;
        }
        Timestamp createDateTimestamp = Timestamp.valueOf(createDate);
        preparedStatement.setTimestamp(5, createDateTimestamp);
        preparedStatement.setTimestamp(6, createDateTimestamp);
        return preparedStatement;
    }
}
