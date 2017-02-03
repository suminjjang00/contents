<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../include/header.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
	var formObj=$("form[role='form']");
	console.log(formObj);
	$(".btn-warning").on("click",function(){
		/* alert("ddd"); */
		formObj.attr("action","/board/modify");
		formObj.attr("method","get");
		formObj.submit();
	});
	$(".btn-danger").on("click",function(){
		formObj.attr("action","/board/remove");		
		formObj.submit();
	});
	$(".btn-primary").on("click",function(){
		/* self.location='/board/listAll'; */
		history.back();
	});
	
});
</script>
<form role="form" method="post">
	<input type='hidden' name='bno' value="${boardVo.bno }">
</form>
<div class="box-body">
	<div class="form-group">
		<label for="exampleInputEmail1">TiTle</label>
		<input type="text" name="title" class="form-control" value="${boardVo.title }"
		readonly="readonly">
	</div>
	<div class="form-group">
		<label for="exampleInputPassword1">Content</label>
		<textarea rows="3" class="form-control" name="content" readonly="readonly" >${boardVo.content }</textarea>
	</div>
	<div class="form-group">
		<label for="exampleInputEmail1">Writer</label>
		<input type="text" name="writer" class="form-control" value="${boardVo.writer }"
		readonly="readonly">
	</div>	
</div>
<!-- /.box-body -->
<div class="box-footer">
	<button type="submit" class='btn btn-warning'>Modify</button>
	<button type="submit" class="btn btn-danger">ReMOVE</button>
	<button type="submit" class='btn btn-primary'>LIST ALL</button>
</div>
<%@ include file="../include/footer.jsp" %>