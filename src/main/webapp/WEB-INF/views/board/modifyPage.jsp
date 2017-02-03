<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="../../resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var formObj=$("form[role='form']");
		console.log(formObj);
		$(".btn-primary").on("click",function(){
			formObj.submit();
		});
		$(".btn-warning").on("click",function(){			
			self.location="/board/listPage?page=${cri.page}&perPageNum=${cri.perPageNum}";
		});
});
</script>
<body>
<%@include file="../include/header.jsp" %>
<form role="form" method="post">
	<input type="hidden" name="page" value="${cri.page }">
	<input type="hidden" name="perPageNum" value="${cri.perPageNum }">
	<div class="box-body">
		<div class="form-group">
			<label for="exampleInputEmail1">Bno</label>
			<input type="text" name="bno" class="form-control" value="${boardVo.bno }"
			readonly="readonly">
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">Title</label>
			<input type="text" name="title" class="form-control" value="${boardVo.title }">
		</div>
		<div class="form-group">
			<label for="examplePassword1">Content</label>
			<textarea rows="3" class="form-control" name="content" >${boardVo.content }</textarea>
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">Writer</label>
			<input type="text" name="writer" class="form-control" value="${boardVo.writer }"
			readonly="readonly">
		</div>
	</div>
	<!-- </.box-body -->
</form>
	<div class="box-footer">
		<button type="submit" class="btn btn-primary">SAVE</button>
		<button type="submit" class="btn btn-warning">CANCEL</button>
	</div>
	<%@include file="../include/footer.jsp" %>
</body>
</html>