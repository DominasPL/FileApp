package com.github.dominaspl.convertapp.persistence.mapper.impl;

import com.github.dominaspl.convertapp.domain.dto.ContactDTO;
import com.github.dominaspl.convertapp.domain.entity.ContactEntity;
import com.github.dominaspl.convertapp.domain.enumeration.AssertionErrorKey;
import com.github.dominaspl.convertapp.persistence.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class ContactMapper implements Mapper<ContactDTO, ContactEntity> {

    @Override
    public ContactEntity mapToEntity(ContactDTO contactDTO) {
        if (Objects.isNull(contactDTO) || Objects.isNull(contactDTO.getContact())) {
            throw new AssertionError(AssertionErrorKey.PROVIDED_OBJECT_CANNOT_BE_NULL);
        }

        List<String> regexList = Arrays.asList(
                "[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.([a-zA-Z]{2,}){1}",
                "^(\\d{3}[- .]?){2}\\d{3}$",
                "^jbr:.*"
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

    @Override
    public List<ContactEntity> mapToEntities(List<ContactDTO> contactDTOList) {
        if (Objects.isNull(contactDTOList)) {
            throw new AssertionError(AssertionErrorKey.PROVIDED_OBJECT_CANNOT_BE_NULL);
        }
        return contactDTOList.stream().map(c -> mapToEntity(c)).collect(Collectors.toList());
    }

}
