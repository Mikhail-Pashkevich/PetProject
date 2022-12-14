package by.pashkevich.mikhail.service.mapper;

import by.pashkevich.mikhail.model.dto.MoveDto;
import by.pashkevich.mikhail.model.util.Step;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MoveMapper {
    @Mapping(target = "i", source = "i")
    @Mapping(target = "j", source = "j")
    Step toStep(MoveDto moveDto);
}
