package ywoosang.springadmin.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
// @Component 로 컴포넌트스캔을 이용해 빈에 등록되게 할 수도 있지만
// 적용된 AOP 를 인지하기 위해 스프링 빈에 수동으로 등록해주는 것이 더 좋다.
@Component
public class TimeTraceAop {

    // 어느 단계에서 적용될 것인지 적어줘야 한다.
    // 패키지명아래의 모든

    @Around("execution(* ywoosang.springadmin..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: "+ joinPoint.toString());
        Object result;
        try {
            result = joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
        return result;
    }
}
