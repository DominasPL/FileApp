package com.github.dominaspl.convertapp.persistence.dao;

import com.github.dominaspl.convertapp.domain.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

class CustomerDAOTest {

    private JdbcTemplate customJdbcTemplate;
    private CustomerEntity customer;

    @InjectMocks
    private CustomerDAO customerDAO;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        jdbcTemplate = new JdbcTemplate();

        customer = new CustomerEntity();
        customer.setName("Dominik");
        customer.setSurname("Dominas");
        customer.setAge(15);
    }

//    @Test
//    void shouldExecuteInsertSqlQueryWhenCustomerAgeIsNotNullAndReturnCustomerId() throws SQLException {
//        //given
//        String INSERT_SQL = "INSERT INTO T_CUSTOMERS (NAME, SURNAME, AGE) VALUES (?, ?, ?)";
//        String INSERT_SQL_WITHOUT_AGE = "INSERT INTO T_CUSTOMERS (NAME, SURNAME) VALUES (?, ?)";
//
//        //when
//        when(jdbcTemplate.getDataSource())
//                .thenReturn(customJdbcTemplate.getDataSource());
//        Long id = customerDAO.save(customer);
//
//        //then
//        assertNotNull(id);
//    }

}