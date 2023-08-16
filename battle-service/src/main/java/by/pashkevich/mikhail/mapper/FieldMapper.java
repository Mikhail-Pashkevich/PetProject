package by.pashkevich.mikhail.mapper;

import by.pashkevich.mikhail.dto.FieldDto;
import by.pashkevich.mikhail.entity.Field;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    FieldDto toFieldDto(Field field);
}
