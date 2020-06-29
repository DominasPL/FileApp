package com.github.dominaspl.convertapp.service;

import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    void saveCustomers(List<CustomerDTO> customerDTO);
    void saveCustomers(String body);
}
