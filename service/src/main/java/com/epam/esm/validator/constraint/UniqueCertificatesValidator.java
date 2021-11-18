package com.epam.esm.validator.constraint;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.exception.ResourceContainsDuplicateValuesException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UniqueCertificatesValidator
        implements ConstraintValidator<UniqueCertificates, List<GiftCertificateClientModel>> {
    @Override
    public boolean isValid(List<GiftCertificateClientModel> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return validateCertificatesDuplicates(value);
    }

    private boolean validateCertificatesDuplicates(List<GiftCertificateClientModel> certificates) {
        List<Long> idsDuplicates = getIdsDuplicates(certificates);
        List<String> namesDuplicates = getNamesDuplicates(certificates);
        boolean isIdsDuplicated = !idsDuplicates.isEmpty();
        boolean isNamesDuplicated = !namesDuplicates.isEmpty();
        if (isIdsDuplicated || isNamesDuplicated) {
            StringBuilder certificatesDuplicates = new StringBuilder("{")
                    .append(isIdsDuplicated ? "ids: " + idsDuplicates : "")
                    .append(isNamesDuplicated ? "names: " + namesDuplicates : "")
                    .append("}");
            throw new ResourceContainsDuplicateValuesException("Gift Certificate", certificatesDuplicates.toString());
        } else {
            return true;
        }
    }

    private List<String> getNamesDuplicates(List<GiftCertificateClientModel> certificates) {
        return certificates.stream().map(GiftCertificateClientModel::getName)
                .filter(Objects::nonNull).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().filter(nameUsedTimes -> nameUsedTimes.getValue() > 1).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Long> getIdsDuplicates(List<GiftCertificateClientModel> certificates) {
        return certificates.stream().map(GiftCertificateClientModel::getId)
                .filter(Objects::nonNull).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().filter(idUsedTimes -> idUsedTimes.getValue() > 1).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
