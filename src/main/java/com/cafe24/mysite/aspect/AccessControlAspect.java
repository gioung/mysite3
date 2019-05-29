package com.cafe24.mysite.aspect;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*@Aspect
@Component
public class AccessControlAspect{
	
	@Around(value="execution(* com.cafe24.mysite.controller.BoardController.*(..)) || "
			+ "execution(* com.cafe24.mysite.controller.GuestbookController.*(..))")
	public Object accessControl(ProceedingJoinPoint pjp) throws Throwable {
		HttpSession session = null;
		
		for(Object o:pjp.getArgs()) {
			if(o instanceof HttpSession) {
				session = (HttpSession)o;
				//System.out.println("세션존재");
			}
		}
		

		if(session.getAttribute("authUser")==null || session==null) {
				return "redirect:/nologin";
		}
		Object returnObj=pjp.proceed();
		
		//System.out.println("After");
		return returnObj;
	}
}*/
