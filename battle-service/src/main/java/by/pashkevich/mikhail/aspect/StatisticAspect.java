package by.pashkevich.mikhail.aspect;

import by.pashkevich.mikhail.config.KafkaConfig;
import by.pashkevich.mikhail.dto.StatisticDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class StatisticAspect {

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Pointcut("execution(public * by.pashkevich.mikhail.repository.BattleRepository.save(*))")
    public void executionBattleRepositorySavePointCut() {
    }

    @Around("executionBattleRepositorySavePointCut()")
    public Object saveStatisticAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        StatisticDto statisticDto = new StatisticDto(result);
        kafkaTemplate.send(KafkaConfig.STATISTIC_TOPIC_NAME, statisticDto);
        return result;
    }
}
