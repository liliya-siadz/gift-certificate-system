package com.epam.esm.validator;

import com.epam.esm.exception.InvalidFieldValueException;
import com.epam.esm.model.GiftCertificateModel;
import com.epam.esm.model.TagModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Extends {@link Validator}, typed by {@link GiftCertificateModel},
 * validates client model of Gift Certificate .
 */
@Component("giftCertificateValidator")
public class GiftCertificateValidator extends Validator<GiftCertificateModel> {
    private static final int DURATION_MAX_VALUE = 32768;
    private static final int NAME_MAX_LENGTH = 200;
    private static final int DESCRIPTION_MAX_LENGTH = 2000;

    /**
     * Validator for Tag models .
     */
    private final TagValidator tagValidator;

    /**
     * Constructs <code>GiftCertificateValidator</code> class
     * with injected validator for tags .
     *
     * @param tagValidator {@link #tagValidator}
     */
    @Autowired
    public GiftCertificateValidator(TagValidator tagValidator) {
        this.tagValidator = tagValidator;
    }

    @Override
    public void validateForUpdate(GiftCertificateModel giftCertificate) {
        setValidationMap(new HashMap<>());
        if (giftCertificate != null) {
            String name = giftCertificate.getName();
            String description = giftCertificate.getDescription();
            BigDecimal price = giftCertificate.getPrice();
            Integer duration = giftCertificate.getDuration();
            String createDate = giftCertificate.getCreateDate();
            if ((name != null) && (!isNameValid(name))) {
                validationMap.put("name", name);
            }
            if ((description != null) && (!isDescriptionValid(description))) {
                validationMap.put("description", description);
            }
            if ((price != null) && (!isPriceValid(price))) {
                validationMap.put("price", price);
            }
            if ((duration != null) && (!isDurationValid(duration))) {
                validationMap.put("duration", duration);
            }
            if ((createDate != null) && (!isCreateDateValid(createDate))) {
                validationMap.put("createDate", createDate);
            }
            Set<TagModel> tags = giftCertificate.getTags();
            if (tags != null) {
                validateTags(tags);
            }
            if (!validationMap.isEmpty()) {
                throw new InvalidFieldValueException("Tag", validationMap);
            }
        }
    }

    @Override
    public void validateForCreate(GiftCertificateModel giftCertificate) {
        setValidationMap(new HashMap<>());
        if (giftCertificate != null) {
            String name = giftCertificate.getName();
            String description = giftCertificate.getDescription();
            BigDecimal price = giftCertificate.getPrice();
            Integer duration = giftCertificate.getDuration();
            String createDate = giftCertificate.getCreateDate();
            validateForNullables(giftCertificate);
            if (!isNameValid(name)) {
                validationMap.put("name", name);
            }
            if (!isCreateDateValid(createDate)) {
                validationMap.put("createDate", createDate);
            }
            if (!isDurationValid(duration)) {
                validationMap.put("duration", duration);
            }
            if (!isPriceValid(price)) {
                validationMap.put("price", price);
            }
            if (!isDescriptionValid(description)) {
                validationMap.put("description", description);
            }
            Set<TagModel> tags = giftCertificate.getTags();
            if (tags != null) {
                validateTags(tags);
            }
            if (!validationMap.isEmpty()) {
                throw new InvalidFieldValueException("Tag", validationMap);
            }
        }
    }

    boolean isCreateDateValid(String createDate) {
        if (createDate != null) {
            try {
                LocalDateTime temp = LocalDateTime.parse(createDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                if (!(temp.isBefore(LocalDateTime.now()))) {
                    return false;
                }
            } catch (DateTimeParseException exception) {
                return false;
            }
        }
        return true;
    }

    boolean isDurationValid(Integer duration) {
        return (duration > 0) && (duration <= DURATION_MAX_VALUE);
    }

    boolean isPriceValid(BigDecimal price) {
        return (price.compareTo(BigDecimal.ZERO) >= 0);
    }

    boolean isNameValid(String name) {
        return (isMatchToRegex(name, AT_LEAST_ONE_LETTER_SET_REGEX_PATTERN))
                && (name.length() <= NAME_MAX_LENGTH);
    }

    boolean isDescriptionValid(String description) {
        return (isMatchToRegex(description, AT_LEAST_ONE_LETTER_SET_REGEX_PATTERN))
                && (description.length() <= DESCRIPTION_MAX_LENGTH);
    }

    private void validateForNullables(GiftCertificateModel giftCertificate) {
        if (giftCertificate.getName() == null) {
            validationMap.put("name", null);
        }
        if (giftCertificate.getDescription() == null) {
            validationMap.put("description", null);
        }
        if (giftCertificate.getPrice() == null) {
            validationMap.put("price", null);
        }
        if (giftCertificate.getDuration() == null) {
            validationMap.put("duration", null);
        }
        if (!validationMap.isEmpty()) {
            throw new InvalidFieldValueException("Tag", validationMap);
        }
    }

    private void validateTags(Set<TagModel> tags) {
        Set<Object> invalidTagsName = new HashSet<>();
        Set<Object> invalidTagsId = new HashSet<>();
        tags.forEach(tag -> {
            try {
                tagValidator.validateForUpdate(tag);
            } catch (InvalidFieldValueException exception) {
                Map<String, Object> tagsValidationMap = exception.getValidationMap();
                if (tagsValidationMap.containsKey("name")) {
                    String invalidTagName = tagsValidationMap.get("name").toString();
                    if ((invalidTagName.isEmpty()) || (invalidTagName.trim().isEmpty())) {
                        invalidTagsName.add("[EMPTY OR BLANK]");
                    } else {
                        invalidTagsName.add(invalidTagName);
                    }
                }
                if (tagsValidationMap.containsKey("id")) {
                    invalidTagsId.add(tagsValidationMap.get("id"));
                }
                StringBuilder invalidTagsValues = new StringBuilder("{")
                        .append(invalidTagsName.isEmpty() ? "" : "names: " + invalidTagsName)
                        .append(invalidTagsId.isEmpty() ? "" : "ids: " + invalidTagsId)
                        .append("}");
                validationMap.put("tags", invalidTagsValues);
            }
        });
    }
}

