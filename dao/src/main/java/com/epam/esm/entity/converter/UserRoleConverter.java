package com.epam.esm.entity.converter;

import com.epam.esm.entity.security.UserRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserRoleConverter implements AttributeConverter<UserRole, String> {
    @Override
    public String convertToDatabaseColumn(UserRole attribute) {
        return attribute.toString();
    }

    @Override
    public UserRole convertToEntityAttribute(String dbData) {
<<<<<<< HEAD
        return UserRole.valueOf(dbData);
=======
        return UserRole.USER;
>>>>>>> 79b8fef (1) Jwt token created; 2) Signup and login functions realized)
    }
}
