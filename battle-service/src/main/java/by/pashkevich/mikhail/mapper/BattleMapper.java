package by.pashkevich.mikhail.mapper;

import by.pashkevich.mikhail.dto.BattleDto;
import by.pashkevich.mikhail.entity.Battle;
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
