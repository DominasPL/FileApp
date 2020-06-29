package com.github.dominaspl.convertapp.persistence.mapper.impl;

import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import com.github.dominaspl.convertapp.domain.entity.CustomerEntity;
import com.github.dominaspl.convertapp.domain.enumeration.AssertionErrorKey;
import com.github.dominaspl.convertapp.persistence.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerMapper implements Mapper<CustomerDTO, CustomerEntity> {

    @Override
    public CustomerEntity mapToEntity(CustomerDTO customerDTO) {
        if (Objects.isNull(customerDTO)) {
            throw new AssertionError(AssertionErrorKey.PROVIDED_OBJECT_CANNOT_BE_NULL);
        }

        CustomerEntity customer = new CustomerEntity();
        customer.setName(customerDTO.getName());
        customer.setSurname(customerDTO.getSurname());
        if (!Objects.isNull(customerDTO.getAge()) && !customerDTO.getAge().isEmpty()) {
            customer.setAge(Integer.parseInt(customerDTO.getAge()));
        }

        return customer;
    }

    @Override
    public List<CustomerEntity> mapToEntities(List<CustomerDTO> customerDTOList) {
        if (Objects.isNull(customerDTOList)) {
            throw new AssertionError(AssertionErrorKey.PROVIDED_OBJECT_CANNOT_BE_NULL);
        }
        return customerDTOList.stream().map(c -> mapToEntity(c)).collect(Collectors.toList());
    }
}
