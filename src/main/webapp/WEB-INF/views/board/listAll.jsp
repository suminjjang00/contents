<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
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
			<td><a href='/board/read?bno=${boardVo.bno }'>${boardVo.title }</a></td>
			<td>${boardVo.writer }</td>
			<td><fmt:formatDate value="${boardVo.regdate }" pattern="yyyy-MM-dd HH:mm"/><br/></td>
			<td><span class="badge bg-red">${boardVo.viewcnt }</span></td>			
		</tr>
	</c:forEach>
</table>

<%@ include file="../include/footer.jsp"%>
<script type="text/javascript">
var result='${msg}';
	if(result='success'){
		alert("요청 사항이 처리되었습니다");
	};
</script>