package com.github.dominaspl.convertapp.service.Impl;

import com.github.dominaspl.convertapp.domain.dto.ContactDTO;
import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import com.github.dominaspl.convertapp.domain.entity.CustomerEntity;
import com.github.dominaspl.convertapp.domain.enumeration.AssertionErrorKey;
import com.github.dominaspl.convertapp.domain.enumeration.BusinessExceptionKey;
import com.github.dominaspl.convertapp.domain.exception.BusinessException;
import com.github.dominaspl.convertapp.persistence.dao.ContactDAO;
import com.github.dominaspl.convertapp.persistence.dao.CustomerDAO;
import com.github.dominaspl.convertapp.persistence.mapper.impl.ContactMapper;
import com.github.dominaspl.convertapp.persistence.mapper.impl.CustomerMapper;
import com.github.dominaspl.convertapp.service.CustomerService;
import com.github.dominaspl.convertapp.validator.impl.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
//@Transactional
public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO customerDAO;
    private ContactDAO contactDAO;
    private CustomerMapper customerMapper;
    private ContactMapper contactMapper;
    private CustomerValidator customerValidator;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO, ContactDAO contactDAO, CustomerMapper customerMapper, ContactMapper contactMapper, CustomerValidator customerValidator) {
        this.customerDAO = customerDAO;
        this.contactDAO = contactDAO;
        this.customerMapper = customerMapper;
        this.contactMapper = contactMapper;
        this.customerValidator = customerValidator;
    }

    @Override
    public void saveCustomers(List<CustomerDTO> customerDTOList) {
        if (Objects.isNull(customerDTOList)) {
            throw new AssertionError(AssertionErrorKey.PROVIDED_OBJECT_CANNOT_BE_NULL);
        }
        customerDTOList.forEach(c -> customerValidator.validate(c));
        save(customerDTOList);
    }

    @Override
    public void saveCustomers(String body) {
        if (Objects.isNull(body)) {
            throw new AssertionError(AssertionErrorKey.PROVIDED_OBJECT_CANNOT_BE_NULL);
        }
        List<CustomerDTO> customers = convertFileToObjects(body);
        customers.forEach(c -> customerValidator.validate(c));
        save(customers);
    }

    public void save(List<CustomerDTO> customerDTOList) {
        if (Objects.isNull(customerDTOList)) {
            throw new AssertionError(AssertionErrorKey.PROVIDED_OBJECT_CANNOT_BE_NULL);
        }
        List<CustomerEntity> customers = customerMapper.mapToEntities(customerDTOList);
        try {
            Long customerId;
            for (int i = 0; i < customers.size(); i++) {
                customerId = customerDAO.save(customers.get(i));
                if (Objects.isNull(customerId)) {
                    throw new BusinessException(BusinessExceptionKey.OBJECT_NOT_FOUND_IN_DATABASE);
                }
                contactDAO.saveContacts(contactMapper.mapToEntities(customerDTOList.get(i).getContacts()), customerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<CustomerDTO> convertFileToObjects(String body) {

        List<CustomerDTO> customers = new ArrayList<>();
        String[] customersString = body.split("\n");

        for (String customerString : customersString) {
            customers.add(createCustomer(customerString));
        }

        return customers;
    }

    private CustomerDTO createCustomer(String customerString) {
        CustomerDTO customer = new CustomerDTO();
        List<ContactDTO> contacts = new ArrayList<>();
        String[] splittedData = customerString.replaceAll("\\s+","").split(",");

        for (int i = 0; i < splittedData.length; i++) {
            switch (i) {
                case 0: {
                    customer.setName(splittedData[0]);
                    break;
                }
                case 1: {
                    customer.setSurname(splittedData[1]);
                    break;
                }
                case 2: {
                    customer.setAge(splittedData[2]);
                    break;
                }
                case 3:
                    continue;
                default: {
                    ContactDTO contact = new ContactDTO();
                    contact.setContact(splittedData[i]);
                    contacts.add(contact);
                    break;
                }
            }
        }
        customer.setContacts(contacts);
        return customer;
    }

}

