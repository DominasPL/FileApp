package com.github.dominaspl.convertapp.persistence.mapper.impl;

import com.github.dominaspl.convertapp.domain.dto.ContactDTO;
import com.github.dominaspl.convertapp.domain.entity.ContactEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactMapperTest {

    private ContactMapper contactMapper;
    private ContactDTO contactDTO;

    @BeforeEach
    void setUp() {
        contactMapper = new ContactMapper();

        contactDTO = new ContactDTO();
    }

    @Test
    void shouldThrowAssertionErrorWhenGivenContactIsNull() {
        //then
        assertThrows(AssertionError.class, () -> contactMapper.mapToEntity(null));
    }

    @Test
    void shouldThrowAssertionErrorWhenGivenContactContactIsNull() {
        //then
        assertThrows(AssertionError.class, () -> contactMapper.mapToEntity(contactDTO));
    }

    @Test
    void shouldReturnContactEntityWithUnknownType() {
        //given
        contactDTO.setContact("HelloWorld");

        //when
        ContactEntity contact = contactMapper.mapToEntity(contactDTO);

        //then
        assertNotNull(contact);
        assertEquals(contactDTO.getContact(), contact.getContact());
        assertEquals(0, contact.getType());
    }

    @Test
    void shouldReturnContactEntityWithEmailType() {
        //given
        contactDTO.setContact("domek-ste@wp.pl");

        //when
        ContactEntity contact = contactMapper.mapToEntity(contactDTO);

        //then
        assertNotNull(contact);
        assertEquals(contactDTO.getContact(), contact.getContact());
        assertEquals(1, contact.getType());
    }

    @Test
    void shouldReturnContactEntityWithPhoneType() {
        //given
        contactDTO.setContact("601 202 222");

        //when
        ContactEntity contact = contactMapper.mapToEntity(contactDTO);

        //then
        assertNotNull(contact);
        assertEquals(contactDTO.getContact(), contact.getContact());
        assertEquals(2, contact.getType());
    }

    @Test
    void shouldReturnContactEntityWithJbrType() {
        //given
        contactDTO.setContact("jbr:");

        //when
        ContactEntity contact = contactMapper.mapToEntity(contactDTO);

        //then
        assertNotNull(contact);
        assertEquals(contactDTO.getContact(), contact.getContact());
        assertEquals(3, contact.getType());
    }

    @Test
    void shouldThrowAssertionErrorWhenGivenContactListIsNull() {
        //then
        assertThrows(AssertionError.class, () -> contactMapper.mapToEntities(null));
    }

    @Test
    void shouldReturnConvertedListOfContacts() {
        List<ContactDTO> customerDTOList = new ArrayList<>();
        contactDTO.setContact("poland@gmail.com");
        customerDTOList.add(contactDTO);
        customerDTOList.add(contactDTO);

        //when
        List<ContactEntity> contacts = contactMapper.mapToEntities(customerDTOList);

        //then
        assertNotNull(contacts);
        assertEquals(2, contacts.size());
    }

}