package com.github.dominaspl.convertapp.domain.validator.impl;

import com.github.dominaspl.convertapp.domain.dto.ContactDTO;
import com.github.dominaspl.convertapp.domain.exception.CustomValidationException;
import com.github.dominaspl.convertapp.persistence.dao.ContactDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ContactValidatorTest {

    private ContactDTO contactDTO;
    private String moreThan255CharactersStr;

    @InjectMocks
    private ContactValidator contactValidator;

    @Mock
    private ContactDAO contactDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        contactDTO = new ContactDTO();
        contactDTO.setContact("hhello@wp.pl");

        moreThan255CharactersStr = "Etiam ornare mattis pretium. Vivamus finibus ullamcorper molestie. " +
                "Praesent suscipit odio eu lorem egestas, non ornare nulla pellentesque. Vivamus rhoncus, " +
                "ligula quis malesuada porttitor, dui mauris vehicula urna, ut facilisis ipsum felis non est. " +
                "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; " +
                "Praesent tempor, arcu eu euismod tempor, elit ex porta quam, sed venenatis est ipsum quis magna. " +
                "Donec luctus, sapien ac pretium condimentum, nisi nunc commodo velit, eu bibendum augue elit id " +
                "diam. Maecenas ac enim ligula. Sed ultricies libero vel lectus laoreet, et dapibus erat tempor." +
                " Duis facilisis neque nec dictum euismod. Nam vehicula nibh vitae metus fringilla semper. " +
                "Etiam rhoncus vel orci a sagittis. Pellentesque sagittis mi nec tortor dictum blandit id id " +
                "tortor. Aenean at faucibus ipsum. Nulla sed est massa.";
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenGivenContactIsNull() {
        //then
        assertThrows(CustomValidationException.class, () -> contactValidator.validate(null));
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenGivenContactContactIsNull() {
        //given
        contactDTO.setContact(null);

        //then
        assertThrows(CustomValidationException.class, () -> contactValidator.validate(contactDTO));
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenGivenContactIsEmpty() {
        //given
        contactDTO.setContact("");

        //then
        assertThrows(CustomValidationException.class, () -> contactValidator.validate(contactDTO));
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenGivenContactHasMoreThan255Characters() {
        //given
        contactDTO.setContact(moreThan255CharactersStr);

        //then
        assertThrows(CustomValidationException.class, () -> contactValidator.validate(contactDTO));
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenGivenContactIsNotUnique() {
        //when
        when(contactDAO.checkContactExists(contactDTO.getContact())).thenReturn(false);

        //then
        assertThrows(CustomValidationException.class, () -> contactValidator.validate(contactDTO));
        verify(contactDAO, atLeast(1)).checkContactExists(contactDTO.getContact());
    }
}