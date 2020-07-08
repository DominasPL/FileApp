package com.github.dominaspl.convertapp.service.Impl;

import com.github.dominaspl.convertapp.domain.dto.ContactDTO;
import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import com.github.dominaspl.convertapp.domain.entity.ContactEntity;
import com.github.dominaspl.convertapp.domain.entity.CustomerEntity;
import com.github.dominaspl.convertapp.domain.exception.BusinessException;
import com.github.dominaspl.convertapp.domain.validator.impl.CustomerValidator;
import com.github.dominaspl.convertapp.persistence.converter.TextConverter;
import com.github.dominaspl.convertapp.persistence.dao.ContactDAO;
import com.github.dominaspl.convertapp.persistence.dao.CustomerDAO;
import com.github.dominaspl.convertapp.persistence.mapper.impl.ContactMapper;
import com.github.dominaspl.convertapp.persistence.mapper.impl.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    private CustomerDTO customerDTO;
    private List<CustomerDTO> customers;
    private CustomerEntity customer;
    private String customersXml;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerDAO customerDAO;

    @Mock
    private ContactDAO contactDAO;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private ContactMapper contactMapper;

    @Mock
    private CustomerValidator customerValidator;

    @Mock
    private TextConverter textConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        customerDTO = new CustomerDTO();
        customerDTO.setName("Jan");
        customerDTO.setSurname("Gruszka");
        customerDTO.setAge("104");
        customerDTO.setContacts(new ArrayList<>());

        customers = new ArrayList<>();

        ContactDTO contactDTO = new ContactDTO("stepuchdominik@gmail.com");
        customerDTO.getContacts().add(contactDTO);

        customer = new CustomerEntity();
        customer.setName("Dominik");
        customer.setSurname("Dominas");
        customer.setAge(15);

        customersXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<people>\n" +
                "    <person>\n" +
                "        <name>Jan</name>\n" +
                "        <surname>Kowalski</surname>\n" +
                "        <age>12</age>\n" +
                "        <city>Lublin</city>\n" +
                "        <contacts>\n" +
                "            <contact>123123123</contact>\n" +
                "        </contacts>\n" +
                "        <contacts>\n" +
                "            <contact>654 765 765</contact>\n" +
                "        </contacts>\n" +
                "        <contacts>\n" +
                "            <contact>kowalski@gmail.com</contact>\n" +
                "        </contacts>\n" +
                "        <contacts>\n" +
                "            <contact>jan@gmail.com</contact>\n" +
                "        </contacts>\n" +
                "    </person>\n" +
                "    <person>\n" +
                "        <name>Adam</name>\n" +
                "        <surname>Nowak</surname>\n" +
                "        <city>Lublin</city>\n" +
                "        <contacts>\n" +
                "            <contact>123123142</contact>\n" +
                "        </contacts>\n" +
                "        <contacts>\n" +
                "            <contact>adam@gmail.com</contact>\n" +
                "        </contacts>\n" +
                "        <contacts>\n" +
                "            <contact>12321</contact>\n" +
                "        </contacts>\n" +
                "        <contacts>\n" +
                "            <contact>jbr:</contact>\n" +
                "        </contacts>\n" +
                "    </person>\n" +
                "</people>";
    }

    @Test
    void shouldThrowAssertionErrorWhenGivenCustomerListIsNull() {
        //then
        assertThrows(AssertionError.class, () -> customerService.saveCustomersXml(null));
    }

    @Test
    void shouldThrowAssertionErrorWhenGivenCustomerIsNull() {
        //given
        customers.add(null);

        //when
        doNothing().when(customerValidator).validate(customerDTO);

        //then
        assertThrows(AssertionError.class, () -> customerService.saveCustomersXml(customers));
        verify(customerValidator, atLeast(1)).validate(refEq(null));
    }

    @Test
    void shouldThrowBusinessExceptionWhenObjectWasNotFoundInDatabaseAfterInsertQuery() throws SQLException {
        //given
        customers.add(customerDTO);
        customers.add(customerDTO);

        //when
        doNothing().when(customerValidator).validate(customerDTO);
        when(customerMapper.mapToEntity(customerDTO)).thenReturn(customer);
        when(customerDAO.save(customer)).thenReturn(null);

        //then
        assertThrows(BusinessException.class, () -> customerService.saveCustomersXml(customers));
        verify(customerValidator, atLeast(1)).validate(refEq(customerDTO));
        verify(customerMapper, atLeast(1)).mapToEntity(refEq(customerDTO));
        verify(customerDAO, atLeast(1)).save(refEq(customer));
    }

    @Test
    void shouldCallAllMethodsFromMockedObjects() throws SQLException {
        //given
        Long customerId = 1L;
        customers.add(customerDTO);
        customers.add(customerDTO);

        List<ContactEntity> contacts = new ArrayList<>();
        ContactEntity contact = new ContactEntity();
        contact.setContact("hello@wp.pl");
        contact.setType(2);
        contacts.add(contact);

        //when
        doNothing().when(customerValidator).validate(customerDTO);
        when(customerMapper.mapToEntity(customerDTO)).thenReturn(customer);
        when(customerDAO.save(customer)).thenReturn(customerId);
        when(contactMapper.mapToEntities(customerDTO.getContacts())).thenReturn(contacts);
        doNothing().when(contactDAO).saveContacts(contacts, customerId);

        customerService.saveCustomersXml(customers);

        //then
        verify(customerValidator, atLeast(1)).validate(refEq(customerDTO));
        verify(customerMapper, atLeast(1)).mapToEntity(refEq(customerDTO));
        verify(customerDAO, atLeast(1)).save(refEq(customer));
        verify(contactMapper, atLeast(1)).mapToEntities(refEq(customerDTO.getContacts()));
        verify(contactDAO, atLeast(1)).saveContacts(refEq(contacts), refEq(customerId));
    }

    @Test
    void shouldThrowAssertionErrorWhenGivenCustomerListIsNullSaveCustomersTextMethod() {
        //then
        assertThrows(AssertionError.class, () -> customerService.saveCustomersText(null));
    }

    @Test
    void shouldCallTextConverterMockConvertFileToObjectsMethod() {
        //given
        String body = "Hello World";

        //when
        when(textConverter.convertFileToObjects(body)).thenReturn(customers);
        customerService.saveCustomersText(body);

        //then
        verify(textConverter, atLeast(1)).convertFileToObjects(refEq(body));
    }

    @Test
    void shouldCreateCustomerServiceInstance() {
        //given
        CustomerServiceImpl customerService = new CustomerServiceImpl();

        //then
        assertNotNull(customerService);
    }

    @Test
    void shouldThrowAssertionErrorWhenGivenFileIsNullSaveCustomersXmlFileMethod() {
        //then
        assertThrows(AssertionError.class, () -> customerService.saveCustomersFile(null));
    }

    @Test
    void shouldCallCustomerValidatorValidateMethodSaveCustomersXmlFileMethod() throws HttpMediaTypeNotSupportedException {
        //given
        MockMultipartFile xmlFile = new MockMultipartFile("file",
                "customers.xml", "application/xml", customersXml.getBytes());

        //when
        doNothing().when(customerValidator).validate(customerDTO);
        customerService.saveCustomersFile(xmlFile);

        //then
        verify(customerValidator, atLeast(2)).validate(any());
    }
}