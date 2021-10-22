package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.GiftCertificateModelMapper;
import com.epam.esm.model.GiftCertificateClientModel;
import com.epam.esm.model.TagClientModel;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao dao;

    private final TagService tagService;

    private final GiftCertificateModelMapper modelMapper;

    @Autowired
    public GiftCertificateServiceImpl(
            GiftCertificateDao dao,
            TagService tagService, GiftCertificateModelMapper modelMapper) {
        this.dao = dao;
        this.tagService = tagService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public GiftCertificateClientModel create(GiftCertificateClientModel clientModel) {
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
            throw new EntityNotFoundException("Gift certificate with id= " + id + " is not found!");
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
            throw new EntityNotFoundException("Gift certificate with id= " + id + " is not found!");
        }
    }

    @Override
    @Transactional
    public GiftCertificateClientModel update(Long id, GiftCertificateClientModel clientModel) {
        if (id == null) {
            throw new IllegalArgumentException("Gift certificate's id is null!");
        }
        if (dao.isExist(id)) {
            dao.update(id, modelMapper.toEntity(clientModel));
            List<TagClientModel> tagsForUpdate = clientModel.getTags();
            if (tagsForUpdate != null) {
                tagService.updateExistingGiftCertificateTags(id, tagsForUpdate);
            }
            clientModel = findById(id);
            return clientModel;
        } else {
            throw new EntityNotFoundException("Gift certificate with id= " + id + " is not found!");
        }
    }

    @Override
    public boolean isExist(Long id) {
        return ((id != null) && (dao.isExist(id)));
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