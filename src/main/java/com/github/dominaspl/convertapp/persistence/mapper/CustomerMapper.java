package com.github.dominaspl.convertapp.persistence.mapper;

import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import com.github.dominaspl.convertapp.domain.entity.CustomerEntity;
import com.github.dominaspl.convertapp.domain.enumeration.AssertionErrorKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomerMapper implements Mapper<CustomerDTO, CustomerEntity> {

    private ContactMapper contactMapper;

    @Autowired
    public CustomerMapper(ContactMapper contactMapper) {
        this.contactMapper = contactMapper;
    }

    @Override
    public CustomerEntity mapToEntity(CustomerDTO customerDTO) {
        if (Objects.isNull(customerDTO)) {
            throw new AssertionError(AssertionErrorKey.PROVIDED_OBJECT_CANNOT_BE_NULL);
        }

        CustomerEntity customer = new CustomerEntity();
        customer.setName(customerDTO.getName());
        customer.setSurname(customerDTO.getSurname());
        customer.setAge(customerDTO.getAge());
        customer.setContact(contactMapper.mapToEntity(customerDTO.getContact()));
        return customer;
    }

}
