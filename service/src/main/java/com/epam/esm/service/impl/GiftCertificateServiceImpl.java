package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.mapper.GiftCertificateModelMapper;
import com.epam.esm.model.GiftCertificateClientModel;
import com.epam.esm.model.TagClientModel;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link GiftCertificateService} interface,
 * for presenting access to service operations with Gift Certificate .
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    /**
     * Dao for repository operations with table 'gift_certificate' .
     */
    private final GiftCertificateDao dao;

    /**
     * Service for operations with Tag .
     */
    private final TagService tagService;

    /**
     * Mapper from Gift Certificate entity model
     * to Gift Certificate client model and otherwise .
     */
    private final GiftCertificateModelMapper modelMapper;

    /**
     * Validator for client model of Gift Certificate .
     */
    private final Validator<GiftCertificateClientModel> validator;

    /**
     * Constructs service with injected params .
     *
     * @param dao {@link #dao}
     * @param tagService {@link #tagService}
     * @param modelMapper {@link #modelMapper}
     * @param validator {@link #validator}
     */
    @Autowired
    public GiftCertificateServiceImpl(
            GiftCertificateDao dao,
            TagService tagService, GiftCertificateModelMapper modelMapper,
            @Qualifier("giftCertificateValidator") Validator<GiftCertificateClientModel> validator) {
        this.dao = dao;
        this.tagService = tagService;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    @Transactional
    public GiftCertificateClientModel create(GiftCertificateClientModel clientModel) {
        if (clientModel == null) {
            throw new IllegalArgumentException("Parameter 'clientModel' is null!");
        }
        validator.isValidForCreate(clientModel);
        long generatedId = dao.create(modelMapper.toEntity(clientModel));
        tagService.updateNewGiftCertificateTags(generatedId, clientModel.getTags());
        return findById(generatedId);
    }

    @Override
    public List<GiftCertificateClientModel> findAll() {
        return dao.findAll().stream()
                .map(modelMapper::toClientModel)
                .peek(giftCertificate -> giftCertificate.setTags(
                        tagService.findAllTagsBoundToGiftCertificate(giftCertificate.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateClientModel findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Gift certificate's id is null!");
        }
        if (dao.isExist(id)) {
            GiftCertificateClientModel clientModel =
                    modelMapper.toClientModel(dao.findById(id).get());
            clientModel.setTags(tagService.findAllTagsBoundToGiftCertificate(id));
            return clientModel;
        } else {
            throw new ResourceWithIdNotFoundException("Gift certificate", id);
        }
    }

    @Override
    public GiftCertificateClientModel delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Gift certificate's id is null!");
        }
        if (dao.isExist(id)) {
            GiftCertificateClientModel clientModel = findById(id);
            dao.delete(id);
            return clientModel;
        } else {
            throw new ResourceWithIdNotFoundException("Gift certificate", id);
        }
    }

    @Override
    @Transactional
    public GiftCertificateClientModel update(Long id, GiftCertificateClientModel clientModel) {
        if (id == null) {
            throw new IllegalArgumentException("Gift certificate's id is null!");
        }
        if (dao.isExist(id)) {
            validator.isValidForUpdate(clientModel);
            dao.update(id, modelMapper.toEntity(clientModel));
            List<TagClientModel> tagsForUpdate = clientModel.getTags();
            if (tagsForUpdate != null) {
                tagService.updateExistingGiftCertificateTags(id, tagsForUpdate);
            }
            clientModel = findById(id);
            return clientModel;
        } else {
            throw new ResourceWithIdNotFoundException("Gift certificate", id);
        }
    }

    @Override
    public List<GiftCertificateClientModel> search(
            String tagName, String name, String description, String sort) {
        return dao.search(tagName, name, description, sort).stream()
                .map(modelMapper::toClientModel)
                .peek(giftCertificate -> giftCertificate.setTags(
                        tagService.findAllTagsBoundToGiftCertificate(giftCertificate.getId())))
                .collect(Collectors.toList());
    }
}
