package com.github.dominaspl.convertapp.persistence.dao;

import com.github.dominaspl.convertapp.domain.entity.ContactEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ContactDAOTest {

    private ContactEntity contact;
    private List<ContactEntity> contacts;

    @InjectMocks
    private ContactDAO contactDAO;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        contacts = new ArrayList<>();
        contact = new ContactEntity();
        contact.setContact("hello@wp.pl");
        contact.setType(2);
    }

    @Test
    void shouldCallJdbcTemplateUpdateMethod() {
        //given
        String sql = "INSERT INTO T_CONTACTS (CUSTOMER_ID, TYPE, CONTACT) VALUES (?, ?, ?)";
        Long id = 1L;
        contacts.add(contact);
        contacts.add(contact);
        contacts.add(contact);

        //when
        when(jdbcTemplate.update(sql, id, contact.getType(), contact.getContact())).thenReturn(1);
        contactDAO.saveContacts(contacts, id);

        //then
        verify(jdbcTemplate, atLeast(3))
                .update(sql, id, contact.getType(), contact.getContact());
    }

    @Test
    void shouldReturnTrueWhenObjectIsNotInDatabase() {
        //given
        String contact = "242 453 656";
        String sql = "SELECT COUNT(*) FROM T_CONTACTS WHERE CONTACT = ?";

        //when
        when(jdbcTemplate.queryForObject(sql, new Object[] { contact }, Integer.class)).thenReturn(0);
        boolean result = contactDAO.checkContactExists(contact);

        //then
        assertTrue(result);
        verify(jdbcTemplate, atLeast(1))
                .queryForObject(sql, new Object[] { contact }, Integer.class);
    }

    @Test
    void shouldReturnFalseWhenObjectIsNotInDatabase() {
        //given
        String contact = "242 453 656";
        String sql = "SELECT COUNT(*) FROM T_CONTACTS WHERE CONTACT = ?";

        //when
        when(jdbcTemplate.queryForObject(sql, new Object[] { contact }, Integer.class)).thenReturn(10);
        boolean result = contactDAO.checkContactExists(contact);

        //then
        assertFalse(result);
        verify(jdbcTemplate, atLeast(1))
                .queryForObject(sql, new Object[] { contact }, Integer.class);
    }

}