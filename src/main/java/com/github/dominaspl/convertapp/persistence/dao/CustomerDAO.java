package com.github.dominaspl.convertapp.persistence.dao;

import com.github.dominaspl.convertapp.domain.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(CustomerEntity customer) {
        jdbcTemplate.update("INSERT INTO T_CUSTOMERS (NAME, SURNAME, AGE) VALUES (?, ?, ?)",
                customer.getName(),
                customer.getSurname(),
                customer.getAge()
        );
    }
}
