package ru.otus.finalproject.rest.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.domain.User;
import ru.otus.finalproject.rest.dto.ProductDto;
import ru.otus.finalproject.rest.dto.UserDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @InheritInverseConfiguration
    User toUser(UserDto userDto);

    List<UserDto> toUserDtoList(List<User> userList);

    List<User> toUserList(List<UserDto> userDtoList);
}
