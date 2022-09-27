package by.pashkevich.mikhail.service.mapper;

import by.pashkevich.mikhail.model.dto.BattleDto;
import by.pashkevich.mikhail.model.entity.Battle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FieldMapper.class, PlayerMapper.class})
public interface BattleMapper {
    @Mapping(target = "playerX", source = "playerDtoX")
    @Mapping(target = "playerO", source = "playerDtoO")
    Battle toBattle(BattleDto battleDto);

    @Mapping(target = "playerDtoX", source = "playerX")
    @Mapping(target = "playerDtoO", source = "playerO")
    BattleDto toBattleDto(Battle battle);
}
