package com.github.dominaspl.convertapp.web.controller;

import com.github.dominaspl.convertapp.domain.dto.ContactDTO;
import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import com.github.dominaspl.convertapp.service.Impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class HomeControllerTest {

    private MockMvc mockMvc;
    private List<CustomerDTO> customers;
    private String response;

    @InjectMocks
    private HomeController homeController;

    @Mock
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Jan");
        customerDTO.setSurname("Gruszka");
        customerDTO.setAge("104");
        customerDTO.setContacts(new ArrayList<>());

        ContactDTO contactDTO = new ContactDTO("stepuchdominik@gmail.com");
        customerDTO.getContacts().add(contactDTO);

        customers = new ArrayList<>();
        customers.add(customerDTO);
        customers.add(customerDTO);

        response = "{\n" +
                "    \"message\": \"Customers have been added successfully\",\n" +
                "    \"status\": \"200 OK\"\n" +
                "}";
    }

    @Test
    void testIfSaveCustomersXmlMethodReturnsCreatedHttpStatusAndCorrectJsonResponse() throws Exception {
        //given
        String newCustomers = "<people>\n" +
                "    <person>\n" +
                "        <name>Jan</name>\n" +
                "        <surname>Kowalski</surname>\n" +
                "        <age>12</age>\n" +
                "        <city>Lublin</city>\n" +
                "        <contacts>\n" +
                "            <contact>155123123</contact>\n" +
                "        </contacts>\n" +
                "    </person>\n" +
                "    <person>\n" +
                "        <name>Adrian</name>\n" +
                "        <surname>Nalepa</surname>\n" +
                "        <age>32</age>\n" +
                "        <city>Warszawa</city>\n" +
                "        <contacts>\n" +
                "            <contact>132123123</contact>\n" +
                "        </contacts>\n" +
                "    </person>\n" +
                "</people>";

        //when
        doNothing().when(customerService).saveCustomersXml(customers);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/")
                        .header("Content-Type", "application/xml")
                        .content(newCustomers))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));

        //then
        verify(customerService, atLeast(1)).saveCustomersXml(refEq(customers));
    }

    @Test
    void testIfSaveCustomersTextMethodReturnsCreatedHttpStatusAndCorrectJsonResponse() throws Exception {
        //given
        String customers = "Jan,Kowalski,12,Lublin,123123123,654 765 765,kowalski@gmail.com,jan@gmail.com\n" +
                "Adam,Nowak,,Lublin,123111123,adam@g33mail.com,123233321,jbr";

        //when
        doNothing().when(customerService).saveCustomersText("");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/")
                        .header("Content-Type", "text/plain")
                        .content(customers))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andExpect(MockMvcResultMatchers.content().json(response));

        //then
        verify(customerService, atLeast(1)).saveCustomersText(refEq(customers));
    }

}