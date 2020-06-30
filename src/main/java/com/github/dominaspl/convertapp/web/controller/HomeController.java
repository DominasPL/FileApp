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

import java.util.ArrayList;

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
    public ResponseEntity<CustomResponse> saveFile(@RequestBody CustomerDTO customer) {
        customerService.saveCustomers(new ArrayList<>());
        return new ResponseEntity<>(new CustomResponse("Customers have been added successfully"), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> saveCsvFile(@RequestBody String body) {
        customerService.saveCustomers(body);
        return new ResponseEntity<>(new CustomResponse("Customers have been added successfully"), HttpStatus.OK);
    }

//    @RequestMapping(value = "customers", method = RequestMethod.POST,
//            produces = "application/xml", consumes = "application/xml")
//    public ResponseEntity<CustomerDTO> createDoctor(@RequestBody CustomerDTO customer) {
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
//    }
}
