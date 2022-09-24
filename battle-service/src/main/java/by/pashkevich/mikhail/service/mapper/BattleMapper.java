package by.pashkevich.mikhail.service.mapper;

import by.pashkevich.mikhail.model.dto.BattleDto;
import by.pashkevich.mikhail.model.entity.Battle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BattleMapper {
    Battle toBattle(BattleDto battleDto);

    BattleDto toBattleDto(Battle battle);
}
