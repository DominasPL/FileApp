package com.github.dominaspl.convertapp.domain.annotation;

import com.github.dominaspl.convertapp.persistence.dao.ContactDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueContactValidatorImpl implements ConstraintValidator<UniqueContact, String> {

    private ContactDAO contactDAO;

    @Autowired
    public UniqueContactValidatorImpl(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    @Override
    public void initialize(UniqueContact constraintAnnotation) {

    }

    @Override
    public boolean isValid(String contact, ConstraintValidatorContext constraintValidatorContext) {
        return contactDAO.checkContactExists(contact);
    }
}
