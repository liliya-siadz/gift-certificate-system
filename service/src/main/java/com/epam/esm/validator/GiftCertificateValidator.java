package com.epam.esm.validator;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.exception.InvalidFieldValueException;
import com.epam.esm.validator.group.CreateChecks;
import com.epam.esm.validator.group.SpecialMessageChecks;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.util.Map;
import java.util.Set;

/**
 * Extends {@link Validator}, typed by {@link GiftCertificateClientModel},
 * validates client model of Gift Certificate .
 */
@Component
public class GiftCertificateValidator extends Validator<GiftCertificateClientModel> {
    @Override
    public void validateForUpdate(GiftCertificateClientModel certificate) {
        Map<String, Object> validationMap = getValidationMap(certificate);
        validateTags(getValidator(), validationMap, certificate);
        if (!validationMap.isEmpty()) {
            throw new InvalidFieldValueException(getResourceName(), validationMap);
        }
    }

    @Override
    public void validateForCreate(GiftCertificateClientModel certificate) {
        Map<String, Object> validationMap = getValidationMapForGroup(certificate, CreateChecks.class);
        validationMap.putAll(getValidationMap(certificate));
        validateTags(getValidator(), validationMap, certificate);
        if (!validationMap.isEmpty()) {
            throw new InvalidFieldValueException(getResourceName(), validationMap);
        }
    }

    @Override
    public String getResourceName() {
        return "Gift Certificate";
    }

    private void validateTags(javax.validation.Validator validator,
                              Map<String, Object> validationMap,
                              GiftCertificateClientModel certificate) {
        Set<ConstraintViolation<GiftCertificateClientModel>> tagsViolations =
                validator.validateProperty(certificate, "tags", SpecialMessageChecks.class);
        if (certificate.getTags() != null) {
            if (tagsViolations.size() > 0) {
                validationMap.put("tags", tagsViolations.iterator().next().getMessage());
            }
        }
    }
}

