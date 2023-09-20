package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.dto.StatisticDto;
import by.pashkevich.mikhail.entity.Statistic;
import by.pashkevich.mikhail.mapper.StatisticMapper;
import by.pashkevich.mikhail.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    private final StatisticMapper statisticMapper;

    @PostMapping
    public void save(@RequestBody StatisticDto dto) {
        Statistic statistic = statisticMapper.toEntity(dto);
        statisticService.save(statistic);
    }
}
