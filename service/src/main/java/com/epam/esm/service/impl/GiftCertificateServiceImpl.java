package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.sort.UnknownSortFieldException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ResourceWithNameExistsException;
import com.epam.esm.exception.UnknownSortParamException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificateModel;
import com.epam.esm.model.TagModel;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link GiftCertificateService} interface,
 * for presenting access to service operations with Gift Certificate .
 */
@Service
public class GiftCertificateServiceImpl
        extends AbstractService<GiftCertificate, GiftCertificateModel>
        implements GiftCertificateService {

    /**
     * Service for operations with Tag .
     */
    private final TagService tagService;

    /**
     * Constructs <code>GiftCertificateServiceImpl</code> class
     * with injected dao, mapper, validator and tag service .
     *
     * @param dao        {@link #dao}
     * @param mapper     {@link #mapper}
     * @param validator  {@link #validator}
     * @param tagService {@link #tagService}
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao dao, GiftCertificateMapper mapper,
                                      Validator<GiftCertificateModel> validator,
                                      TagService tagService) {
        super(dao, mapper, validator);
        this.tagService = tagService;
    }

    @Override
    @Transactional
    public GiftCertificateModel update(Long id, GiftCertificateModel newModel) {
        if (newModel == null) {
            throw new IllegalArgumentException("Parameter 'newModel' is null.");
        }
        validator.validateForUpdate(newModel);
        newModel.setId(null);
        GiftCertificateModel model = findById(id);
        copyNonNullFields(model, newModel);
        ((GiftCertificateDao) dao).update(mapper.toEntity(model));
        Set<TagModel> tags = newModel.getTags();
        if (tags != null) {
            tagService.updateExistingGiftCertificateTags(id, tags);
        }
        return findById(id);
    }

    @Override
    public List<GiftCertificateModel> search(String tagName, String name, String description,
                                             String sortField, String sortDirection) {
        try {
            GiftCertificateDao temp = (GiftCertificateDao) dao;
            return temp.search(tagName, name, description, sortField, sortDirection).stream()
                    .map(mapper::toModel)
                    .collect(Collectors.toList());
        } catch (EnumConstantNotPresentException exception) {
            throw new UnknownSortParamException(exception.constantName());
        } catch (UnknownSortFieldException exception) {
            throw new UnknownSortParamException(exception.getSortFieldValue());
        }
    }

    @Override
    @Transactional
    public GiftCertificateModel create(GiftCertificateModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Parameter 'model' is null.");
        }
        model.setId(null);
        validator.validateForCreate(model);
        try {
            Set<TagModel> tags = new HashSet<>(model.getTags());
            model.getTags().clear();
            GiftCertificate giftCertificate = dao.create(mapper.toEntity(model));
            Long id = giftCertificate.getId();
            if (!tags.isEmpty()) {
                tagService.updateNewGiftCertificateTags(id, tags);
            }
            return findById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new ResourceWithNameExistsException(
                    dao.retrieveEntityClass().getSimpleName(), model.getName(), exception);
        }
    }

    private void copyNonNullFields(GiftCertificateModel model, GiftCertificateModel newModel) {
        if (!model.equals(newModel)) {
            String newName = newModel.getName();
            String newDescription = newModel.getDescription();
            String newCreateDate = newModel.getCreateDate();
            Integer newDuration = newModel.getDuration();
            BigDecimal newPrice = newModel.getPrice();
            if (newName != null) {
                model.setName(newName);
            }
            if (newDescription != null) {
                model.setDescription(newDescription);
            }
            if (newCreateDate != null) {
                model.setCreateDate(newCreateDate);
            }
            if (newDuration != null) {
                model.setDuration(newDuration);
            }
            if (newPrice != null) {
                model.setPrice(newPrice);
            }
        }
    }
}
