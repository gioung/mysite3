<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="navigation">
		<ul>
			<li><a href="${pageContext.servletContext.contextPath}/">남기웅</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/guestbook">방명록</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/guestbook/spa">방명록(ajax)</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/gallery">갤러리</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/board/1">게시판</a></li>
		</ul>
	</div>

</body>
</html>