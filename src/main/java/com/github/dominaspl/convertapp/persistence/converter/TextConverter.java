package com.github.dominaspl.convertapp.persistence.converter;

import com.github.dominaspl.convertapp.domain.dto.ContactDTO;
import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TextConverter {

    public List<CustomerDTO> convertFileToObjects(String body) {

        List<CustomerDTO> customers = new ArrayList<>();
        String[] customersString = body.split("\n");

        for (String customerString : customersString) {
            customers.add(createCustomer(customerString));
        }

        return customers;
    }

    private CustomerDTO createCustomer(String customerString) {
        CustomerDTO customer = new CustomerDTO();
        List<ContactDTO> contacts = new ArrayList<>();
        String[] splittedData = customerString.replaceAll("\\s+","").split(",");

        for (int i = 0; i < splittedData.length; i++) {
            switch (i) {
                case 0: {
                    customer.setName(splittedData[0]);
                    break;
                }
                case 1: {
                    customer.setSurname(splittedData[1]);
                    break;
                }
                case 2: {
                    customer.setAge(splittedData[2]);
                    break;
                }
                case 3:
                    continue;
                default: {
                    ContactDTO contact = new ContactDTO();
                    contact.setContact(splittedData[i]);
                    contacts.add(contact);
                    break;
                }
            }
        }
        customer.setContacts(contacts);
        return customer;
    }

}
