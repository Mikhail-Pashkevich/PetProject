package by.pashkevich.mikhail.service.mapper;

import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.dto.PlayerDto;
import by.pashkevich.mikhail.model.entity.Player;

public interface PlayerMapper {
    Player toPlayer(User user);

    Player toPlayer(PlayerDto playerDto);

    PlayerDto toPlayerDto(Player player);
}
