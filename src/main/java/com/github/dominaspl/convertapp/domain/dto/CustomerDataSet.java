package com.github.dominaspl.convertapp.domain.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "people")
public class CustomerDataSet {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "person")
    private List<CustomerDTO> customers;

    public List<CustomerDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerDTO> customers) {
        this.customers = customers;
    }
}
