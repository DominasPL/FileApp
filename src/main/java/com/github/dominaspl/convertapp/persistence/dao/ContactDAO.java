package com.github.dominaspl.convertapp.persistence.dao;

import com.github.dominaspl.convertapp.domain.entity.ContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ContactDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ContactEntity contact, Long customerId) {
        jdbcTemplate.update("INSERT INTO T_CONTACTS (CUSTOMER_ID, TYPE, CONTACT) VALUES (?, ?, ?)",
                customerId,
                contact.getType(),
                contact.getContact()
        );
    }

}
