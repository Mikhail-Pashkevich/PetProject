package by.pashkevich.mikhail.mapper;

import by.pashkevich.mikhail.dto.UserDto;
import by.pashkevich.mikhail.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {
    UserDto toDto(User user);
}
