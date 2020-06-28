package com.github.dominaspl.convertapp.domain.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CustomerDTO {

    @Size(min = 3, max = 255, message = "Name length must be greater than 2 and less than 256 characters")
    private String name;

    @Size(min = 3, max = 255, message = "Surname length must be greater than 2 and less than 256 characters")
    private String surname;

    @NotNull
    private Integer age;

    @Valid
    private List<ContactDTO> contacts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDTO> contacts) {
        this.contacts = contacts;
    }
}
