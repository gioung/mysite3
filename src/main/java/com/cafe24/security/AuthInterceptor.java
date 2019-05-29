package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.repository.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 1.handler 종류 확인 (HandlerMethod , DefaultHandler)
		// HandlerMethod가 아닐경우
		if(handler instanceof HandlerMethod == false) {
			
			return true;
		}
		
		//2.casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		/*//3. method에 @Auth 받아오기
		Auth auth=handlerMethod.getMethodAnnotation(Auth.class);
		
		//4. Method에 @Auth 없으면 
		//Class(Type)의 @Auth를 받아오기
		//if(auth==null) {
		//	return true;
		//}
		
		//5. 클래스 , 메소드에 @Auth가 안 붙어있는 경우
		if(auth==null) {
			return true;
		}*/
		
		//6. @Auth가 (class 또는 method 붙어 있기 때문에)
		//   인증 여부 체크
		HttpSession session = request.getSession();
		
		// 인증이 안되있을 경우
		if(session==null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			System.out.println("authUser == null");
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		//7. Role 가져오기
		// Auth.Role role = auth.role();
		
		//8. role이 Auth.Role.USER 라면,
		//   인증된 모든 사용자는 접근 가능
		/*
		if(role.equals(Auth.Role.USER) )
			return true;
		*/
		//9. Admin Role 권한 체크
		
		//authUser.getRole().equals("ADMIN");
		
		return true;
	}

}
