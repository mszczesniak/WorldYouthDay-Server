package pl.kielce.tu.worldyouthday.logs;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {

    private static final Log logger = LogFactory.getLog(LoggingAspect.class);

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void service() {
    }

    @Around("service()")
    public Object profile(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch sw = new StopWatch();
        String name = joinPoint.getSignature().toShortString();
        Object returnValue = null;
        logger.info(":::START:::Method Name :" + name + "| Args => " + Arrays.asList(joinPoint.getArgs()));
        try {
            sw.start();
            returnValue = joinPoint.proceed();
            return returnValue;
        } finally {
            sw.stop();
            logger.info(":::STOP:::Method Name :" + name + "| Return value => " + returnValue + "STOPWATCH:" + sw.getTime());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                logger.info(":::Principal::: " + authentication.getPrincipal());
            }
        }
    }
}
