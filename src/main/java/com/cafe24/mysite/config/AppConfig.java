package com.cafe24.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.cafe24.config.app.DBConfig;
import com.cafe24.config.app.MyBatisConfig;
import com.cafe24.config.app.SecurityConfig2;
import com.cafe24.config.web.FileuploadConfig;


@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cafe24.mysite.repository","com.cafe24.mysite.service","com.cafe24.mysite.aspect","com.cafe24.mysite.security"})
@Import({DBConfig.class,MyBatisConfig.class,FileuploadConfig.class,SecurityConfig2.class})
public class AppConfig {
	
}
