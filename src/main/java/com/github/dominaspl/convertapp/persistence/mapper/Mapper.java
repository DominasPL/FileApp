package com.github.dominaspl.convertapp.persistence.mapper;

public interface Mapper <D, T> {
    T mapToEntity(D dto);
}
