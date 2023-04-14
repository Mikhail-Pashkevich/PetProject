package by.pashkevich.mikhail.mapper;

import by.pashkevich.mikhail.model.dto.UserDto;
import by.pashkevich.mikhail.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {
    UserDto toDto(User user);
}
