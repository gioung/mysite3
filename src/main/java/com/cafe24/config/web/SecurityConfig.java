package com.cafe24.config.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.cafe24.security.AuthInterceptor;
import com.cafe24.security.AuthLogoutInterceptor;

//Spring Security로 대체
//@EnableWebMvc
//@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter{

	//
	// Argument Resolver
	//
//	
//	
//
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(authLoginInterceotor()).addPathPatterns("/user/auth");
//		
//		registry.addInterceptor(authLogoutInterceotor()).addPathPatterns("/user/logout");
//		
//		registry
//        .addInterceptor(authInterceptor())
//        .addPathPatterns("**")
//        .excludePathPatterns("/assets/**")
//        .excludePathPatterns("/user/logout")
//        .excludePathPatterns("/user/auth");
//		
//	}
//	@Bean
//	public AuthInterceptor authInterceptor() {
//		 return new AuthInterceptor();
//	}
//	@Bean
//	public AuthLoginInterceptor authLoginInterceotor() {
//		return new AuthLoginInterceptor();
//		
//	}
//	@Bean
//	public AuthLogoutInterceptor authLogoutInterceotor() {
//		return new AuthLogoutInterceptor();
//		
//	}
//	
//	
//	
//	
	
}
