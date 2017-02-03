<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="../../../../resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript" src="/resources/js/upload.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
		$("#registerForm").submit(function(event){
			event.preventDefault();
			
			var that=$(this);
			var str="";
			
			$(".uploadedList .delbtn").each(function(index){
				str += "<input type='hidden' name='files["+index+"]' value='"+$(this).attr("href")+"'>";
			});
			that.append(str);
			
			that.get(0).submit();
					
		})

/* 파일 로드 jquery */
	var template =Handlebars.compile($("#template").html());
		$(".fileDrop").on("dragenter dragover",function(event){
			event.preventDefault();
		})
		$(".fileDrop").on("drop",function(event){
			var files=event.originalEvent.dataTransfer.files;
			var file=files[0];
			
			var formData = new FormData();
			
			formData.append("file",file);
			
			$.ajax({
				url: '/uploadAjax',
				data: formData,
				dataType:'text',
				processData:false,
				contentType:false,
				type:'POST',
				success:function(data){
					var fileInfo = getFileInfo(data);
					
					var html = template(fileInfo);
					$(".uploadedList").append(html);
				}
			});			
		});		
/* 게시판 글쓰기 업로드 jquery */
	$(document).ready(function() {
		var formObj = $("form[role='form']");
		console.log(formObj);
		$("#cancelboard").on("click", function() {
			self.location = "/sboard/list";
		});
		$("#submitboard").on("click", function() {
			formObj.submit();
		});
	});
});
</script>
<script id="template" type="text/x-handlebars-template">
<li>
	<span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}" alt="Attachment"></span>
	<div class="mailbox-attachment-info">
		<a href="{{getLink}}" class="mailbox-attachment-name">{{failName}}</a>
		<a href="{{fullName}}"
		   	class="btn btn-default btn-xs pull-right delbtn"><i class="fa fa-fw fa-remove"></i></a>
	</span>
	</div> 
</li>
</script>
<form role="form" method="post" id="registerForm">
	<div class="box-body">
		<div class="form-group">
			<label for="exampleInputEmail1">Title</label> <input type="text"
				name='title' class="form-control" placeholder="Enter Title">
		</div>
		<div class="form-group">
			<label for="exampleInputPassword1">Content</label>
			<textarea class="form-control" name="content" rows="3"
				placeholder="Enter..."></textarea>
		</div>
		<!-- 기존에 있는 로그인 처리화면임. 글쓴이를 따로 등록해서 text타입으로 form객체에 담아서 전송했다. -->
		<!-- <div class="form-group">
			<label for="exampleInputEmail1">Writer</label> <input type="text"
				name="writer" class="form-control" placeholder="Enter Writer">
		</div> -->
		<div class="form-group">
			<label for="exampleInputEmail1">Writer</label> <input type="text"
				name="writer" class="form-control" value='${login.uid}' readonly>
		</div>
		<!-- 파일 등록 부분  -->
		<div class="form-group">
			<label for="exampleInputEmail1">File DROP HERE</label>
			<div class="fileDrop">fileDrop클래스 디브로 감싼곳.</div>
		</div>
		<!-- /.box-body -->
		<div class="box-footer">
			<div>
				<hr>디브로 감싼 hr 태그 안쪽임.</hr>
			</div>
			<ul class="mailbox-attachments clearfix uploadedList"></ul>
			<button type="submit" class="btn btn-primary" id="registerForm">Submit</button>
		</div>

		<!-- /.box body -->
</form>
<div class="box-footer">
	<button type="button" class="btn btn-primary" id="submitboard">Submit</button>
	<button type="button" class="btn btn-warning" id="cancelboard">CanCel</button>
</div>
<%@ include file="../include/footer.jsp"%>