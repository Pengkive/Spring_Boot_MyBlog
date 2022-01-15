<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>
	<div class="container">
		<form action="/auth/loginProc" method="post">
		 <div class="form-group col-4 mx-auto">
		    <label for="username">Username</label>
		    <input type="text" name="username"class="form-control" placeholder="Enter username" id="username">
		  </div>
		  <div class="form-group col-4 mx-auto">
		    <label for="pwd">Password</label>
		    <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		  </div>
		  <div class="d-grid gap-2 col-3 mx-auto">
	  		  <button id="btn-login" class="btn btn-success">로그인</button>
	  		  <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=65fcfe922d0fa539a1bf32c45062e422&redirect_uri=http://localhost:8000/auth/kakao/callback">
	  		  <img class="rounded mx-auto d-block" src="/image/kakao_login_large_wide.png" height="38px" width="300px"></a>
		  </div>
		</form>

	</div>

<%@ include file="../layout/footer.jsp" %>