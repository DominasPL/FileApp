package com.github.dominaspl.convertapp.web.controller;

import com.github.dominaspl.convertapp.domain.dto.ContactDTO;
import com.github.dominaspl.convertapp.domain.dto.CustomerDTO;
import com.github.dominaspl.convertapp.service.Impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

class HomeControllerTest {

    private MockMvc mockMvc;
    private CustomerDTO customerDTO;
    private List<CustomerDTO> customers;

    @InjectMocks
    private HomeController homeController;

    @Spy
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
    void testSaveCustomersFromXml() throws Exception {
        //given
        String newCustomer = "<customer><name>Jan</name><surname>Kowalski</surname><age>12</age><city>Lublin</city><contacts><contact>dominik-stepuch@wp.pl</contact></contacts></customer>";


        //when
        Mockito.doNothing().when(customerService).saveCustomers(new ArrayList<>());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/")
                        .header("Content-Type","application/xml")
                        .content(newCustomer))
                .andExpect(MockMvcResultMatchers.status().isCreated());
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