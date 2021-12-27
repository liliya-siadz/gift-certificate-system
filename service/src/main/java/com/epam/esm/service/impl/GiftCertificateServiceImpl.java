package com.epam.esm.service.impl;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.service.impl.builder.PageRequestBuilder;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.exception.ResourceWithNameExistsException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.preparator.GiftCertificatePreparator;
import com.epam.esm.preparator.Preparator;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.ResourceNames;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.service.ResourceNames.getResourceName;

/**
 * Implementation of {@link GiftCertificateService} interface,
 * for presenting access to service operations with Gift Certificate .
 */
@Service
public class GiftCertificateServiceImpl
        extends AbstractService<GiftCertificateEntity, GiftCertificateClientModel> implements GiftCertificateService {

    /**
     * Service for operations with Tag .
     */
    @Autowired
    private TagService tagService;

    /**
     * Mapper for mapping Tag entities to client models and otherwise .
     */
    @Autowired
    private TagMapper tagMapper;

    /**
     * Preparator for update/create operations with Gift Certificate
     */
    @Autowired
    private Preparator<GiftCertificateClientModel> preparator;

    /**
     * Page request's builder .
     */
    @Autowired
    private PageRequestBuilder pageRequestBuilder;

    /**
     * Constructs <code>GiftCertificateServiceImpl</code> class
     * with dao, mapper, validator and Tag service .
     *
     * @param repository {@link #repository}
     * @param mapper     {@link #mapper}
     * @param tagService {@link #tagService}
     */
    public GiftCertificateServiceImpl(GiftCertificateRepository repository,
                                      GiftCertificateMapper mapper, TagService tagService) {
        super(repository, mapper);
        this.tagService = tagService;
    }

    @Override
    @Transactional
    public GiftCertificateClientModel create(GiftCertificateClientModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Parameter 'model' is null.");
        }
        preparator.prepareForCreate(model);
        List<TagClientModel> tags = new ArrayList<>(model.getTags());
        model.getTags().clear();
        GiftCertificateEntity giftCertificate;
        try {
            giftCertificate = repository.save(mapper.toEntity(model));
        } catch (DataIntegrityViolationException exception) {
            throw new ResourceWithNameExistsException(
                    ResourceNames.getResourceName(repository.getEntityClass()), model.getName(), exception);
        }
        Long id = giftCertificate.getId();
        if (!tags.isEmpty()) {
            updateNewGiftCertificateTags(id, tags);
        }
        return findById(id);
    }

    @Override
    @Transactional
    public GiftCertificateClientModel update(Long id, GiftCertificateClientModel newModel) {
        if ((newModel == null) || (id == null)) {
            throw new IllegalArgumentException("Parameter 'newModel' or 'id' is null.");
        }
        GiftCertificateClientModel model = findById(id);
        ((GiftCertificatePreparator) preparator).prepareForMerge(model, newModel);
        repository.save(mapper.toEntity(model));
        List<TagClientModel> tags = newModel.getTags();
        if (tags != null) {
            updateExistingGiftCertificateTags(id, tags);
        }
        return findById(id);
    }

    @Override
    public GiftCertificateClientModel updatePrice(Long id, BigDecimal price) {
        if ((id == null) || (price == null)) {
            throw new IllegalArgumentException("Parameter 'id' or 'price' is null!");
        }
        GiftCertificateEntity model = repository.findById(id).orElseThrow(() -> new ResourceWithIdNotFoundException
                (getResourceName(getClass()), id));
        model.setPrice(price);
        repository.save(model);
        return findById(id);
    }

    @Override
    public PageableClientModel<GiftCertificateClientModel> search(List<String> tagNames, String name, String description,
                                                                  String sortField, String sortDirection,
                                                                  Integer pageSize, Integer pageNumber) {
        if ((pageSize == null) || (pageNumber == null)) {
            throw new IllegalArgumentException("Parameter 'pageSize' or 'pageNumber' is null.");
        }
        PageRequest pageRequest = pageRequestBuilder.buildCertificateSearchRequest(tagNames, name,
                description, sortField, sortDirection, pageSize, pageNumber);
        return mapper.toClientModel(((GiftCertificateRepository) this.repository)
                .findByConditions(tagNames, tagNames == null ? 0 : (long) tagNames.size(), name, description, pageRequest));
    }

    private void updateExistingGiftCertificateTags(Long certificateId, List<TagClientModel> tags) {
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
                TagClientModel temp = tagService.create(tag);
                boundTagToGiftCertificate(temp.getId(), certificateId);
            }
        });
        tags.stream().filter(tag -> tag.getName() != null).collect(Collectors.toList());
    }

    private void updateNewGiftCertificateTags(Long certificateId, List<TagClientModel> tags) {
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
                TagClientModel temp = tagService.create(tag);
                boundTagToGiftCertificate(temp.getId(), certificateId);
            }
        });
        tags.stream().filter(tag -> tag.getName() != null).collect(Collectors.toList());
    }

    private void boundTagToGiftCertificate(Long tagId, Long certificateId) {
        if (!tagService.isExist(tagId)) {
            throw new ResourceWithIdNotFoundException(ResourceNames.getResourceName(TagEntity.class), tagId);
        }
        if (!isTagBoundToGiftCertificate(tagId, certificateId)) {
            GiftCertificateEntity model = repository.findById(certificateId).get();
            model.getTags().add(tagMapper.toEntity(tagService.findById(tagId)));
            repository.save(model);
        }
    }

    private void unboundTagFromGiftCertificate(Long tagId, Long certificateId) {
        if (!tagService.isExist(tagId)) {
            throw new ResourceWithIdNotFoundException(ResourceNames.getResourceName(TagEntity.class), tagId);
        }
        if (isTagBoundToGiftCertificate(tagId, certificateId)) {
            GiftCertificateEntity model = repository.findById(certificateId).get();
            model.getTags().remove(tagMapper.toEntity(tagService.findById(tagId)));
            repository.save(model);
        }
    }

    private boolean isTagBoundToGiftCertificate(Long tagId, Long certificateId) {
        if ((tagId == null) || (certificateId == null)) {
            throw new IllegalArgumentException("Parameter 'id' or 'certificateId' is null.");
        }
        return ((GiftCertificateRepository) repository).findByIdAndTagsId(certificateId, tagId).isPresent();
    }
}
