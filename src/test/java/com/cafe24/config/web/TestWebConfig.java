package com.cafe24.config.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cafe24.config.web.MVCConfig;
import com.cafe24.config.web.SecurityConfig;
import com.cafe24.mysite.config.SwaggerConfig;

@Configuration
@EnableWebMvc
@ComponentScan({"com.cafe24.mysite.controller", "com.cafe24.mysite.exception", "com.cafe24.mysite.controller.api", "com.cafe24.fileupload.controller"})
@Import({MVCConfig.class,SecurityConfig.class,SwaggerConfig.class, TestMVCConfig.class})
public class TestWebConfig extends WebMvcConfigurerAdapter{

  
   
   @Override
   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		/* configurer.enable(); */
   }
   
 //static 리소스 처리
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/resources/**")
               .addResourceLocations("/WEB-INF/resources/");

       registry.addResourceHandler("swagger-ui.html")
               .addResourceLocations("classpath:/META-INF/resources/");

       registry.addResourceHandler("/webjars/**")
               .addResourceLocations("classpath:/META-INF/resources/webjars/");
   }
}