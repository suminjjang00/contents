<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="../../../resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
</head>
<body>
	<%-- <script type="text/javascript">
$(".pagination li a").on("click",function(e){
	e.preventDefault();
	var target=$(this).attr("href");
	
	var jobForm=$("#jobForm");
	jobForm.find("name=['page']").val(target);
	jobForm.attr("action","/board/listPage").attr("method","get");
	jobForm.submit();
});
</script>
<form id="jobForm">
	<input type="hidden" name='page' value=${pageMaker.cri.perPageNum }>
	<input type="hidden" name="perPageNum" value=${pageMaker.cri.perPageNum }>
</form>
 --%>
 <!-- git test for reset and checkout  -->
 <script>
$(document).ready(function(){
	
	 $('#searchBtn').on("click",function(event){
		 self.location="list"
		 +'${pageMaker.makeQuery(1)}'
		 +'&searchType='
		 +$("select option:selected").val()
		 +'&keyword='
		 +$("#keywordInput").val();
	 });
	 $('#newBtn').on('click',function(evt){
		 self.location="register";
	 });
 });
 </script>
	<%@include file="../include/header.jsp"%>
	<!-- c:out으로 값을 도출해냄. 그리고 삼항연산자로 cri.searchType이 널값일 경우 선택자에 selected를 넣고 
	널값이 아닐경우 ''이 공백값을 넣도록 한다.  -->
	<div class="box-body">
		<select name="searchType">
			<option value="n"
				<c:out value="${cri.searchType == null? 'selected':'' }"/>>
				--</option>
			<option value="t"
				<c:out value="${cri.searchType eq 't' ? 'selected': '' }"/>>
				Title</option>
			<option value="c"
				<c:out value="${cri.searchType eq 'c' ? 'selected': ''}" />>
				Content</option>
			<option value="w"
				<c:out value="${cri.searchType eq 'w' ? 'selected': '' }"/>>
				Writer</option>
			<option value="tc"
				<c:out value="${cri.searchType eq 'tc' ? 'selected': '' }"/>>
				Title or Content</option>
			<option value="cw"
				<c:out value="${cri.searchType eq 'cw' ? 'selected': '' }"/>>
				Content or Writer</option>
			<option value="tcw"
				<c:out value="${cri.searchType eq 'tcw' ? 'selected': '' }"/>>
				Title or Content or Writer</option>			
		</select>
		<input type="text" name="keyword" id="keywordInput" value="${cri.keyword }">
		<button id="searchBtn">Search</button>
		<button id="newBtn">New Board</button>
	</div>
	<table class="table table-border">
		<tr>
			<th style="width: 10px">BNO</th>
			<th>TITLE</th>
			<th>WRITER</th>
			<th>REGDATE</th>
			<th style="width: 40px">VIEWCNT</th>
		</tr>
		<c:forEach items="${list}" var="boardVo">
			<tr>
				<td>${boardVo.bno }</td>
				<td><a href='/sboard/readPage${pageMaker.makeSearch(pageMaker.cri.page) }
			&bno=${boardVo.bno}'>${boardVo.title }<strong>[${boardVo.replycnt}]</strong>
				</a></td>
				<td>${boardVo.writer }</td>
				<td><fmt:formatDate value="${boardVo.regdate }"
						pattern="yyyy-MM-dd HH:mm" /><br /></td>
				<td><span class="badge bg-red">${boardVo.viewcnt }</span></td>
			</tr>
		</c:forEach>
	</table>			
					
		<div class="text-center">
			<ul class="pagination">
				<c:if test="${pageMaker.prev }">
					<li><a href="list${pageMaker.makeSearch(pageMaker.startPage -1) }">&laquo;</a></li>
				</c:if>
				<c:forEach begin="${pageMaker.startPage}"
					end="${pageMaker.endPage }" var="idx">
					<li <c:out value="${pageMaker.cri.page == idx?'class=active':''}"/>>
						<a href="list${pageMaker.makeSearch(idx) }">${idx}</a>
					</li>
				</c:forEach>
				<c:if test="${pageMaker.next && pageMaker.endPage > 0 }">
					<li><a href="list${pageMaker.makeSearch(pageMaker.endPage +1)}">&raquo;</a></li>
				</c:if>
			</ul>
		</div>
	
	<%@include file="../include/footer.jsp"%>
</body>
</html>