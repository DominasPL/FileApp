package com.github.dominaspl.convertapp.domain.validator.impl;

import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import com.github.dominaspl.convertapp.domain.enumeration.ValidationExceptionKey;
import com.github.dominaspl.convertapp.domain.exception.CustomValidationException;
import com.github.dominaspl.convertapp.domain.validator.CustomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CustomerValidator implements CustomValidator<CustomerDTO> {

    private ContactValidator contactValidator;

    @Autowired
    public CustomerValidator(ContactValidator contactValidator) {
        this.contactValidator = contactValidator;
    }

    @Override
    public void validate(CustomerDTO customerDTO) {
        if (Objects.isNull(customerDTO)) {
            throw new CustomValidationException("Customer", ValidationExceptionKey.OBJECT_CANNOT_BE_NULL);
        } else {
            String name = customerDTO.getName();
            if (Objects.isNull(name)) {
                throw new CustomValidationException("Name", ValidationExceptionKey.OBJECT_CANNOT_BE_NULL);
            } else if (name.isEmpty() || name.length() > 255) {
                throw new CustomValidationException("Name", ValidationExceptionKey.INCORRECT_OBJECT_LENGTH);
            }
            String surname = customerDTO.getSurname();
            if (Objects.isNull(surname)) {
                throw new CustomValidationException("Surname", ValidationExceptionKey.OBJECT_CANNOT_BE_NULL);
            } else if (surname.isEmpty() || surname.length() > 255) {
                throw new CustomValidationException("Surname", ValidationExceptionKey.INCORRECT_OBJECT_LENGTH);
            }
            String age = customerDTO.getAge();

            if (!Objects.isNull(age) && !age.isEmpty()) {
                Pattern pattern = Pattern.compile("\\d{1,3}");
                Matcher matcher = pattern.matcher(age);
                if (!matcher.matches())
                    throw new CustomValidationException("Age", ValidationExceptionKey.INCORRECT_TYPE_PROVIDED);
            }
            customerDTO.getContacts().forEach(c -> contactValidator.validate(c));
        }
    }
}
