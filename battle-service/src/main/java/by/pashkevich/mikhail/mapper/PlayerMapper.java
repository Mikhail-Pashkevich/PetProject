package by.pashkevich.mikhail.mapper;

import by.pashkevich.mikhail.dto.PlayerDto;
import by.pashkevich.mikhail.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerDto toPlayerDto(User user);
}
