package com.cafe24.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.repository.vo.BoardVo;
import com.cafe24.mysite.repository.vo.UserVo;
import com.cafe24.mysite.service.BoardService;

public class AuthBoardAccessInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	BoardService boardservice;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		/*Map map = (Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		System.out.println(map);*/
		
		String url=request.getRequestURI();
		String[] tokens = url.split("/");
		
		long no = Long.parseLong(tokens[tokens.length-1]);
		
		 //게시물 번호에 해당하는 BoardVo 객체 가져오기
		BoardVo boardVo = boardservice.getBoard(no);
		
		//세션 유저 아이디와 작성자 아이디 비교
		HttpSession session = (HttpSession)request.getSession();
		if(session.getAttribute("authUser")==null) {
			response.sendRedirect(request.getContextPath()+"/board");		
			return false;
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		//세션의 유저아이디와 게시글의 유저넘버 비교 다르다면 board로 리다이렉트
		if(authUser.getNo()!=boardVo.getUser_no()) {
			System.out.println("허가받지 않은 사용자 글번호:"+no+"접근");
			response.sendRedirect(request.getContextPath()+"/board");		
			return false;
		}
		return true;
	}

}
