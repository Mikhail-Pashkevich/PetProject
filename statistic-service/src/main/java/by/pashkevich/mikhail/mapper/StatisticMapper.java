package by.pashkevich.mikhail.mapper;

import by.pashkevich.mikhail.dto.StatisticDto;
import by.pashkevich.mikhail.entity.Statistic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatisticMapper {

    Statistic toEntity(StatisticDto dto);
}
