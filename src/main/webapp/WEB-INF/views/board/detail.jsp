<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
	<button class="btn btn-info" onclick="history.back();">돌아가기</button>
	</p>
	
	<form>
		<div class="form-group" style="font-size: 15px;float: right;">
			글번호 : <span id="id"><i>${board.id} </i></span>
			작성자 : <span><i>${board.user.username} </i></span>
		</div><hr>
		<div class="form-group" style="font-size: 19px;">
		   <b>${board.title}</b>
		</div><hr>
		<div class="form-group" style="font-size: 17px;">
		  ${board.content}
		</div>
	</form>
	<c:if test="${board.user.id==principal.user.id}">
		<div class="d-grid gap-2 d-md-flex justify-content-md-end">
			<a href="/board/${board.id}/updateForm" class="btn btn-warning" style="margin-right:10px">수정하기</a>
			<button id="btn-delete" class="btn btn-danger">삭제하기</button>
		</div>
	</c:if><hr>
	
	<!-- 댓글 시작 -->
	<form>
		<div class="input-group mb-3">
			<input type="hidden" id="userId" value="${principal.user.id}">
			<input type="hidden" id="boardId" value="${board.id}">
			<textarea id="replyContent" class="form-control" rows="1"></textarea>
			<button id="btn-reply-save" class="btn btn-outline-secondary" type="button" id="button-addon2">등록</button>
		</div>
	</form>

	<div class="card">
		<div class="card-hearder">댓글 리스트</div>
		<ul id="reply--box" class="list-group list-group-flush">
			<c:forEach var="reply" items="${board.replys}">
				<li id="reply--1" class="list-group-item d-flex justify-content-between">
					<div>${reply.content}</div>
					<div class="d-flex">
						<div class=d-flex>작성자: ${reply.user.username } &nbsp;</div>
						<c:if test="${principal.username == reply.user.username}">
							<button class="badge badge-secondary">삭제</button>
						</c:if>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
	<!-- 댓글 끝 -->


</div>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>