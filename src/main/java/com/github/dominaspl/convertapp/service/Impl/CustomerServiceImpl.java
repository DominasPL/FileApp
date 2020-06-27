package com.github.dominaspl.convertapp.service.Impl;

import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import com.github.dominaspl.convertapp.domain.entity.CustomerEntity;
import com.github.dominaspl.convertapp.domain.enumeration.AssertionErrorKey;
import com.github.dominaspl.convertapp.persistence.dao.CustomerDAO;
import com.github.dominaspl.convertapp.persistence.mapper.CustomerMapper;
import com.github.dominaspl.convertapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO customerDAO;
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO, CustomerMapper customerMapper) {
        this.customerDAO = customerDAO;
        this.customerMapper = customerMapper;
    }

    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        if (Objects.isNull(customerDTO)) {
            throw new AssertionError(AssertionErrorKey.PROVIDED_OBJECT_CANNOT_BE_NULL);
        }
        CustomerEntity customerEntity = customerMapper.mapToEntity(customerDTO);
        customerDAO.save(customerEntity);
    }
}
