package by.pashkevich.mikhail.listener;

import by.pashkevich.mikhail.config.KafkaConfig;
import by.pashkevich.mikhail.dto.StatisticDto;
import by.pashkevich.mikhail.entity.Statistic;
import by.pashkevich.mikhail.mapper.StatisticMapper;
import by.pashkevich.mikhail.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(id = "statisticGroup", topics = KafkaConfig.STATISTIC_TOPIC_NAME)
@RequiredArgsConstructor
public class StatisticKafkaListener {

    private final StatisticService statisticService;

    private final StatisticMapper statisticMapper;

    @KafkaHandler
    public void save(StatisticDto statisticDto) {
        Statistic statistic = statisticMapper.toEntity(statisticDto);
        statisticService.save(statistic);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        log.warn("Received unknown: " + object);
    }
}
