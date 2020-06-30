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
    private CustomerDTO customerDTO;
    private List<CustomerDTO> customers;

    @InjectMocks
    private HomeController homeController;

    @Mock
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
        customerDTO = new CustomerDTO();
        customerDTO.setName("Jan");
        customerDTO.setSurname("Gruszka");
        customerDTO.setAge("104");
        customerDTO.setContacts(new ArrayList<>());

        ContactDTO contactDTO = new ContactDTO("stepuchdominik@gmail.com");

        customerDTO.getContacts().add(contactDTO);
        customers = new ArrayList<>();
        customers.add(customerDTO);
        customers.add(customerDTO);
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

        String response = "{\n" +
                "    \"message\": \"Customers have been added successfully\",\n" +
                "    \"status\": \"200 OK\"\n" +
                "}";

        //when
        doNothing().when(customerService).saveCustomers(customers);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/")
                        .header("Content-Type", "application/xml")
                        .content(newCustomers))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));

        verify(customerService, atLeast(1)).saveCustomers(refEq(customers));
    }

//    @Test
//    void testSaveCustomersFromString() throws Exception {
//        //given
//
//
//        //when
//        Mockito.doNothing().when(customerService).saveCustomers(" ");
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/")
//                        .accept(MediaType.TEXT_PLAIN)
//                        .contentType(MediaType.TEXT_PLAIN)
//                        .content("DUPA "))
//                        .andExpect(MockMvcResultMatchers.status().isOk());
//
//    }


//    @Test
//    public void whenPostDoctor_thenAddDoctorCalled_andDoctorReturned() throws Exception {
//
//        //given
//        String newCustomer = "<customer><name>Jan</name><surname>Kowalski</surname><age>12</age><city>Lublin</city><contacts><contact>dominik-stepuch@wp.pl</contact></contacts></customer>";
//
//
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
//                .content(newCustomer)
//                .header("Content-Type","application/xml"))
//                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
//
//    }
}