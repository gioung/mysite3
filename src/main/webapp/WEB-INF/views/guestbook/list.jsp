<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.cafe24.mysite.repository.GuestbookDao"%>
<%@page import="com.cafe24.mysite.repository.vo.GuestbookVo"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<% request.setAttribute("newline", "\n"); %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form action="${pageContext.servletContext.contextPath }/guestbook/add" method="post">
					<c:if test='${not empty authUser }'>
					<table>
						<tr>
							<td>이름 : </td><td><input type="text" name="name" value="${authUser.name }" readonly></td>
							<td>비밀번호</td><td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="contents" id="content"></textarea></td>
						</tr>
						
					
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
					</c:if>	
				</form>
				<ul>
					
			<c:forEach items='${list }' var='vo' varStatus='status'>
					<li>
						<table>
							<tr>
								<td>[${vo.no}]</td>
								<td>${vo.name}</td>
								<td>${vo.reg_date }</td>
							<c:if test='${not empty authUser and vo.name eq authUser.name}'>
								<td><a href="${pageContext.servletContext.contextPath }/guestbook/delete/${vo.no }">삭제</a></td>
							</c:if>
							</tr>
							<tr>
								<td colspan=4>
									${fn:replace(vo.contents,newline,"<br>") }
								</td>
							</tr>
						</table>
						<br>
					</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>