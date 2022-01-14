<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
	<button class="btn btn-info" onclick="history.back();">돌아가기</button>
	</p>
	
	<form>
		<div class="form-group1" style="font-size: 15px;float: right;">
			글번호 : <span id="id"><i>${board.id} </i></span>
			작성자 : <span><i>${board.user.username} </i></span>
		</div>
		<hr>
		<div class="form-group" style="font-size: 19px;">
		   <b>${board.title}</b>
		</div>
		<hr>
		<div class="form-group" style="font-size: 17px;">
		  ${board.content}
		</div>
	</form>
	<c:if test="${board.user.id==principal.user.id}">
		<div class="d-grid gap-2 d-md-flex justify-content-md-end">
			<a href="/board/${board.id}/updateForm" class="btn btn-warning" style="margin-right:10px">수정하기</a>
			<button id="btn-delete" class="btn btn-danger">삭제하기</button>
		</div>
	</c:if>
	
</div>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>