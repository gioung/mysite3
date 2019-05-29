package com.cafe24.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


import com.cafe24.config.web.MVCConfig;
import com.cafe24.config.web.SecurityConfig;

@Configuration
@EnableWebMvc
@ComponentScan({"com.cafe24.mysite.controller", "com.cafe24.mysite.exception", "com.cafe24.mysite.controller.api", "com.cafe24.fileupload.controller"})
@Import({MVCConfig.class,SecurityConfig.class})
public class WebConfig extends WebMvcConfigurerAdapter{

  
   
   @Override
   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
       configurer.enable();
   }
}