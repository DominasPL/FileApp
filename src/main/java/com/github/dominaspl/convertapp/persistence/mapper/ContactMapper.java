package com.github.dominaspl.convertapp.persistence.mapper;

import com.github.dominaspl.convertapp.domain.dto.ContactDTO;
import com.github.dominaspl.convertapp.domain.entity.ContactEntity;
import com.github.dominaspl.convertapp.domain.enumeration.AssertionErrorKey;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ContactMapper implements Mapper<ContactDTO, ContactEntity> {

    @Override
    public ContactEntity mapToEntity(ContactDTO contactDTO) {
        if (Objects.isNull(contactDTO)) {
            throw new AssertionError(AssertionErrorKey.PROVIDED_OBJECT_CANNOT_BE_NULL);
        }

        List<String> regexList = Arrays.asList(
                "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
                "^(\\d{3}[- .]?){2}\\d{3}$",
                "^(?:([^@/<>'\\\"]+)@)?([^@/<>'\\\"]+)(?:/([^<>'\\\"]*))?$"
        );
        ContactEntity contact = new ContactEntity();
        contact.setType(0);
        String contactType = contactDTO.getContact();
        Pattern pattern;
        Matcher matcher;
        for (int i = 0; i < regexList.size(); i++) {
            pattern = Pattern.compile(regexList.get(i));
            matcher = pattern.matcher(contactType);
            if (matcher.matches()) {
                contact.setType(i + 1);
                break;
            }
        }
        contact.setContact(contactType);
        return contact;
    }

}
