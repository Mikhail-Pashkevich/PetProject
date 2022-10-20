package by.pashkevich.mikhail.service.mapper;

import by.pashkevich.mikhail.model.dto.FieldDto;
import by.pashkevich.mikhail.model.entity.Field;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    Field toField(FieldDto fieldDto);

    FieldDto toFieldDto(Field field);
}
