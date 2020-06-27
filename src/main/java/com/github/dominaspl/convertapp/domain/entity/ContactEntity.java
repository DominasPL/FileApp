package com.github.dominaspl.convertapp.domain.entity;

import com.github.dominaspl.convertapp.domain.enumeration.ContactType;

public class ContactEntity {

    private Long id;

    private ContactType type;

    private String contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
