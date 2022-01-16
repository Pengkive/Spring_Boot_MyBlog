<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>
	<div class="container">
		<form>
			<input type="hidden" id="id" value="${principal.user.id}"/> <!-- 아이디정보 히든값으로 넘기기 -->
		  <div class="form-group">
		    <label for="username">Username</label>
		    <input type="text" class="form-control" placeholder="Enter username" id="username" value="${principal.user.username}" readonly>
		  </div>
		  
		  <c:if test="${empty principal.user.oauth }">
		   <div class="form-group">
		     <label for="pwd">Password</label>
		     <input type="password" class="form-control" placeholder="Enter password" id="password">
		   </div>
		   <div class="form-group">
		    <label for="email">Email</label>
		    <input type="email" class="form-control" placeholder="Enter email" id="email" value="${principal.user.email}">
		   </div>
		  </c:if>
		  
		  <div class="form-group">
		    <label for="email">Email</label>
		    <input type="email" class="form-control" placeholder="Enter email" id="email" value="${principal.user.email}" readonly>
		  </div>
		</form>
	  		 <button id="btn-update" class="btn btn-primary">회원수정완료</button>

	</div>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>