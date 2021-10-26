package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.mapper.TagModelMapper;
import com.epam.esm.model.TagClientModel;
import com.epam.esm.model.TagEntityModel;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagModelMapper modelMapper;
    private final Validator<TagClientModel> validator;

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagModelMapper modelMapper,
                          @Qualifier("tagValidator") Validator<TagClientModel> validator) {
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public TagClientModel findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of tag is null!");
        } else {
            Optional<TagEntityModel> entity = tagDao.findById(id);
            if (entity.isPresent()) {
                return modelMapper.toClientModel(entity.get());
            } else {
                throw new ResourceWithIdNotFoundException("Tag", id);
            }
        }
    }

    @Override
    public TagClientModel delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of tag is null!");
        }
        if (tagDao.isExist(id)) {
            TagEntityModel tagEntity = tagDao.findById(id).get();
            tagDao.delete(id);
            return modelMapper.toClientModel(tagEntity);
        } else {
            throw new ResourceWithIdNotFoundException("Tag", id);
        }
    }

    @Override
    public List<TagClientModel> findAll() {
        return tagDao.findAll()
                .stream()
                .map(modelMapper::toClientModel).
                collect(Collectors.toList());
    }

    @Override
    public TagClientModel create(TagClientModel clientModel) {
        if (clientModel == null) {
            throw new IllegalArgumentException("Tag's model or tag's name is null!");
        } else {
            validator.isValidForCreate(clientModel);
            long tagGeneratedId = tagDao.create(modelMapper.toEntity(clientModel));
            clientModel.setId(tagGeneratedId);
            return clientModel;
        }
    }

    @Override
    public List<TagClientModel> findAllTagsBoundToGiftCertificate(Long giftCertificateId) {
        if (giftCertificateId == null) {
            throw new IllegalArgumentException("Gift certificate is null!");
        }
        return tagDao.findAllTagsBoundToGiftCertificate(giftCertificateId)
                .stream()
                .map(modelMapper::toClientModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<TagClientModel> updateExistingGiftCertificateTags(
            Long giftCertificateId, List<TagClientModel> tags) {
        if ((giftCertificateId == null) || (tags == null)
                || (tags.stream().anyMatch(Objects::isNull))) {
            throw new IllegalArgumentException("Gift certificate's id or tags list is null (has null values)!");
        }
        tags.forEach(tag -> {
            Long tagId = tag.getId();
            String tagName = tag.getName();
            if ((tagId != null) && (tagName != null)) {
                validator.isValidForUpdate(tag);
                boundTagToGiftCertificate(tagId, giftCertificateId);
            } else if (tagId != null) {
                validator.isValidForUpdate(tag);
                unboundTagFromGiftCertificate(tagId, giftCertificateId);
            } else if (tagName != null) {
                validator.isValidForCreate(tag);
                create(tag);
                boundTagToGiftCertificate(tag.getId(), giftCertificateId);
            }
        });
        return tags.stream()
                .filter(tag -> tag.getName() != null)
                .collect(Collectors.toList());
    }

    @Override
    public List<TagClientModel> updateNewGiftCertificateTags(
            Long giftCertificateId, List<TagClientModel> tags) {
        if ((giftCertificateId == null) || (tags == null)
                || (tags.stream().anyMatch(Objects::isNull))) {
            throw new IllegalArgumentException("Gift certificate's id or tags list is null (has null values)!");
        }
        tags.forEach(tag -> {
            Long tagId = tag.getId();
            String tagName = tag.getName();
            if ((tagId != null) && (tagName != null)) {
                validator.isValidForUpdate(tag);
                boundTagToGiftCertificate(tagId, giftCertificateId);
            } else if (tagName != null) {
                validator.isValidForCreate(tag);
                create(tag);
                boundTagToGiftCertificate(tag.getId(), giftCertificateId);
            }
        });
        return tags.stream()
                .filter(tag -> tag.getName() != null)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isExist(Long id) {
        return (id != null) && (tagDao.isExist(id));
    }

    private void boundTagToGiftCertificate(Long id, Long giftCertificateId) {
        if (isExist(id)) {
            if (!isTagBoundToGiftCertificate(id, giftCertificateId)) {
                tagDao.boundTagToGiftCertificate(id, giftCertificateId);
            }
        } else {
            throw new ResourceWithIdNotFoundException("Tag", id);
        }
    }

    private void unboundTagFromGiftCertificate(Long id, Long giftCertificateId) {
        if (isExist(id)) {
            if (isTagBoundToGiftCertificate(id, giftCertificateId)) {
                tagDao.unboundTagFromGiftCertificate(id, giftCertificateId);
            }
        } else {
            throw new ResourceWithIdNotFoundException("Tag", id);
        }
    }

    private boolean isTagBoundToGiftCertificate(Long id, Long giftCertificateId) {
        if ((id == null) || (giftCertificateId == null)) {
            throw new IllegalArgumentException("Tag id of gift certificate id is null!");
        }
        return tagDao.isTagBoundToGiftCertificate(id, giftCertificateId);
    }
}
