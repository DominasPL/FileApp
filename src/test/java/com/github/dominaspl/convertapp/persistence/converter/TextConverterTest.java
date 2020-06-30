package com.github.dominaspl.convertapp.persistence.converter;

import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextConverterTest {

    private TextConverter textConverter;

    @BeforeEach
    void setUp() {
        textConverter = new TextConverter();
    }

    @Test
    void shouldConvertCustomesFromFileToListOfCustomerObjects() {
        //given
        String body = "Jan,Kowalski,12,Lublin,123123123,654 765 765,kowalski@gmail.com,jan@gmail.com\n" +
                "Adam,Nowak,,Lublin,123123123,adam@gmail.com,12321,jbr";

        //when
        List<CustomerDTO> customers = textConverter.convertFileToObjects(body);

        //then
        assertNotNull(customers);
        assertEquals(2, customers.size());
        assertEquals("12", customers.get(0).getAge());
        assertNotNull(customers.get(1).getContacts());
        assertEquals("123123123", customers.get(1).getContacts().get(0).getContact());
    }

    @Test
    void shouldConvertCustomesFromFileToListOfCustomerObjectsWithEmptyContactList() {
        //given
        String body = "Jan,Kowalski\n" +
                "Adam,Nowak,,Lublin";

        //when
        List<CustomerDTO> customers = textConverter.convertFileToObjects(body);

        //then
        assertNotNull(customers);
        assertEquals(2, customers.size());
        assertNotNull(customers.get(0).getContacts());
        assertEquals(0, customers.get(0).getContacts().size());
        assertNotNull(customers.get(1).getAge());
        assertEquals(0, customers.get(1).getAge().length());
    }
}