package by.pashkevich.mikhail.mapper;

import by.pashkevich.mikhail.dto.MoveDto;
import by.pashkevich.mikhail.entity.Step;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MoveMapper {
    @Mapping(target = "i", source = "i")
    @Mapping(target = "j", source = "j")
    Step toStep(MoveDto moveDto);
}
