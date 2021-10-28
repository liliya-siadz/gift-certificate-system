package com.epam.esm.validator;

import com.epam.esm.exception.InvalidFieldValueException;
import com.epam.esm.model.GiftCertificateClientModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.epam.esm.validator.Validator.isMatchToRegex;

@Component("giftCertificateValidator")
public class GiftCertificateValidator implements Validator<GiftCertificateClientModel> {
    private static final int DURATION_MAX_VALUE = 32768;
    private static final int NAME_MAX_LENGTH = 200;
    private static final int DESCRIPTION_MAX_LENGTH = 2000;

    @Override
    public void isValidForUpdate(GiftCertificateClientModel giftCertificate) {
        if (giftCertificate != null) {
            String name = giftCertificate.getName();
            String description = giftCertificate.getDescription();
            BigDecimal price = giftCertificate.getPrice();
            Integer duration = giftCertificate.getDuration();
            String createDate = giftCertificate.getCreateDate();
            if (!(name == null)) {
                validateName(name);
            }
            if (!(description == null)) {
                validateDescription(description);
            }
            if (!(price == null)) {
                validatePrice(price);
            }
            if (!(duration == null)) {
                validateDuration(duration);
            }
            if (!(createDate == null)) {
                validateCreateDate(createDate);
            }
        }
    }

    @Override
    public void isValidForCreate(GiftCertificateClientModel giftCertificate) {
        if (giftCertificate != null) {
            validateForNullables(giftCertificate);
            validateName(giftCertificate.getName());
            validateCreateDate(giftCertificate.getCreateDate());
            validateDescription(giftCertificate.getDescription());
            validatePrice(giftCertificate.getPrice());
            validateDuration(giftCertificate.getDuration());
        }
    }

    public void validateCreateDate(String createDate) {
        if (createDate != null) {
            try {
                LocalDateTime temp = LocalDateTime.parse(createDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                if (!(temp.isBefore(LocalDateTime.now()))) {
                    throw new InvalidFieldValueException("createDate");
                }
            } catch (DateTimeParseException exception) {
                throw new InvalidFieldValueException("createDate");
            }
        }
    }

    public void validateDuration(Integer duration) {
        if (!((duration > 0) && (duration <= DURATION_MAX_VALUE))) {
            throw new InvalidFieldValueException("duration");
        }
    }

    public void validatePrice(BigDecimal price) {
        if (!(price.compareTo(BigDecimal.ZERO) >= 0)) {
            throw new InvalidFieldValueException("price");
        }
    }

    public void validateName(String name) {
        if (!((isMatchToRegex(name, AT_LEAST_ONE_LETTER_SET_REGEX_PATTERN))
                && (name.length() <= NAME_MAX_LENGTH))) {
            throw new InvalidFieldValueException("name");
        }
    }

    public void validateDescription(String description) {
        if (!((isMatchToRegex(description, AT_LEAST_ONE_LETTER_SET_REGEX_PATTERN))
                && (description.length() <= DESCRIPTION_MAX_LENGTH))) {
            throw new InvalidFieldValueException("description");
        }
    }

    private void validateForNullables(GiftCertificateClientModel giftCertificate) {
        if (giftCertificate.getName() == null) {
            throw new InvalidFieldValueException("name");
        }
        if (giftCertificate.getDescription() == null) {
            throw new InvalidFieldValueException("description");
        }
        if (giftCertificate.getPrice() == null) {
            throw new InvalidFieldValueException("price");
        }
        if (giftCertificate.getDuration() == null) {
            throw new InvalidFieldValueException("duration");
        }
    }
}

