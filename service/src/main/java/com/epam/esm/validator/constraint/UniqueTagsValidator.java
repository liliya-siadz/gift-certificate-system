package com.epam.esm.validator.constraint;

import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.exception.ResourceContainsDuplicateValuesException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Validates constraint {@link UniqueTags}
 * for list of objects type of {@link com.epam.esm.clientmodel.TagClientModel} .
 */
public class UniqueTagsValidator implements ConstraintValidator<UniqueTags, List<TagClientModel>> {
    @Override
    public boolean isValid(List<TagClientModel> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return validateTagsDuplicates(value);
    }

    private boolean validateTagsDuplicates(List<TagClientModel> tags) {
        List<Long> idsDuplicates = getIdsDuplicates(tags);
        List<String> namesDuplicates = getNamesDuplicates(tags);
        boolean isIdsDuplicated = !idsDuplicates.isEmpty();
        boolean isNamesDuplicated = !namesDuplicates.isEmpty();
        if (isIdsDuplicated || isNamesDuplicated) {
            StringBuilder tagsDuplicates = new StringBuilder("{")
                    .append(isIdsDuplicated ? "ids: " + idsDuplicates : "")
                    .append(isNamesDuplicated ? "names: " + namesDuplicates : "")
                    .append("}");
            throw new ResourceContainsDuplicateValuesException("Tag", tagsDuplicates.toString());
        } else {
            return true;
        }
    }

    private List<String> getNamesDuplicates(List<TagClientModel> tags) {
        return tags.stream().map(TagClientModel::getName)
                .filter(Objects::nonNull).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().filter(nameUsedTimes -> nameUsedTimes.getValue() > 1).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Long> getIdsDuplicates(List<TagClientModel> tags) {
        return tags.stream().map(TagClientModel::getId)
                .filter(Objects::nonNull).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().filter(idUsedTimes -> idUsedTimes.getValue() > 1).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
