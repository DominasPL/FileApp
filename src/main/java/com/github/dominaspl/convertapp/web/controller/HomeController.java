package com.github.dominaspl.convertapp.web.controller;

import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import com.github.dominaspl.convertapp.domain.response.CustomResponse;
import com.github.dominaspl.convertapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/")
@Validated
public class HomeController {

    private CustomerService customerService;

    @Autowired
    public HomeController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> saveCustomersXml(@RequestBody List<CustomerDTO> customers) {
        customerService.saveCustomersXml(customers);
        return new ResponseEntity<>(new CustomResponse("Customers have been added successfully"), HttpStatus.CREATED);
    }

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> saveCustomersText(@RequestBody String body) {
        customerService.saveCustomersText(body);
        return new ResponseEntity<>(new CustomResponse("Customers have been added successfully"), HttpStatus.CREATED);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> saveCustomersXmlRequestParam(@RequestParam("file") MultipartFile file) {
        customerService.saveCustomersXmlFile(file);
        return new ResponseEntity<>(new CustomResponse("Customers have been added successfully"), HttpStatus.CREATED);
    }
}
