package com.github.dominaspl.convertapp.service.Impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO customerDAO;
    private ContactDAO contactDAO;
    private CustomerMapper customerMapper;
    private ContactMapper contactMapper;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO, ContactDAO contactDAO, CustomerMapper customerMapper, ContactMapper contactMapper) {
        this.customerDAO = customerDAO;
        this.contactDAO = contactDAO;
        this.customerMapper = customerMapper;
        this.contactMapper = contactMapper;
    }

    @Override
    public void saveCustomers(List<CustomerDTO> customerDTOList) {
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
}

