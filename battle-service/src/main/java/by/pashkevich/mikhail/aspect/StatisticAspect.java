package by.pashkevich.mikhail.aspect;

import by.pashkevich.mikhail.annotation.Statistic;
import by.pashkevich.mikhail.client.StatisticClient;
import by.pashkevich.mikhail.dto.StatisticDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class StatisticAspect {

    private final StatisticClient statisticClient;

    @Pointcut("@annotation(by.pashkevich.mikhail.annotation.Statistic)")
    public void annotationStatisticPointCut() {
    }

    @Around("annotationStatisticPointCut()")
    public Object saveStatisticAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Statistic annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Statistic.class);
        String annotationMessage = annotation.message();
        Object[] args = joinPoint.getArgs();
        Object result = joinPoint.proceed();
        StatisticDto statisticDto = new StatisticDto(annotationMessage, args, result);
        statisticClient.save(statisticDto);
        return result;
    }

    @AfterThrowing(pointcut = "annotationStatisticPointCut()", throwing = "e")
    public void saveStatisticAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Statistic annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Statistic.class);
        String annotationMessage = annotation.message();
        Object[] args = joinPoint.getArgs();
        String exceptionMessage = e.getMessage();
        StatisticDto statisticDto = new StatisticDto(annotationMessage, args, exceptionMessage);
        statisticClient.save(statisticDto);
    }
}
