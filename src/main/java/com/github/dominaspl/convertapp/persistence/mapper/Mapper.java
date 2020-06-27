package com.github.dominaspl.convertapp.persistence.mapper;

import java.util.List;

public interface Mapper <D, T> {
    T mapToEntity(D dto);

    List<T> mapToEntities(List<D> dtos);
}
