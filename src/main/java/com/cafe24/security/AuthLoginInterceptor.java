package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.repository.vo.UserVo;
import com.cafe24.mysite.service.UserService;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//외부에서 받는방법 (컨테이너 밖에서)
		/*ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		UserService userService = ac.getBean(UserService.class);
		*/
		UserVo userVo = new UserVo();
		userVo.setEmail(email);
		userVo.setPassword(password);
		UserVo authUser = userService.getUser(userVo);
		if(authUser == null) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		
		
		
		return true;
	}
	
}
