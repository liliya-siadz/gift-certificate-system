package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.TagModelMapper;
import com.epam.esm.model.TagClientModel;
import com.epam.esm.model.TagEntityModel;
import com.epam.esm.service.TagService;
import com.epam.esm.service.change_predicate.BoundTagPredicate;
import com.epam.esm.service.change_predicate.CreateAndBoundTagPredicate;
import com.epam.esm.service.change_predicate.UnboundTagPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    private final TagModelMapper modelMapper;

    private final BoundTagPredicate boundTagPredicate;

    private final UnboundTagPredicate unboundTagPredicate;

    private final CreateAndBoundTagPredicate createAndBoundTagPredicate;

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagModelMapper modelMapper,
                          BoundTagPredicate boundTagPredicate,
                          UnboundTagPredicate unboundTagPredicate,
                          CreateAndBoundTagPredicate createAndBoundTagPredicate) {
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
        this.boundTagPredicate = boundTagPredicate;
        this.unboundTagPredicate = unboundTagPredicate;
        this.createAndBoundTagPredicate = createAndBoundTagPredicate;
    }

    @Override
    public TagClientModel findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of tag is null!");
        } else {
            return modelMapper.toClientModel(tagDao.findById(id)
                    .orElseThrow(EntityNotFoundException::new));
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
            throw new EntityNotFoundException("Tag with id is not found!");
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
        if ((clientModel == null) || (clientModel.getName() == null)) {
            throw new IllegalArgumentException("Tag's model or tag's name is null!");
        } else {
            long tagGeneratedId = tagDao.create(
                    modelMapper.toEntity(clientModel));
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
            if (boundTagPredicate.test(tag)) {
                boundTagToGiftCertificate(tagId, giftCertificateId);
            } else if (unboundTagPredicate.test(tag)) {
                unboundTagFromGiftCertificate(tagId, giftCertificateId);
            } else if (createAndBoundTagPredicate.test(tag)) {
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
            if (boundTagPredicate.test(tag)) {
                boundTagToGiftCertificate(tagId, giftCertificateId);
            } else if (createAndBoundTagPredicate.test(tag)) {
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
            throw new EntityNotFoundException("Tag with id=" + id + " is not found!");
        }
    }

    private void unboundTagFromGiftCertificate(Long id, Long giftCertificateId) {
        if (isExist(id)) {
            if (isTagBoundToGiftCertificate(id, giftCertificateId)) {
                tagDao.unboundTagFromGiftCertificate(id, giftCertificateId);
            }
        } else {
            throw new EntityNotFoundException("Tag with id=" + id + " is not found!");
        }
    }

    private boolean isTagBoundToGiftCertificate(Long id, Long giftCertificateId) {
        if ((id == null) || (giftCertificateId == null)) {
            throw new IllegalArgumentException("Tag id of gift certificate id is null!");
        }
        return tagDao.isTagBoundToGiftCertificate(id, giftCertificateId);
    }
}
