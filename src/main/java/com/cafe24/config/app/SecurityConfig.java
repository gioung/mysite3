package com.cafe24.config.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.cafe24.mysite.security.CustomUrlAuthenticationSuccessHandler;

/*
 * 기본은 웹응답(Redirect, forwarding)이므로 api응답을 위해서는  Override해서(custom) 바꿀 수 있음.
Security Filter Chain

1. ChannelProcessingFilter
2. SecurityContextPersistenceFilter			( auto-config default, 필수 ) 
3. ConcurrentSessionFilter
4. LogoutFilter								( auto-config default, 필수 ) 
5. UsernamePasswordAuthenticationFilter		( auto-config default, 필수 )
6. DefaultLoginPageGeneratingFilter			( auto-config default)
7. CasAuthenticationFilter
8. BasicAuthenticationFilter				( auto-config default, 필수 )
9. RequestCacheAwareFilter					( auto-config default )
10. SecurityContextHolderAwareRequestFilter	( auto-config default )
11. JaasApiIntegrationFilter
12. RememberMeAuthenticationFilter          
13. AnonymousAuthenticationFilter			( auto-config default )
14. SessionManagementFilter					( auto-config default )
15. ExceptionTranslationFilter				( auto-config default, 필수 )
16. FilterSecurityInterceptor				( auto-config default, 필수 )


*/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsService userDetailsService;

	
	public SecurityConfig() {
		System.out.println("SecurityConfig 생성");
	}
	// 스프링 시큐리티 필터 연결
	// WebSecurity 객체
	// springSecurityFilterChain 라는 이름의
	// DelegatingFilterProxy Bean 객체를 생성 
	// DelegatingFilterProxy Bean은 많은 Spring Security Filter Chain에 위임한다.
	@Override
	public void configure(WebSecurity web) throws Exception {
		// super.configure(web);
		// ACL(Access Control List)에 등록하지 않을 URL을 설정한다.
//		web.ignoring().antMatchers("/assets/**");
//		web.ignoring().antMatchers("/favicon.ico");
		web.ignoring().regexMatchers("\\A/assets/.*\\Z");
		web.ignoring().regexMatchers("\\A/favicon.ico\\Z");
	}
	//위 설정은 다음과 같다. 
//	Bean("springSecurityFilterChain")
//	public filterChainProxy() {
//		f = new DelegatingFilterProxy()
//	    f.setting 필터개수설정
//	}
	
	// Interceptor URL의 요청을 보호하는 방법을 설정(보안)
	/*
	 * - 인증과 권한 설정
	 deny all
	/user/update
	/user/logout  
	/board/xxx 의 경우 사용자, 관리자가 분리될 필요가 없다. (ex) 한줄평에 댓글등)
	/board/write 
	/board/delete
	/board/modify
	-> (ROLE_USER, ROLE_ADMIN) -> Authenticated 인증
	
	/admin/**      -> ROLE_ADMIN(Authorized)  인증 + 권한
	
	그 외
	all permitted  
	 * */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//			super.configure(http);  //이놈을 막으면 login 안뜸.
		
		// 1. ACL 설정
		http.authorizeRequests()
		//인증이 되어있을때(authenticated?) , 인증없이 접근하면 403 FORBIDDEN 뜸.
		.antMatchers("/user/update", "/user/logout").authenticated()
		.antMatchers("/board/write", "/board/delete", "/board/modify").authenticated()
		
		//ADMIN Authorization(ADMIN 권한, ROLE_ADMIN)
		//access 안은 SS(SpringSecurity)언어임.
//		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		//좀더 간결한 표현은 밑에
		.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
		.antMatchers("/gallery/upload", "/gallery/delete/**").hasAuthority("ROLE_ADMIN")
		//더 간결한 표현
//		.antMatchers("/admin/**").hasRole("ADMIN")
		
		//모두 허용
		//# 방법1
//		.antMatchers("/**").permitAll();
		//# 방법2
		.anyRequest().permitAll()
		
		//Temporary for Testing
//		http.csrf().disable();
		
		
		// 2. 로그인 설정
		.and()
		.formLogin()
		.loginPage("/user/login")
		.loginProcessingUrl("/user/auth")
		.failureUrl("/user/login?result=fail")
		//.defaultSuccessUrl("/", true) // authenticationSuccessHandler
		.successHandler(authenticationSuccessHandler())
		.usernameParameter("email")
		.passwordParameter("password")
		
		
		
		// 3. 로그아웃 설정
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true)     //JSession을 새로 발급하는가?
		.deleteCookies("JSESSIONID")
		.invalidateHttpSession(true)
		
		// 4. Access Denial Handler
		.and()
		.exceptionHandling()
		.accessDeniedPage("/WEB-INF/views/error/403.jsp")
		
		// 5. RememberMe
		.and()
		.rememberMe()
		.key("mysite3")
		.rememberMeParameter("remember-me");
	}
	
	
	
	// UserDetailService를 설정
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.and()
		.authenticationProvider(autenicationProvider());
		
	}
	
	// AuthenticationSuccessHandler 등록
	//login 성공했을때 success를 ajax와 web둘다 응답가능 하게 하기 위해
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomUrlAuthenticationSuccessHandler();
	}
	
	@Bean
	public AuthenticationProvider autenicationProvider() {
		DaoAuthenticationProvider authProvider = 
				new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
