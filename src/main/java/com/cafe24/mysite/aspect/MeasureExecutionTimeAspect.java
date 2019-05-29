package com.cafe24.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/*@Aspect
@Component
public class MeasureExecutionTimeAspect {
	
	@Around(value="execution(* *..repository.*.*(..)) || execution(* *..service.*.*(..)) || execution(* *..controller.*.*(..))")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		//before
		StopWatch sw = new StopWatch();
		sw.start();
		//method 실행
		Object result = pjp.proceed();
		//after
		sw.stop();
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName(); //signature 는 method랑 같은 의미
		String taskName = className+"."+methodName;
		System.out.println(taskName+"=>"+sw.getTotalTimeMillis());
		
		return result;
	}
}*/
