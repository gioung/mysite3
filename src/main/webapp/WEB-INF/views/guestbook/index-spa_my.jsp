<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style type="text/css">
	#list-guestbook li{
		
	}
</style>
<script>
// jQuery PlugIn
</script>
<script>
var isEnd = false; // 강사 코드 참고

var render = function(vo, mode){
	// 실제로는 template library를 사용한다.
	// -> ejs, underscore, mustache, 강의때는 ejs를 배울것임
	// 정규표현식
	// var re =//;
	var html = "<li data-no='"+ vo.no +"'>" +
		"<strong>"+ vo.name +"</strong>" +
		"<p>"+ vo.contents.replace(/\n/gi, "<br>") + "</p>"+
		"<strong></strong>"+
		"<a href='#' data-no=''>삭제</a>"+ 
		"</li>";
	
	if(mode){
		$("#list-guestbook").prepend(html);	
	}
	else{
		$("#list-guestbook").append(html);	
	}
 	
 	
}

var fetchList = function(){
	if(isEnd){
		return;
	}
	
	var lastNo = $('#list-guestbook li').last().data('no');
	if(lastNo == null)
		lastNo = 0;
	
	$.ajax({
		url: "${pageContext.request.contextPath }/api/guestbook/list/" + lastNo,
		type: "get",
		contentType: "application/json", 
		dataType: "json",
		data: "",
		success: function(response){
			if(response.result != "success"){
				console.error(response.message);
				return;
			}
			
			// isEnd 검증
			if(response.data.length == 0){
				isEnd = true;
				$("#btn-next").prop("disabled",true);
				return;
			}
			
			
			// rendering
			$.each(response.data, function(index, vo){
				render(vo);
				
			});
			
			//다른 렌더링방법
			/* $.each(response.data, render); */
		},
		error: function(jqXHR, status, e){
			console.error(status + ":" + e);
		}});
}

$(function(){
	/* o = {
			email: "kickscar@gmail.com",
			password: "1234"
	}; */
	//jsp는 이게 java코드인질 모름. 다 똑같이 봄, 따라서 jstl먹힘
	//JSON , POST방식으로 Data를 보낼때 이용.
			//data: o.stringify() o는 객체이므로 이를 string으로 바꾸어 주기위해 씀.
  var dialogDelete = $( "#dialog-delete-form" ).dialog({
      autoOpen: false,
      height: 400,
      width: 350,
      modal: true,
      buttons: {
        "삭제": function(){
        	console.log("go delete");
        },
        
        "취소": function() {
        	dialogDelete.dialog( "close" );
        }
      },
      close: function() {
    	 // $( "#dialog-delete-form" )[0].reset();
        allFields.removeClass( "ui-state-error" );
      }
    });
	
	$("#btn-next").click(function(){
		fetchList(); 
	});
	
	$(window).scroll(function(){
		var $window = $(this);
		var scrollTop = $window.scrollTop();
		var windowHeight = $window.height();
		var documentHeight = $(document).height();
		if( scrollTop + windowHeight + 10 > documentHeight ){
			fetchList();
		}
	});
	
	
	$("#add-form").submit(function(event){
		//submit event 기본동작을 막음
		//posting을 막음
		event.preventDefault();
		
		var vo = {};
		
		// validation (client side, UX, jQuery Validation Plug-in)
		// 생략
		vo.name = $("#input-name").val(); //값을 가져올땐 .val();
		vo.password = $("#input-password").val();
		vo.contents = $("#tx-content").val();
		
		//console.log($.param(vo)); //jquery제공
		//console.log(JSON.stringify(vo)); //js 자체제공
		$.ajax({
			url: "${pageContext.request.contextPath }/api/guestbook/add",
			type: "post",
			contentType: "application/json", 
			dataType: "json",
			data: JSON.stringify(vo),
			success: function(response){
				
				if(response.result != "success"){
					console.error(response.message);
					return;
				}
				
				//rendering
				render(response.data, true);
				
				//reset form
				$("#add-form")[0].reset(); //reset은 vanila js가 지원
			},
				
			error: function(jqXHR, status, e){
				console.error(status + ":" + e);
			}});
		
	});
	
	//Live Event => delegation 방식
	$(document).on('click',"#guestbook ul li a",function(event){
		/* event.preventDefault(); */
		dialogDelete.dialog('open');
	});
	//최초 리스트 가져오기
	fetchList();
	
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" name="name" placeholder="이름">
					<input type="password" id="input-password" name="password" placeholder="비밀번호">
					<textarea name="contents" id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input id="input-submit" type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">

					
									
				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<button id="btn-next">Next Page</button>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>