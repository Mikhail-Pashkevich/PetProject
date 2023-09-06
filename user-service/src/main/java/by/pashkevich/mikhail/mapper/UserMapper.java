package by.pashkevich.mikhail.mapper;

import by.pashkevich.mikhail.dto.UserDto;
import by.pashkevich.mikhail.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);

    UserDto toDto(User user);
}
