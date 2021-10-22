package com.epam.esm.service;

import com.epam.esm.model.TagClientModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    TagClientModel findById(Long id);

    TagClientModel delete(Long id);

    List<TagClientModel> findAll();

    TagClientModel create(TagClientModel tagClientModel);

    List<TagClientModel> findAllTagsBoundToGiftCertificate(Long giftCertificateId);

    List<TagClientModel> updateExistingGiftCertificateTags(
            Long giftCertificateId, List<TagClientModel> tags);

    List<TagClientModel> updateNewGiftCertificateTags(Long giftCertificateId, List<TagClientModel> tags);

    boolean isExist(Long id);
}
