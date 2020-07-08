package com.github.dominaspl.convertapp.service;

import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface CustomerService {
    void saveCustomersXml(List<CustomerDTO> customerDTO);
    void saveCustomersText(String body);
    void save(CustomerDTO customerDTO);
    void saveCustomersXmlFile(MultipartFile file) throws JAXBException;
}
