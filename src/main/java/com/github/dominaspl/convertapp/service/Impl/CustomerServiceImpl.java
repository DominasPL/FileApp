package com.github.dominaspl.convertapp.service.Impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import com.github.dominaspl.convertapp.domain.dto.CustomerDataSet;
import com.github.dominaspl.convertapp.domain.entity.CustomerEntity;
import com.github.dominaspl.convertapp.domain.enumeration.AssertionErrorKey;
import com.github.dominaspl.convertapp.domain.enumeration.BusinessExceptionKey;
import com.github.dominaspl.convertapp.domain.exception.BusinessException;
import com.github.dominaspl.convertapp.domain.validator.impl.CustomerValidator;
import com.github.dominaspl.convertapp.persistence.converter.TextConverter;
import com.github.dominaspl.convertapp.persistence.dao.ContactDAO;
import com.github.dominaspl.convertapp.persistence.dao.CustomerDAO;
import com.github.dominaspl.convertapp.persistence.mapper.impl.ContactMapper;
import com.github.dominaspl.convertapp.persistence.mapper.impl.CustomerMapper;
import com.github.dominaspl.convertapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO customerDAO;
    private ContactDAO contactDAO;
    private CustomerMapper customerMapper;
    private ContactMapper contactMapper;
    private CustomerValidator customerValidator;
    private TextConverter textConverter;

    public CustomerServiceImpl() {
    }

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO, ContactDAO contactDAO, CustomerMapper customerMapper, ContactMapper contactMapper, CustomerValidator customerValidator, TextConverter textConverter) {
        this.customerDAO = customerDAO;
        this.contactDAO = contactDAO;
        this.customerMapper = customerMapper;
        this.contactMapper = contactMapper;
        this.customerValidator = customerValidator;
        this.textConverter = textConverter;
    }

    @Override
    public void saveCustomersXml(List<CustomerDTO> customers) {
        if (Objects.isNull(customers)) {
            throw new AssertionError(AssertionErrorKey.PROVIDED_OBJECT_CANNOT_BE_NULL);
        }
        validateAndSave(customers);
    }

    @Override
    public void saveCustomersText(String body) {
        if (Objects.isNull(body)) {
            throw new AssertionError(AssertionErrorKey.PROVIDED_OBJECT_CANNOT_BE_NULL);
        }
        validateAndSave(textConverter.convertFileToObjects(body));
    }

    @Override
    public void save(CustomerDTO customerDTO) {
        if (Objects.isNull(customerDTO)) {
            throw new AssertionError(AssertionErrorKey.PROVIDED_OBJECT_CANNOT_BE_NULL);
        }
        CustomerEntity customer = customerMapper.mapToEntity(customerDTO);
        try {
            Long customerId;
                customerId = customerDAO.save(customer);
                if (Objects.isNull(customerId)) {
                    throw new BusinessException(BusinessExceptionKey.OBJECT_NOT_FOUND_IN_DATABASE);
                }
                contactDAO.saveContacts(contactMapper.mapToEntities(customerDTO.getContacts()), customerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveCustomersFile(MultipartFile file) throws HttpMediaTypeNotSupportedException {
        if (Objects.isNull(file)) {
            throw new AssertionError(AssertionErrorKey.PROVIDED_OBJECT_CANNOT_BE_NULL);
        }
        String fileType = file.getContentType();
        if (!Objects.isNull(fileType) && fileType.equals("application/xml")) {
            validateAndSave(deserializeXmlFile(file));
        } else if (!Objects.isNull(fileType) && (fileType.equals("text/plain") || fileType.equals("text/csv"))) {
            validateAndSave(convertTextToCustomerObjects(file));
        } else {
            throw new HttpMediaTypeNotSupportedException(HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString());
        }
    }

    private List<CustomerDTO> convertTextToCustomerObjects(MultipartFile file) {
        try {
            String customersText = new BufferedReader(new InputStreamReader(file.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));
            return textConverter.convertFileToObjects(customersText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<CustomerDTO> deserializeXmlFile(MultipartFile file) {
        try {
            ObjectMapper xmlMapper = new XmlMapper();
            xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            CustomerDataSet dataSet = xmlMapper.readValue(file.getInputStream(), CustomerDataSet.class);
            return dataSet.getCustomers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void validateAndSave(List<CustomerDTO> customers) {
        customers.forEach(c ->  {
            customerValidator.validate(c);
            save(c);
        });
    }
}

