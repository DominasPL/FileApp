package com.github.dominaspl.convertapp.domain.validator.impl;

import com.github.dominaspl.convertapp.domain.dto.ContactDTO;
import com.github.dominaspl.convertapp.domain.enumeration.ValidationExceptionKey;
import com.github.dominaspl.convertapp.domain.exception.CustomValidationException;
import com.github.dominaspl.convertapp.persistence.dao.ContactDAO;
import com.github.dominaspl.convertapp.domain.validator.CustomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ContactValidator implements CustomValidator<ContactDTO> {

    private ContactDAO contactDAO;

    @Autowired
    public ContactValidator(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    @Override
    public void validate(ContactDTO contactDTO) {
        if (Objects.isNull(contactDTO)) {
            throw new CustomValidationException("Customer contacts", ValidationExceptionKey.OBJECT_CANNOT_BE_NULL);
        } else {
            String contact = contactDTO.getContact();
            if (Objects.isNull(contact)) {
                throw new CustomValidationException("Contact", ValidationExceptionKey.OBJECT_CANNOT_BE_NULL);
            } else if (contact.isEmpty() || contact.length() > 255) {
                throw new CustomValidationException("Contact", ValidationExceptionKey.INCORRECT_OBJECT_LENGTH);
            } else if (!checkContactIsUnique(contact)) {
                throw new CustomValidationException("Contact", ValidationExceptionKey.NAME_IS_NOT_AVAILABLE);
            }
        }
    }

    private boolean checkContactIsUnique(String contact) {
        return contactDAO.checkContactExists(contact);
    }
}
