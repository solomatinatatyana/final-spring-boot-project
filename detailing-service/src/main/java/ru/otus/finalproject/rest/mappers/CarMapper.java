package ru.otus.finalproject.rest.mappers;


import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.rest.dto.CarDto;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderMapper.class, RequestMapper.class})
public interface CarMapper {

    @Mappings({
            @Mapping(source="car.orders",target = "orders", ignore=true),
            @Mapping(source="car.requests",target = "requests", ignore=true)
    })
    CarDto toCarDto(Car car);

    @InheritInverseConfiguration
    Car toCar(CarDto carDto);

    List<CarDto> toCarDtoList(List<Car> carList);
}
