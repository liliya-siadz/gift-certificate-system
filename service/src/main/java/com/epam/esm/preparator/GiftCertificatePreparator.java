package com.epam.esm.preparator;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Implementation of {@link Preparator} interface,
 * for preparing Gift Certificate client models for service operations .
 */
@Component
public class GiftCertificatePreparator extends Preparator<GiftCertificateClientModel> {

    /**
     * Prepares Gift Certificate client model for merge operation,
     * copy non-null values of fields to current client model .
     *
     * @param currentModelState current client model
     * @param newModelState     new client model,
     *                          which non-null fields will replace same fields of current model
     */
    public void prepareForMerge(GiftCertificateClientModel currentModelState,
                                GiftCertificateClientModel newModelState) {
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
