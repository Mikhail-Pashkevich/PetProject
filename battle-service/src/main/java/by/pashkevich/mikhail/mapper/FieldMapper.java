package by.pashkevich.mikhail.mapper;

import by.pashkevich.mikhail.model.dto.FieldDto;
import by.pashkevich.mikhail.model.entity.Field;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    FieldDto toFieldDto(Field field);
}
