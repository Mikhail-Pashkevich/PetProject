package by.pashkevich.mikhail.aspect;

import by.pashkevich.mikhail.client.StatisticClient;
import by.pashkevich.mikhail.dto.StatisticDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class StatisticAspect {

    private final StatisticClient statisticClient;

    @Pointcut("execution(public * by.pashkevich.mikhail.repository.BattleRepository.save(*))")
    public void executionBattleRepositorySavePointCut() {
    }

    @Around("executionBattleRepositorySavePointCut()")
    public Object saveStatisticAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object result = joinPoint.proceed();
        StatisticDto statisticDto = new StatisticDto(args[0]);
        statisticClient.save(statisticDto);
        return result;
    }
}
