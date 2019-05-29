<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="search" method="post">
					<input type="text" id="kwd" name="kwd" value="%">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					
					<c:forEach items='${list }' var='vo' varStatus='status' >			
					<tr>
						<td>${vo.no }</td>
						<td style="text-align:left; padding-left:${(vo.depth+1)*10 }px">
						<c:if test="${vo.depth ne 0}">
						<img src="${pageContext.servletContext.contextPath }/assets/reply.png">
						</c:if>
						<a href="${pageContext.servletContext.contextPath }/board/view/${vo.no }">${vo.title }</a></td>
						<td>${vo.name }</td>
						<td>${vo.hit }</td>
						<td>${vo.reg_date }</td>
						<c:if test="${not empty authUser and vo.user_no eq authUser.no }">
						<td><a href="${pageContext.servletContext.contextPath }/board/delete/${vo.no }" class="del">삭제</a></td>
						</c:if>
					</tr>
					</c:forEach>	
					
				</table>
					<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${pageNo-1 >= 1}">
						<li><a href="${pageContext.servletContext.contextPath }/board/1">◀</a></li>
						</c:if>
						<c:if test="${pageNo-2 >= 1}">
						<li><a href="${pageContext.servletContext.contextPath }/board/${pageNo-2 }">${pageNo-2 }</a></li>
						</c:if>
						<c:if test="${pageNo-1 >= 1}">
						<li><a href="${pageContext.servletContext.contextPath }/board/${pageNo-1 }">${pageNo-1 }</a></li>
						</c:if>
						<li class="selected">${pageNo }</li>
						<c:if test="${pageNo+1<=pageCount }">
						<li><a href="${pageContext.servletContext.contextPath }/board/${pageNo+1 }">${pageNo+1 }</a></li>
						</c:if>
						<c:if test="${pageNo+2<=pageCount }">
						<li><a href="${pageContext.servletContext.contextPath }/board/${pageNo+2 }">${pageNo+2 }</a></li>
						</c:if>
						<c:if test="${pageNo+1<=pageCount }">
						<li><a href="${pageContext.servletContext.contextPath }/board/${pageCount }">▶</a></li>
						</c:if>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<c:if test='${not empty authUser }'>
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath}/board/write" id="new-book">글쓰기</a>
				</div>		
				</c:if>
						
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>