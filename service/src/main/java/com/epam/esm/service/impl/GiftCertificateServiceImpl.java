package com.epam.esm.service.impl;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.dao.Dao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.builder.sort.UnknownSortFieldException;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.exception.ResourceWithNameExistsException;
import com.epam.esm.exception.UnknownSortParamException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.preparator.GiftCertificatePreparator;
import com.epam.esm.preparator.Preparator;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.ResourceNames;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link GiftCertificateService} interface,
 * for presenting access to service operations with Gift Certificate .
 */
@Service
public class GiftCertificateServiceImpl
        extends AbstractService<GiftCertificateEntity, GiftCertificateClientModel>
        implements GiftCertificateService {

    /**
     * Dao class for repository operations .
     */
    @Autowired
    private Dao<GiftCertificateEntity> dao;

    /**
     * Mapper for mapping from entity to client model and otherwise .
     */
    @Autowired
    private Mapper<GiftCertificateEntity, GiftCertificateClientModel> mapper;

    /**
     * Service for operations with Tag .
     */
    @Autowired
    private final TagService tagService;

    /**
     * Preparator for update/create operations with Gift Certificate
     */
    @Autowired
    private Preparator<GiftCertificateClientModel> preparator;

    /**
     * Constructs <code>GiftCertificateServiceImpl</code> class
     * with dao, mapper, validator and tag service .
     *
     * @param dao        {@link #dao}
     * @param mapper     {@link #mapper}
     * @param tagService {@link #tagService}
     */
    public GiftCertificateServiceImpl(GiftCertificateDao dao, GiftCertificateMapper mapper,
                                      TagService tagService) {
        super(dao, mapper);
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
            giftCertificate = dao.create(mapper.toEntity(model));
        } catch (DataIntegrityViolationException exception) {
            throw new ResourceWithNameExistsException(ResourceNames.getResourceName(dao.getEntityClass()),
                    model.getName(), exception);
        }
        Long id = giftCertificate.getId();
        if (!tags.isEmpty()) {
            tagService.updateNewGiftCertificateTags(id, tags);
        }
        return findById(id);
    }

    @Override
    @Transactional
    public GiftCertificateClientModel update(Long id, GiftCertificateClientModel newModel) {
        if (newModel == null) {
            throw new IllegalArgumentException("Parameter 'newModel' is null.");
        }
        GiftCertificateClientModel model = findById(id);
        ((GiftCertificatePreparator) preparator).prepareForMerge(model, newModel);
        ((GiftCertificateDao) dao).update(mapper.toEntity(model));
        List<TagClientModel> tags = newModel.getTags();
        if (tags != null) {
            tagService.updateExistingGiftCertificateTags(id, tags);
        }
        return findById(id);
    }

    @Override
    public PageableClientModel<GiftCertificateClientModel> search(String tagName, String name, String description,
                                                                  String sortField, String sortDirection,
                                                                  Integer pageSize, Integer pageNumber) {
        try {
            return mapper.toClientModel(((GiftCertificateDao) dao)
                    .search(tagName, name, description, sortField, sortDirection,
                            pageSize, pageNumber));
        } catch (EnumConstantNotPresentException exception) {
            throw new UnknownSortParamException(exception.constantName());
        } catch (UnknownSortFieldException exception) {
            throw new UnknownSortParamException(exception.getSortFieldValue());
        }
    }
}
