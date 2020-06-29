package com.github.dominaspl.convertapp.domain.dto;

import com.github.dominaspl.convertapp.domain.annotation.UniqueContact;

import javax.validation.constraints.Size;

public class ContactDTO {

    @Size(min = 3, max = 255, message = "Contact length must be greater than 2 and less than 256 characters")
    @UniqueContact
    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
