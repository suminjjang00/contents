<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../include/header.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script
	src="https://cndjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		/* alert("${boardVo.bno}")
		alert("${cri.page}")
		alert("${cri.perPageNum}") */
		var bno=${boardVO.bno};
		var replyPage=1;
		
		function getPage(pageInfo){
			$.getJSON(pageInfo,function(data){
				printData(data.list,$("#repliesDiv"),$('#template'));
				printPaging(data.pageMaker,$(".pagination"));
			});
		}
		var printPaging=function(pageMaker,target){
			var str="";
			if(pageMaker.prev){
				str += "<li><a href='"+(pageMaker.startPage-1)+"'> << </a></li>";
			}
			for(var i=pageMaker.startPage, len=pageMaker.endPage; i<=len; i++){
				var strClass=pageMaker.cri.page==i?'class=active':'';
				str += "<li "+strClass+"><a href='"+i+"'>"+i+"</a></li>";
			}
			if(pageMaker.next){
				str += "<li><a href='"+(pageMaker.endPage+1)+"'> >> </a></li>";
			}
			target.html(str);
		};
		
		var formObj = $("form[role='form']");
		console.log(formObj);
		$(".btn-warning").on("click", function() {
			/* alert("ddd"); */
			formObj.attr("action", "/board/modifyPage");
			formObj.attr("method", "get");
			formObj.submit();
		});
		$(".btn-danger").on("click", function() {
			formObj.attr("action", "/board/removePage");
			formObj.attr("method", "post")
			formObj.submit();
		});
		$(".btn-primary").on("click", function() {
			formObj.attr("method", "get")
			formObj.attr("action", "/board/listPage");
			formObj.submit();
		});

	});
</script>
<script id="template" type="text/x-handlebars-template">
{{#each .}}
<li class="replyLi" dadta-rno={{rno}}><i
	class="fa fa-comments bg-blue"></i>
	<div class="timeline-teim">
		<span class="time"> <i class="fa fa-clock-o"></i>{{prettifyDate
			regdate}}
		</span>
		<h3 class="timeline-header">
			<strong>{{rno}}</strong>-{{replyer}}
		</h3>
		<div class="timeline-footer">
			<a class="btn btn-primary btn-xs" data-toggle="modal"
				data-target="#modifyModal">Modify</a>
		</div>
	</div></li>
{{/each}}
</script>
<<script>

Handlebars.registerHelper("prettifyDate", function(timeValue){
	var dateObj=new Date(timeValue);
	var year=dateObj.getFullYear();
	var montht=dateObj.getMonth()+1;
	var date=dateObj.getDate();
	return year+"/"+month+"/"date;
});
var printData = function(replyArr, target,templateObject){
	var template = Handlebars.compile(templateObject.html());
	
	var html=template(replyArr);
	$(".replyLi").remove();
	target.after(html);
};
</script>

<form role="form">
	<input type="hidden" name="viewcnt" value="${boardVo.viewcnt+1} }">
	<input type="hidden" name="bno" value="${boardVo.bno}"> <input
		type="hidden" name="page" value="${cri.page }"> <input
		type="hidden" name="perPageNum" value="${cri.perPageNum }">
</form>
<div class="box-body">
	<div class="form-group">
		<label for="exampleInputEmail1">TiTle</label> <input type="text"
			name="title" class="form-control" value="${boardVo.title }"
			readonly="readonly">
	</div>
	<div class="form-group">
		<label for="exampleInputPassword1">Content</label>
		<textarea rows="3" class="form-control" name="content"
			readonly="readonly">${boardVo.content }</textarea>
	</div>
	<div class="form-group">
		<label for="exampleInputEmail1">Writer</label> <input type="text"
			name="writer" class="form-control" value="${boardVo.writer }"
			readonly="readonly">
	</div>
</div>
<!-- /.box-body -->
<div class="box-footer">
	<button type="submit" class='btn btn-warning'>Modify</button>
	<button type="submit" class="btn btn-danger">ReMOVE</button>
	<button type="submit" class='btn btn-primary'>LIST ALL</button>
	<button type="button" class='btn btn-danger'>dddd?</button>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="box Box-success">
			<div class="box-header">
				<h3 class="box-title">Add New Reply</h3>
			</div>
			<div class="box-body">
				<label for="exampleInputEmail1">Writer</label> <input
					class="form-control" type="text" placeholder="USER ID"
					id="newReplyWriter"> <label for="exampleInputEmail1">Reply
					Text</label> <input class="form-control" type="text"
					placeholder="REPLY TEXT" id="newReplyText">
			</div>
			<!-- /.box body  -->
			<div class="box-footer">
				<button type="submit" class="btn btn-primary" id="replyAddBtn">ADD
					REPLY</button>
			</div>
		</div>
	</div>
</div>
<!-- The time line -->
<ul class="timeline">
	<!-- timeline time label -->
	<li class="time-label" id="repliesDiv"><span class="bg-green">Replies
			List</span></li>

</ul>
<div class="text-center">
	<ul id="pagination" class="pagination pagination-sm no-margin"></ul>
</div>
<%@ include file="../include/footer.jsp"%>
