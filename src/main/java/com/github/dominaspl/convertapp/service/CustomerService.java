package com.github.dominaspl.convertapp.service;

import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    void saveCustomersXml(List<CustomerDTO> customerDTO);
    void saveCustomersText(String body);
    void save(CustomerDTO customerDTO);
}
