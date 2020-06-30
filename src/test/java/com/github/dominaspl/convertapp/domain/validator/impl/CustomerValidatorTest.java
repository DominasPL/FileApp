package com.github.dominaspl.convertapp.domain.validator.impl;

import com.github.dominaspl.convertapp.domain.dto.ContactDTO;
import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import com.github.dominaspl.convertapp.domain.exception.CustomValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomerValidatorTest {

    private CustomerDTO customerDTO;
    private String moreThan255CharactersStr;

    @InjectMocks
    private CustomerValidator customerValidator;

    @Mock
    private ContactValidator contactValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        customerDTO = new CustomerDTO();
        customerDTO.setName("Jan");
        customerDTO.setSurname("Gruszka");
        customerDTO.setAge("104");
        customerDTO.setContacts(new ArrayList<>());

        ContactDTO contactDTO = new ContactDTO("stepuchdominik@gmail.com");
        customerDTO.getContacts().add(contactDTO);

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
    void shouldThrowCustomValidationExceptionWhenGivenCustomerIsNull() {
        //then
        assertThrows(CustomValidationException.class, () -> customerValidator.validate(null));
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenGivenNameIsNull() {
        //given
        customerDTO.setName(null);

        //then
        assertThrows(CustomValidationException.class, () -> customerValidator.validate(customerDTO));
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenGivenNameIsEmpty() {
        //given
        customerDTO.setName("");

        //then
        assertThrows(CustomValidationException.class, () -> customerValidator.validate(customerDTO));
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenGivenNameHasMoreThan255Characters() {
        //given
        customerDTO.setName(moreThan255CharactersStr);

        //then
        assertThrows(CustomValidationException.class, () -> customerValidator.validate(customerDTO));
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenGivenSurnameIsNull() {
        //given
        customerDTO.setSurname(null);

        //then
        assertThrows(CustomValidationException.class, () -> customerValidator.validate(customerDTO));
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenGivenSurnameIsEmpty() {
        //given
        customerDTO.setSurname("");

        //then
        assertThrows(CustomValidationException.class, () -> customerValidator.validate(customerDTO));
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenGivenSurnameHasMoreThan255Characters() {
        //given
        customerDTO.setSurname(moreThan255CharactersStr);

        //then
        assertThrows(CustomValidationException.class, () -> customerValidator.validate(customerDTO));
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenGivenAgeIsNotCorrect() {
        //given
        customerDTO.setAge("dsadsadsad");

        //then
        assertThrows(CustomValidationException.class, () -> customerValidator.validate(customerDTO));
    }

    @Test
    void shouldCallContactValidatorMockValidateMethod() {
        //given
        List<ContactDTO> contacts = new ArrayList<>();
        ContactDTO contactDTO = new ContactDTO();
        contacts.add(contactDTO);
        contacts.add(contactDTO);
        contacts.add(contactDTO);
        customerDTO.setContacts(contacts);

        //when
        doNothing().when(contactValidator).validate(contactDTO);
        customerValidator.validate(customerDTO);

        //then
        verify(contactValidator, atLeast(3)).validate(contactDTO);
    }
}