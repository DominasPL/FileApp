package com.github.dominaspl.convertapp.persistence.dao;

import com.github.dominaspl.convertapp.domain.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Objects;

@Component
public class CustomerDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(CustomerEntity customer) throws SQLException {
        String INSERT_SQL = "INSERT INTO T_CUSTOMERS (NAME, SURNAME, AGE) VALUES (?, ?, ?)";
        String INSERT_SQL_WITHOUT_AGE = "INSERT INTO T_CUSTOMERS (NAME, SURNAME) VALUES (?, ?)";

        Connection connection = jdbcTemplate.getDataSource().getConnection();
        PreparedStatement preparedStatement;
        if (!Objects.isNull(customer.getAge())) {
            preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,  customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setInt(3, customer.getAge());
        } else {
            preparedStatement = connection.prepareStatement(INSERT_SQL_WITHOUT_AGE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,  customer.getName());
            preparedStatement.setString(2, customer.getSurname());
        }

        preparedStatement.executeUpdate();
        ResultSet keys = preparedStatement.getGeneratedKeys();

        Long customerId = null;
        if (keys.next()) {
            customerId = keys.getLong(1);
        }

        return customerId;
    }
}


