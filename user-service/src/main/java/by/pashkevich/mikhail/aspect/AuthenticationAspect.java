package by.pashkevich.mikhail.aspect;

import by.pashkevich.mikhail.exception.AuthUserProvideException;
import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthenticationAspect {
    private final UserSecurityService userSecurityService;

    @Around(value = "@annotation(by.pashkevich.mikhail.aspect.AuthUserProvide)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        int userArgIndex = -1;

        for (int i = 0; i < args.length; i++) {
            if (args[i].getClass().equals(User.class)) {
                userArgIndex = i;
            }
        }

        if (userArgIndex < 0) {
            throw new AuthUserProvideException(joinPoint.getSignature().getName(),
                    joinPoint.getSignature().getDeclaringTypeName());
        }

        args[userArgIndex] = userSecurityService.getAuthenticatedUser();

        return joinPoint.proceed(args);
    }
}
