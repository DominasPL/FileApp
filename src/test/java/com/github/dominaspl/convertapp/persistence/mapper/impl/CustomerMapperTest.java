package com.github.dominaspl.convertapp.persistence.mapper.impl;

import com.github.dominaspl.convertapp.domain.dto.ContactDTO;
import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import com.github.dominaspl.convertapp.domain.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private CustomerDTO customerDTO;

    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        customerMapper = new CustomerMapper();
        customerDTO = new CustomerDTO();
        customerDTO.setName("Jan");
        customerDTO.setSurname("Gruszka");
        customerDTO.setAge("104");
        customerDTO.setContacts(new ArrayList<>());

        ContactDTO contactDTO = new ContactDTO("stepuchdominik@gmail.com");
        customerDTO.getContacts().add(contactDTO);
    }

    @Test
    void shouldThrowAssertionErrorWhenGivenCustomerIsNull() {
        //then
        assertThrows(AssertionError.class, () -> customerMapper.mapToEntity(null));
    }

    @Test
    void shouldReturnCustomerEntityWithNullAge() {
        //given
        customerDTO.setAge(null);

        //when
        CustomerEntity customer = customerMapper.mapToEntity(customerDTO);

        //then
        assertNotNull(customer);
        assertNull(customer.getAge());
        assertEquals(customerDTO.getName(), customer.getName());
        assertEquals(customerDTO.getSurname(), customer.getSurname());
    }

    @Test
    void shouldReturnCustomerEntityWithAge() {
        //when
        CustomerEntity customer = customerMapper.mapToEntity(customerDTO);

        //then
        assertNotNull(customer);
        assertNotNull(customer.getAge());
        assertEquals(customerDTO.getName(), customer.getName());
        assertEquals(customerDTO.getSurname(), customer.getSurname());
    }

    @Test
    void shouldThrowAssertionErrorWhenGivenCustomerListIsNull() {
        //then
        assertThrows(AssertionError.class, () -> customerMapper.mapToEntities(null));
    }

    @Test
    void shouldReturnConvertedListOfCustomers() {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        customerDTOList.add(customerDTO);
        customerDTOList.add(customerDTO);

        //when
        List<CustomerEntity> customers = customerMapper.mapToEntities(customerDTOList);

        //then
        assertNotNull(customers);
        assertEquals(2, customers.size());
    }

}