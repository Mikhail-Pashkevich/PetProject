package by.pashkevich.mikhail.service.mapper;

import by.pashkevich.mikhail.model.dto.BattleDto;
import by.pashkevich.mikhail.model.entity.Battle;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = BattleMapper.class)
public interface BattleListMapper {
    List<BattleDto> toBattleDtoList(List<Battle> battleList);
}
