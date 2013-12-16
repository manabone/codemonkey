<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<head>
	 <title>Signin Template for Bootstrap</title>
</head>
<body>
	<div class="container">
		<form class="form-signin" action="${ctx}/app/auth/login" >
			<h2 class="form-signin-heading">Please sign in</h2>
			<label>username</label>
			<input name="username" type="text" class="form-control" placeholder="username" autofocus> 
			<label>password</label>
			<input name="password" type="password" class="form-control" placeholder="Password"> 
			<label class="checkbox">
				 <input type="checkbox" value="remember-me"> 
				 Remember me
			</label>
			<button class="btn btn-lg btn-primary btn-block" type="submit">
				Sign in
			</button>
		</form>
	</div>
	<!-- /container -->
</body>
