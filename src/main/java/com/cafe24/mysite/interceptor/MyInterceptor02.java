package com.cafe24.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyInterceptor02 extends HandlerInterceptorAdapter {

	//Object 는 HandlerMapping에서 가져온 해당 uri에 대한 hm 객체 , DefaultHandler , 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("MyInterceptor02.preHandle");
		return true;
		
	}

}
