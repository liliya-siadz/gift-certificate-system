package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceWithNameExistsException;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.TagModel;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link TagService} interface,
 * for presenting access to service operations with Tag .
 */
@Service
public class TagServiceImpl extends AbstractService<Tag, TagModel> implements TagService {

    /**
     * Constructs <code>TagServiceImpl</code> class
     * with injected dao, mapper and validator .
     *
     * @param dao       {@link #dao}
     * @param mapper    {@link #mapper}
     * @param validator {@link #validator}
     */
    @Autowired
    public TagServiceImpl(TagDao dao, TagMapper mapper, Validator<TagModel> validator) {
        super(dao, mapper, validator);
    }

    @Override
    @Transactional
    public TagModel create(TagModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Parameter 'model' is null.");
        }
        model.setId(null);
        validator.validateForCreate(model);
        try {
            return mapper.toModel(dao.create(mapper.toEntity(model)));
        } catch (DataIntegrityViolationException exception) {
            throw new ResourceWithNameExistsException(
                    dao.retrieveEntityClass().getSimpleName(), model.getName(), exception);
        }
    }

    @Override
    public Set<TagModel> findAllTagsBoundToGiftCertificate(Long certificateId) {
        if (certificateId == null) {
            throw new IllegalArgumentException("Parameter 'certificateId' is null.");
        }
        TagDao temp = (TagDao) dao;
        return temp.findAllTagsBoundToGiftCertificate(certificateId)
                .stream()
                .map(mapper::toModel)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Set<TagModel> updateExistingGiftCertificateTags(Long certificateId, Set<TagModel> tags) {
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
                TagModel temp = create(tag);
                boundTagToGiftCertificate(temp.getId(), certificateId);
            }
        });
        return tags.stream().filter(tag -> tag.getName() != null).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Set<TagModel> updateNewGiftCertificateTags(Long certificateId, Set<TagModel> tags) {
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
                TagModel temp = create(tag);
                boundTagToGiftCertificate(temp.getId(), certificateId);
            }
        });
        return tags.stream().filter(tag -> tag.getName() != null).collect(Collectors.toSet());
    }

    private void boundTagToGiftCertificate(Long id, Long certificateId) {
        if (!isExist(id)) {
            throw new ResourceWithIdNotFoundException(Tag.class.getSimpleName(), id);
        }
        if (!isTagBoundToGiftCertificate(id, certificateId)) {
            TagDao temp = (TagDao) dao;
            temp.boundTagToGiftCertificate(id, certificateId);
        }
    }

    private void unboundTagFromGiftCertificate(Long id, Long certificateId) {
        if (!isExist(id)) {
            throw new ResourceWithIdNotFoundException(Tag.class.getSimpleName(), id);
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
