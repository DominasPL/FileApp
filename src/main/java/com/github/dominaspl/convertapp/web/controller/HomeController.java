package com.github.dominaspl.convertapp.web.controller;

import com.github.dominaspl.convertapp.service.CustomerService;
import com.github.dominaspl.convertapp.web.response.CustomResponseClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Validated
public class HomeController {

    private CustomerService customerService;

    @Autowired
    public HomeController(CustomerService customerService) {
        this.customerService = customerService;
    }

//    @PostMapping(consumes = "text/csv", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<CustomResponseClass> saveFile(@RequestBody @Valid List<CustomerDTO> customerDTO) {
//        customerService.saveCustomers(customerDTO);
//        return new ResponseEntity<>(new CustomResponseClass("Customers have been added successfully"), HttpStatus.OK);
//    }

    @PostMapping(consumes = "text/plain", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponseClass> saveCsvFile(@RequestBody String body) {
        customerService.saveCustomers(body);
        return new ResponseEntity<>(new CustomResponseClass("Customers have been added successfully"), HttpStatus.OK);
    }

}
