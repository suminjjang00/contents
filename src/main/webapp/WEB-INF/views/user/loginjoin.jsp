<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#check").on("click",function(){
		var uid = $("#uid").val();
		console.log(uid);
		$.ajax({
			type : 'post',
			url: "/user/logincheck",
			headers: {
				"Content-Type" : "application/json",
				"X-HTTP-method-Override" : "POST"
			},
			dataType : "text",
			data : JSON.stringify({
					uid : uid				
			}),
			success : function(result){
				if(result="SUCCESS"){
					alert("id 중복");						
				}else if(result="FAIL"){
					alert("사용 가능한 아이디 입니다")
				}
			}
			
		})
	})
	
	
	$("#submit").on("click",function(){
	var upd = $("#upd").val();
	var upd2= $("#upd2").val();
	if(!upd.trim(upd2)){
		alert("password not");
		return false;
	}
	})
})
</script>
<body>
		check your id<input type="text" id="uid">
		<button id="check">Check you'r ID</button>
	<form action="loginjoin" method="post">
		sign your name<input type="text" id="uname"> password line<input
			type="text" password="upd"> password check<input type="text"
			password="upd2"> <input type="submit" id="submit">
	</form>
</body>
</html>