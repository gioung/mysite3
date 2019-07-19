package com.cafe24.config.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.ExpressionBasedFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.cafe24.mysite.security.CustomUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig2 extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	//filter도 test가 된다.
	@Bean(name="springSecurityFilterChain")
	public FilterChainProxy filterChainProxy() throws Exception {
		List<SecurityFilterChain> filterChains = new ArrayList<SecurityFilterChain>();
		filterChains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/assets/**")));
		filterChains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/favicon.ico")));
		filterChains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/**"),
		/*Filter Chains*/
				securityContextPersistenceFilter(),
				logoutFilter(),
				usernamePasswordAuthenticationFilter(),
				anonymousAuthenticationFilter(),
				exceptionTranslationFilter(),
				filterSecurityInterceptor()
				
		));
		return new FilterChainProxy(filterChains);
	}
	
	/**
	 *  1.SecurityContextPersistenceFilter
	 * 	
	 * 	SecurityContext 관리하는 필터
	 * 
	 *  */
	@Bean
	public SecurityContextPersistenceFilter securityContextPersistenceFilter() {
		return new SecurityContextPersistenceFilter(new HttpSessionSecurityContextRepository()); 
	}
	
	/**
	 *  2. LogoutFilter
	 *   
	 *  logout url을 감시한다.
	 *  CustomLogOutSuccessHandler(로그아웃이 성공하면 WEB에서는 메인화면이지만, API인 경우에는 JSON으로 응답해야하기 때문에..)
	 *  
	 *  
	 */
	@Bean
	public LogoutFilter logoutFilter() throws ServletException {
		CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler("JSESSIONID");
		SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
		securityContextLogoutHandler.setInvalidateHttpSession(true);
		securityContextLogoutHandler.setClearAuthentication(true);
		
		LogoutFilter logoutFilter = new LogoutFilter("/", cookieClearingLogoutHandler);
				logoutFilter.setFilterProcessesUrl("/user/logout");
				logoutFilter.afterPropertiesSet();
		
		return logoutFilter;
	}
	
	
	
	/**
	 *  3.UsernamePasswordAuthenticationFilter 
	 *   
	 *  인증(Authentication) 처리
	 *  
	 * 
	 */
	@Bean
	public AbstractAuthenticationProcessingFilter usernamePasswordAuthenticationFilter() {
		UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter =
				new UsernamePasswordAuthenticationFilter();
		
		usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
		usernamePasswordAuthenticationFilter.setUsernameParameter("email");
		usernamePasswordAuthenticationFilter.setPasswordParameter("password");
		usernamePasswordAuthenticationFilter.setFilterProcessesUrl("/user/auth");
		usernamePasswordAuthenticationFilter.setAllowSessionCreation(true);
		usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		usernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/user/login?result=fail"));
		usernamePasswordAuthenticationFilter.afterPropertiesSet();
		
		return usernamePasswordAuthenticationFilter;
	}
	
	/**
	 *  4.AnonymousProcessingFilter 
	 *   
	 *  로그인 하지 않은 사용자들을 감시, 
	 *  !isAuthentocated()에 대한 처리
	 *  
	 * 
	 */
	@Bean
	public AnonymousAuthenticationFilter anonymousAuthenticationFilter() {
		return new AnonymousAuthenticationFilter("1373046620"); //JSessionID 
	}
	
	/**
	 *  5.ExceptionTranslationFilter 
	 *   
	 *  인증 또는 권한이 없는 접근을 Exception을 발생시킨다.
	 *  예외에 대한 처리 필터
	 */
	
	@Bean
	public ExceptionTranslationFilter exceptionTranslationFilter() {
		ExceptionTranslationFilter exceptionTranslationFilter = 
				new ExceptionTranslationFilter(new LoginUrlAuthenticationEntryPoint("/user/login") );
		//유저 권한이 관리자 권한이 필요한 페이지로 가려고 할때, 403을 던져주는 코드
		AccessDeniedHandlerImpl accessDeniedHandlerImpl = 
				new AccessDeniedHandlerImpl();
		accessDeniedHandlerImpl.setErrorPage("/WEB-INF/views/error/403.jsp");
		exceptionTranslationFilter.setAccessDeniedHandler(accessDeniedHandlerImpl);
		exceptionTranslationFilter.afterPropertiesSet();
		
		return exceptionTranslationFilter;
	}
	
	/**
	 *  6.FilterSecurityInterceptor 
	 * 	
	 * 	Interceptor URL 접근 제어(ACL)
	 * 
	 *  */
	@Bean
	public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
		FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
		filterSecurityInterceptor.setAuthenticationManager(authenticationManager());
		filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>  requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		requestMap.put(new AntPathRequestMatcher("/user/update"), SecurityConfig.createList("isAuthenticated()"));
		requestMap.put(new AntPathRequestMatcher("/user/logout"), SecurityConfig.createList("isAuthenticated()"));
		requestMap.put(new AntPathRequestMatcher("/board/write"), SecurityConfig.createList("isAuthenticated()"));
		requestMap.put(new AntPathRequestMatcher("/board/delete"), SecurityConfig.createList("isAuthenticated()"));
		requestMap.put(new AntPathRequestMatcher("/board/modify"), SecurityConfig.createList("isAuthenticated()"));
		requestMap.put(new AntPathRequestMatcher("/admin**"), SecurityConfig.createList("hasRole('ADMIN')"));
		requestMap.put(new AntPathRequestMatcher("/gallery/upload"), SecurityConfig.createList("hasRole('ADMIN')"));
		requestMap.put(new AntPathRequestMatcher("/gallery/delete/**"), SecurityConfig.createList("hasRole('ADMIN')"));
//		requestMap.put(new AntPathRequestMatcher("/**"), SecurityConfig.createList("permitAll"));
		
		FilterInvocationSecurityMetadataSource newSource = 
				new ExpressionBasedFilterInvocationSecurityMetadataSource(requestMap, new DefaultWebSecurityExpressionHandler());

		filterSecurityInterceptor.setSecurityMetadataSource(newSource);
//		filterSecurityInterceptor.afterPropertiesSet();
		return filterSecurityInterceptor;
	}
	
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomUrlAuthenticationSuccessHandler();
	}
		
	@Bean
	public AuthenticationManager authenticationManager() {
		AuthenticationManager authenticationManager = 
				new ProviderManager(Arrays.asList(autenicationProvider()));
		return authenticationManager;
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
	
	@Bean
	public AffirmativeBased accessDecisionManager() throws Exception {
		RoleVoter rolevoter = new RoleVoter();
		rolevoter.setRolePrefix("ROLE_");
		
		AffirmativeBased affirmativeBased = 
				new AffirmativeBased(Arrays.asList(rolevoter, new WebExpressionVoter(), new AuthenticatedVoter())); 
		affirmativeBased.setAllowIfAllAbstainDecisions(false);
		affirmativeBased.afterPropertiesSet();
		
		return affirmativeBased;
	}
}