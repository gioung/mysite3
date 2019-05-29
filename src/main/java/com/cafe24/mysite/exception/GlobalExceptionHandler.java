package com.cafe24.mysite.exception;
//부모가 없는class는 POJO

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cafe24.mysite.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log LOGGER = LogFactory.getLog(GlobalExceptionHandler.class);
	//시스템 외적인 것이므로 기술침투는 어느정도 허용 (Controller , model ,view)외적인것.
	@ExceptionHandler(Exception.class)
	public void handlerException(HttpServletRequest request,
			HttpServletResponse response , Exception e) throws ServletException, IOException {
		
			//1.logging
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LOGGER.error(errors.toString());
			
		String accept = request.getHeader("accept");
		
		if(accept.matches(".*application/json.*")) {
			//json응답
			response.setStatus(HttpServletResponse.SC_OK);
			JSONResult result
			= JSONResult.fail(errors.toString());
			String resultnew=new ObjectMapper().writeValueAsString(result);
			System.out.println(resultnew);
			OutputStream os = response.getOutputStream();
			os.write(resultnew.getBytes("utf-8"));
			os.flush();
			os.close();
			
			
		}
		else {
			//2.안내페이지 가기 + 정상종료(response)
			
			request.setAttribute("uri", request.getRequestURI());
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);	
		}
		
		
		
	
	}
	
	
}
