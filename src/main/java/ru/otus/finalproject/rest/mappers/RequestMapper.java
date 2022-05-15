package ru.otus.finalproject.rest.mappers;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.finalproject.domain.Request;
import ru.otus.finalproject.rest.dto.RequestDto;

@Mapper(componentModel = "spring",uses = {ProductMapper.class, CarMapper.class})
public interface RequestMapper {

    @Mapping(source="request.products",target = "products")
    RequestDto toRequestDto(Request request);

    @InheritConfiguration
    Request toRequest(RequestDto requestDto);
}
