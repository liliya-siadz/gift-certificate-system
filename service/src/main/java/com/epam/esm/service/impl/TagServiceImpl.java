package com.epam.esm.service.impl;

import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.dao.Dao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ResourceWithNameExistsException;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.preparator.Preparator;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.ResourceNames;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of {@link TagService} interface,
 * for presenting access to service operations with Tag .
 */
@Service
public class TagServiceImpl extends AbstractService<TagEntity, TagClientModel> implements TagService {

    /**
     * Dao class for repository operations .
     */
    @Autowired
    private Dao<TagEntity> dao;

    /**
     * Mapper for mapping from entity to client model and otherwise .
     */
    @Autowired
    private Mapper<TagEntity, TagClientModel> mapper;

    /**
     * Preparator for update/create operations with Tag
     */
    @Autowired
    private Preparator<TagClientModel> preparator;

    /**
     * Constructs <code>TagServiceImpl</code> class
     * with dao, mapper and validator .
     *
     * @param dao    {@link #dao}
     * @param mapper {@link #mapper}
     */
    public TagServiceImpl(TagDao dao, TagMapper mapper) {
        super(dao, mapper);
    }

    @Override
    @Transactional
    public TagClientModel create(TagClientModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Parameter 'model' is null.");
        }
        preparator.prepareForCreate(model);
        try {
            return mapper.toClientModel(dao.create(mapper.toEntity(model)));
        } catch (DataIntegrityViolationException exception) {
            throw new ResourceWithNameExistsException(ResourceNames.getResourceName(dao.getEntityClass()),
                    model.getName(), exception);
        }
    }

    @Override
    public List<TagClientModel> findAllTagsBoundToGiftCertificate(Long certificateId) {
        if (certificateId == null) {
            throw new IllegalArgumentException("Parameter 'certificateId' is null.");
        }
        TagDao temp = (TagDao) dao;
        return temp.findAllTagsBoundToGiftCertificate(certificateId)
                .stream()
                .map(mapper::toClientModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TagClientModel> updateExistingGiftCertificateTags(Long certificateId, List<TagClientModel> tags) {
        if ((certificateId == null) || (tags == null) || (tags.stream().anyMatch(Objects::isNull))) {
            throw new IllegalArgumentException(
                    "Parameter 'certificateId' or 'tags' is null, or list 'tags' contains null value.");
        }
        tags.forEach(tag -> {
            Long tagId = tag.getId();
            String tagName = tag.getName();
            if ((tagId != null) && (tagName != null)) {
                boundTagToGiftCertificate(tagId, certificateId);
            } else if (tagId != null) {
                unboundTagFromGiftCertificate(tagId, certificateId);
            } else if (tagName != null) {
                TagClientModel temp = create(tag);
                boundTagToGiftCertificate(temp.getId(), certificateId);
            }
        });
        return tags.stream().filter(tag -> tag.getName() != null).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TagClientModel> updateNewGiftCertificateTags(Long certificateId, List<TagClientModel> tags) {
        if ((certificateId == null) || (tags == null) || (tags.stream().anyMatch(Objects::isNull))) {
            throw new IllegalArgumentException(
                    "Parameter 'certificateId' or 'tags' is null, or list 'tags' contains null value.");
        }
        tags.forEach(tag -> {
            Long tagId = tag.getId();
            String tagName = tag.getName();
            if ((tagId != null) && (tagName != null)) {
                boundTagToGiftCertificate(tagId, certificateId);
            } else if (tagName != null) {
                TagClientModel temp = create(tag);
                boundTagToGiftCertificate(temp.getId(), certificateId);
            }
        });
        return tags.stream().filter(tag -> tag.getName() != null).collect(Collectors.toList());
    }

    private void boundTagToGiftCertificate(Long id, Long certificateId) {
        if (!isExist(id)) {
            throw new ResourceWithIdNotFoundException(TagEntity.class.getSimpleName(), id);
        }
        if (!isTagBoundToGiftCertificate(id, certificateId)) {
            TagDao temp = (TagDao) dao;
            temp.boundTagToGiftCertificate(id, certificateId);
        }
    }

    private void unboundTagFromGiftCertificate(Long id, Long certificateId) {
        if (!isExist(id)) {
            throw new ResourceWithIdNotFoundException(TagEntity.class.getSimpleName(), id);
        }
        if (isTagBoundToGiftCertificate(id, certificateId)) {
            TagDao temp = (TagDao) dao;
            temp.unboundTagFromGiftCertificate(id, certificateId);
        }
    }

    private boolean isTagBoundToGiftCertificate(Long id, Long certificateId) {
        if ((id == null) || (certificateId == null)) {
            throw new IllegalArgumentException("Parameter 'id' or 'certificateId' is null.");
        }
        TagDao temp = (TagDao) dao;
        return temp.isTagBoundToGiftCertificate(id, certificateId);
    }
}
