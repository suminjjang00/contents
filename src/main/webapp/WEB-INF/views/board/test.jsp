<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<style>
	#modDiv {
		width:300px;
		height:100px;
		background-color:gray;
		position: absolute;
		top:50%;
		left:50%;
		margin-top: -50px;
		margin-left: -150px;
		padding: 10px;
		z-index:1000;
		display: none;
	}

</style>
<script src="../../resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript">
window.onload= function(){
		var bno=8219;
		
		getPageList(1);
	
	function getAllList(){
		/* var bno=$("#bno").val(); */
		
		var str=null;		
		/*아래와 같은 형태로 JSON객체를 받아옴. 받아온 JSON인자값은 data라는 변수로 명명하고 
		$(data).each(function(){ 이러한 형태로 출력을 함. each 는 for문과 비슷하다. })*/
	$.getJSON("/replies/all/" + bno, function(data) {
		
		console.log(data.length);
		
		$("#replies").html(str);
		
		str = "";
		$(data).each(
				function() {
					str += "<li data-rno='"+this.rno+"' class='replyLi'>"
							+ this.rno + ":" + this.replytext
							+"<button>MOD</button></li>";
				});

		$("#replies").html(str);
	});
	};
	

	$("#testbtn").on("click",function(){
		getAllList();
	})
	$("#replyAddBtn").on("click",function(){
		/* var bno=$("#bno").val(); */
		var replyer=$("#newReplyWriter").val();
		var replytext=$("#newReplyText").val();
		$.ajax({
			type : 'post',
			url : '/replies',
			headers :{
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "POST"
			},
			dataType : 'text',
			data : JSON.stringify({
				bno : bno,
				replyer : replyer,
				replytext : replytext
			}),
			/*function(result)의 result인자 값은 위에있는 /replies로 text type, data = bno, replyer, 
			replytext 의 데이터가 들어간 후 반환되는 값임. 컨트롤러의 url요청 경로를 확인할 경우 SUCCESS 를
			반환되는 것이 확인 가능하다.*/
			success : function(result){
				if(result == 'SUCCESS'){
				
					alert("등록 완료");
					getAllList();
				}
			}
		});		
	});
	$("#replies").on("click", ".replyLi button", function(){
		
		var reply = $(this).parent();
		
		var rno=reply.attr("data-rno");
		var replytext = reply.text();
		
		$(".modal-title").html(rno);
		$("#replytext").val(replytext);
		$("#modDiv").show("slow");
		
	})
	$("#replyDelBtn").on("click",function(){
		var rno=$(".modal-title").html();
		var replytext= $("#replytext").val();
		
		$.ajax({
			type : 'delete',
			url : '/replies/'+rno,
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "DELETE"
			},
			dataType : 'text',
			success : function(result){
				if(result='SUCCESS'){
					alert("삭제 되었습니다.")
					$("#modDiv").hide("slow");
					getPageList(replyPage);
				}
			}
		})
	})
	$("#closeBtn").on("click",function(){
		$("#modDiv").hide();
	})
	$("#replyModBtn").on('click',function(){
		var rno=$(".modal-title").html();
		var replytext=$("#replytext").val();
		
		$.ajax({
			type : 'put',
			url: '/replies/'+rno,
			 headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "PUT"
			}, 
			dataType : 'text',
			data : JSON.stringify({
				replytext : replytext				
			}),
			success : function(result){
				if(result=='SUCCESS'){
					alert("수정 되었습니다");
					$("#modDiv").hide();
					/* getAllList(); */
					getPageList(replyPage);
				}
			}
			
		})
	})
	function getPageList(page){
		
		$.getJSON("/replies/" + bno + "/"+page,function(data){
			console.log(data.list.length);
			var str="";
			$(data.list).each(function(){
				str +="<li data-rno='"+this.rno+"' class='replyLi'>"
				+this.rno+":" + this.replytext + "<button>MOD</button></li>";
			});
			$("#replies").html(str);
			
			printPaging(data.pageMaker);
		})
	}
	function printPaging(pageMaker){
		var str="";
		
		if(pageMaker.prev){
			str +="<li><a href='"+(pageMaker.startPage-1)+"'> << </a></li>";
		};
		for(var i=pageMaker.startPage, len = pageMaker.endPage; i<=len; i++){
			var strClass=pageMaker.cri.page ==i?'class=active' : '';
			str+="<li "+strClass+"><a href='"+i+"'>"+i+"</a></li>";
		}
		$('.pagination').html(str);
	}
	var replyPage =1;
	$(".pagination").on("click","li a",function(event){
		event.preventDefault();
		
		replyPage = $(this).attr("href");
		
		getPageList(replyPage);
	})
	}
	
	
</script>
<body>
	<h2>Ajax test page</h2>
	<div>
		<div>
			게시글 번호 입력<input type="text" name="bno" id="bno">
		</div>
		<div>
			REPLYER<input type="text" name="replyer" id="newReplyWriter">
		</div>
		<div>
			REPLY TEXT<input type="text" name="replytext" id="newReplyText">
		</div>
		<button id="replyAddBtn">ADD REPLY</button>
		<button id="testbtn">testbtn alert</button>
	</div>
	
	<ul id="replies"></ul>
	
	<div id="modDiv">
		<div class='modal-title'></div>
		<div>
			<input type="text" id="replytext">
		</div>
		<div>
			<button type="button" id="replyModBtn">Modify</button>
			<button type="button" id="replyDelBtn">Delete</button>
			<button type="button" id="closeBtn">Close</button>
		</div>
	</div>
	<ul class="pagination"></ul>
		

</body>
</html>

