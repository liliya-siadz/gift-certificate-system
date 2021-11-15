package com.epam.esm.preparator;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Implementation of {@link Preparator} interface,
 * for preparing Gift Certificate client models for service operations .
 */
@Component
public class GiftCertificatePreparator extends Preparator<GiftCertificateClientModel> {

    /**
     * Validator for validating client models .
     */
    @Autowired
    private Validator<GiftCertificateClientModel> validator;

    /**
     * Constructs <code>GiftCertificatePreparator</code>
     * with passed model validator
     *
     * @param validator {@link #validator}
     */
    public GiftCertificatePreparator(Validator<GiftCertificateClientModel> validator) {
        super(validator);
    }

    @Override
    public void prepareForMerge(GiftCertificateClientModel currentModelState,
                                GiftCertificateClientModel newModelState) {
        validator.validateForUpdate(newModelState);
        String newName = newModelState.getName();
        String newDescription = newModelState.getDescription();
        String newCreateDate = newModelState.getCreateDate();
        Integer newDuration = newModelState.getDuration();
        BigDecimal newPrice = newModelState.getPrice();
        if (newName != null) {
            currentModelState.setName(newName);
        }
        if (newDescription != null) {
            currentModelState.setDescription(newDescription);
        }
        if (newCreateDate != null) {
            currentModelState.setCreateDate(newCreateDate);
        }
        if (newDuration != null) {
            currentModelState.setDuration(newDuration);
        }
        if (newPrice != null) {
            currentModelState.setPrice(newPrice);
        }
    }
}
