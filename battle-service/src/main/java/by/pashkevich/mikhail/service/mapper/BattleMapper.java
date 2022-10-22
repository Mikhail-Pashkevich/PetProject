package by.pashkevich.mikhail.service.mapper;

import by.pashkevich.mikhail.model.dto.BattleDto;
import by.pashkevich.mikhail.model.entity.Battle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FieldMapper.class, PlayerMapper.class})
public interface BattleMapper {
    @Mapping(target = "fieldDto", source = "field")
    @Mapping(target = "playerDtoX", source = "playerX")
    @Mapping(target = "playerDtoO", source = "playerO")
    BattleDto toBattleDto(Battle battle);

    List<BattleDto> toBattleDtoList(List<Battle> battleList);
}
