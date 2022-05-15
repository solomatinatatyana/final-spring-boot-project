package ru.otus.finalproject.rest.mappers;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.otus.finalproject.domain.Order;
import ru.otus.finalproject.rest.dto.OrderDto;

@Mapper(componentModel = "spring",uses = {CarMapper.class, ProductMapper.class})
public interface OrderMapper {

    OrderMapper PEOPLE_MAPPER = Mappers.getMapper(OrderMapper.class);
    @Mapping(source="order.products",target = "products",ignore = true)
    OrderDto toOrderDto(Order order);

    @InheritConfiguration
    Order toOrder(OrderDto orderDto);
}
