<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../include/header.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<script src="../../resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<
<style type="text/css">
.popup {
	position: abolute;
}

.back {
	background-color: gray;
	opacity: 0.5;
	width: 100%;
	height: 300%;
	overflow: hidden;
	z-index: 1101;
}

.front {
	z-index: 1110;
	opacity: 1;
	boarder: 1px;
	margin: auto;
}

.show {
	position: relative;
	max-width: 1200px;
	max-height: 800px;
	overflow: auto;
}
</style>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$(".uploadedList").on("click",
								".mailbox-attachment-info a", function(event) {
									var fileLink = $(this).attr("href");
									if (checkImageType(fileLink)) {
										event.preventDefault();
										var imgTag = $("#popup_img");
										imgTag.attr("src", fileLink);

										console.log(imgTag.attr("src"));

										$(".popup").show('slow');
										imgTag.addClass("show");
									}
								});
						$("#popup_img").on("click", function() {
							$(".popup").hide('slow');
						});

						/* 파일 업로드 부분을 처리하는 곳 */

						var bno = ${boardVo.bno};
						var template = Handlebars.compile($("#templateAttach")
								.html());

						$.getJSON("/sboard/getAttach/" + bno, function(list) {
							$(list).each(function() {

								var fileInfo = getFileInfo(this);

								var html = template(fileInfo);

								$(".uploadedList").append(html);
							})
						})

						/* alert("${boardVo.bno}")
						alert("${cri.page}")
						alert("${cri.perPageNum}") */
						/* 게시판 페이지 처리 부  */
						var formObj = $("form[role='form']");
						console.log(formObj);
						$("#ReadMod").on("click", function() {
							/* alert("ddd"); */
							formObj.attr("action", "/sboard/modifyPage");
							formObj.attr("method", "get");
							formObj.submit();
						});
						$("#ReadDel").on("click", function() {
							formObj.attr("method", "post");
							formObj.attr("action", "/sboard/removePage");
							formObj.submit();
						});
						$("#ListAll").on("click", function() {
							formObj.attr("method", "get")
							formObj.attr("action", "/sboard/list");
							formObj.submit();
						});
						/* 댓글 처리 소스부분 */
						/* var bno = ${boardVo.bno}; */
						var replyPage = 1;
						/*JSON형태로 자바에서 리턴된 값을 받아온다. 그리고 아래에있는 printData와 printPaging 함수를 이용하여
						받아온 값들을 출력함.*/
function getPage(pageInfo) {
	$.getJSON(pageInfo, function(data) {
		printData(data.list, $("#repliesDiv"),$('#template'));
		printPaging(data.pageMaker, $(".pagination"));
		$("#modifyModal").modal('hide');
		$("#replycntSmall").html()
		});
	}
		var printPaging = function(pageMaker, target) {
			var str = "";
			if (pageMaker.prev) {
				str += "<li><a href='"
				+ (pageMaker.startPage - 1)
				+ "'> << </a></li>";
				}
			for (var i = pageMaker.startPage, len = pageMaker.endPage; i <= len; i++) {
				var strClass = pageMaker.cri.page == i ? 'class=active': '';
				str += "<li "+strClass+"><a href='"+i+"'>" + i + "</a></li>";
				}
			if (pageMaker.next) {
				str += "<li><a href='"+ (pageMaker.endPage + 1)+ "'> >> </a></li>";
				}
			target.html(str);
			};			
			/* handlebars를 이용하여 댓글관련 데이터를 받아와 처리하는 부분임.   */
			Handlebars.registerHelper("prettifyDate", function(timeValue) {
				var dateObj = new Date(timeValue);
				var year = dateObj.getFullYear();
				var month = dateObj.getMonth() + 1;
				var date = dateObj.getDate();
				return year + "/" + month + "/" + date;
				});
			/* 댓글에서 로그인한지 확인 후 로그인 했다면 댓글기능이 가능하도록 설정하는 것  */
			Handlebars.registerHelper("eqReplyer", function(replyer, block) {
				var check = '';
				if (replyer == '${login.uid}') {
					check += block.fn();
					}
				return check;
				});
			var printData = function(replyArr, target,templateObject) {
				var template = Handlebars.compile(templateObject.html());
				var html = template(replyArr);
				$(".replyLi").remove();
				target.after(html);
				};
				/* 1페이지의 댓글 목록을 가져오도록 처리한 코드임. 이해가 잘 안간다 생각해봐야 할 문제 */
				$("#repliesDiv").on("click", function() {
					if ($('.timeline li').size() > 1) {
						return;
						};
					getPage("/replies/" + bno + "/1");
						});
				/* 댓글 페이징 이벤트 처리임 ul class=pagination 에서 이루어진다.
				각 링크에는 li태그와 a태그로 구성되고 이에대한 이벤트 처리임*/
				$(".pagination").on('click', 'li a', function(event) {
					event.preventDefault();
					replyPage = $(this).attr("href");
					getPage("/replies/" + bno + "/" + replyPage);
					})
					/*1번째 댓글을 등록하는 것. 그다음은 등록한 댓글이 보이는지 확인을 해야하는 것  */
	$("#replyAddBtn").on("click",function() {
		var replyObj = $("#newReplyWriter");
		var replytextObj = $("#newReplyText");
		var replyer = replyObj.val();
		var replytext = replytextObj.val();
		$.ajax({
			type : 'post',
			url : '/replies',
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "POST"},
				dataType : 'text',
				data : JSON.stringify({
					bno : bno,
					replyer : replyer,
					replytext : replytext
					}),
					success : function(result) {
						console.log("result:"+ result);
						if (result == 'SUCCESSA') {
							alert("등록 되었습니다");
							replyPage = 1;
							getPage("/replies/"+ bno+ "/"+ replyPage);
							replyObj.val("");
							replytextObj.val("");
							alert("oh?!")
							}
						}
				});
		});
						/*  */
$(".timeline").on("click",".replyLi",function(event) {
	var reply = $(this);
	$("#replytext").val(reply .find('.timeline-body').text());
		$(".modal-title").html(reply.attr("data-rno"));
		})

						/* 버튼 클릭시 게시물이 수정되도록 설정한 코드. */
						$("#replyModBtn")
								.on(
										"click",
										function() {
											var rno = $(".modal-title").html();
											var replytext = $("#replytext")
													.val();

											$
													.ajax({
														type : 'put',
														url : '/replies/' + rno,
														headers : {
															"Content-Type" : "application/json",
															"X-HTTP-Method-Override" : "PUT"
														},
														dataType : 'text',
														data : JSON
																.stringify({
																	replytext : replytext
																}),
														success : function(
																result) {
															if (result == 'SUCCESS') {
																alert("수정 되었습니다");
																getPage("/replies/"
																		+ bno
																		+ "/"
																		+ replyPage);

															}
														}
													});
										});
						$("#replyDelBtn")
								.on(
										"click",
										function() {
											var rno = $(".modal-title").html();

											$
													.ajax({
														type : 'delete',
														url : '/replies/' + rno,
														headers : {
															"Content-Type" : "application/json",
															"X-HTTP-Method-Override" : "DELETE"
														},
														dataType : 'text',
														success : function(
																result) {
															if (result == 'SUCCESS') {
																alert("삭제 습니다");
																getPage("/replies/"
																		+ bno
																		+ "/"
																		+ replyPage);
															}
														}
													})
										})

					});
</script>
<!-- 핸들러 이벤트를 이용해서 댓글의 목록을 출력하는 것임 아래에 있는것은... 흠.. -->
<script id="template" type="text/x-handlebars-template">
{{#each .}}
<li class="replyLi" data-rno={{rno}}>

<i class="fa fa-comments bg-blue"></i>

	<div class="timeline-item">
		<span class="time"> 
<i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
		</span>
		<h3 class="timeline-header">
			<strong>{{rno}}</strong>-{{replyer}}
		</h3>
		<div class="timeline-body">{{replytext}}</div>
		<div class="timeline-footer">
			{{#eqReplyer replyer}}	
				<a class="btn btn-primary btn-xs" data-toggle="modal"
					data-target="#modifyModal">Modify</a>
			{{/eqReplyer}}			
		</div>
	</div>
</li>
{{/each}}
</script>
<script id="templateAttach" type="text/x-handlebars-template">
<li data-src='{{fullName}}'>
	<span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}" 
alt = "Attachment"></span>
	<div class="mailbox-attachment-info">
	<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
	</span>
	</div>
</li>
</script>
<!-- hidden type img -->
<div class='popup back' style="display: none;"></div>
<div id="popup_front" class='popup front' style="display: none;">
	<img id="popup_img">
</div>
<!-- MOdal -->
<div id="modifyModal" class="modal modal-primary fade" role="dialog">
	<div class="modal-dialog">
		<!-- modal content -->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body" data-rno>
				<p>
					<input type="text" id="replytext" class="form-control">
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info" id="replyModBtn">Modify</button>
				<button type="button" class="btn btn-danger" id="replyDelBtn">Delete</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
<form role="form">
	<input type="hidden" name="page" value="${cri.page }"> <input
		type="hidden" name="perPageNum" value="${cri.perPageNum }"> <input
		type="hidden" name="bno" value="${boardVo.bno}"> <input
		type="hidden" name="searchType" value="${cri.searchType }"> <input
		type="hidden" name="keyword" value="${cri.keyword }">
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
<!-- 기존에있던 수정 삭제 조회 페이지이다. 주석처리한 이유는 로그인한 사용자만이 가능하도록 하기 위해서. -->
<!-- <div class="box-footer">
	<button type="submit" class='btn btn-warning' id="ReadMod">Modify</button>
	<button type="submit" class="btn btn-danger" id="ReadDel">ReMOVE</button>
	<button type="submit" class='btn btn-primary' id="ListAll">LIST ALL</button>
</div> -->
<div class="box-footer">
	<ul class="mailbox-attachments clearfix uploadedList">
		<c:if test="${login.uid ==boardVo.writer }">
			<button type="submit" class="btn btn-warning" id="ReadMod">Modify</button>
			<button type="submit" class="btn btn-danger" id="ReadDel">Remove</button>
		</c:if>
		<button type="submit" class="btn btn-primary" id="ListAll">List
			All</button>
	</ul>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="box Box-success">
			<div class="box-header">
				<h3 class="box-title">Add New Reply</h3>
			</div>
			<c:if test="${not empty login }">
				<div class="box-body">
					<label for="exampleInputEmail1">Writer</label> <input
						class="form-control" type="text" placeholder="USER ID"
						id="newReplyWriter" value="${login.uid }" readonly="readonly">

					<label for="exampleInputEmail1">Reply Text</label> <input
						class="form-control" type="text" placeholder="REPLY TEXT"
						id="newReplyText">
				</div>
				<!-- /.box body  -->
				<div class="box-footer">
					<button type="submit" class="btn btn-primary" id="replyAddBtn">ADD
						REPLY</button>
				</div>
			</c:if>
			<c:if test="${empty login }">
				<div class="box-body">
					<div>
						<a href="javascript:goLogin();">Login please</a>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</div>
<!-- The time line -->
<ul class="timeline">
	<!-- timeline time label -->
	<li class="time-label" id="repliesDiv"><span class="bg-green">Replies
			List<small id="replycntSmall">[${boardVo.replycnt}]</small>
	</span></li>

</ul>
<div class="text-center">
	<ul id="pagination" class="pagination pagination-sm no-margin"></ul>
</div>

<%@ include file="../include/footer.jsp"%>