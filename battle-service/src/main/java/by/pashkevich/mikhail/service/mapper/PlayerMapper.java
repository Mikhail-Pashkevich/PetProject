package by.pashkevich.mikhail.service.mapper;

import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.dto.PlayerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerDto toPlayerDto(User user);
}
