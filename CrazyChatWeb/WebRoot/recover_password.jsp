<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<link rel="stylesheet"
	href="https://cdn.bootcss.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script
	src="https://cdn.bootcss.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>

<body>
	<%
	String username=request.getParameter("username");		
	%>
	<div class="container ">
		<form method="post" action="resetPassword.jsp"
			onsubmit="return validate()">
			<div class="row justify-content-md-center">
				<div class="form-group col-sm-12 col-md-7">
					<h5>你好！<%=username %></h5>
					<h1>找回密码</h1>
				</div>
				.
				<div class="form-group col-sm-12 col-md-7">
					<h4>记住密保比啥重要</h4>
				</div>

				<div class="form-group col-sm-12 col-md-7">
					<label for="exampleInputEmail1" id="labelQuestion1"></label> <input
						type="text" class="form-control" name="username" id="username"
						aria-describedby="emailHelp" placeholder="答案一">
				</div>
				<div class="form-group col-sm-12 col-md-7">
					<label for="exampleInputPassword1" id="labelQuestion2"></label> <input
						type="text" class="form-control" name="password" id="password"
						placeholder="答案二">
				</div>
				<div class="form-group col-sm-12 col-md-7">
					<label for="exampleInputPassword2" id="labelQuestion3">></label> <input
						type="text" class="form-control" name="passwordCheck"
						id="passwordCheck" placeholder="答案三">
				</div>

				<div class="form-group  col-sm-12 col-md-7 ">
					<button type="submit" class="btn btn-primary form-control">确认</button>
				</div>
			</div>
		</form>
	</div>

</body>
<script type="text/javascript">
	var labelQuestion1 = "数据库获取的问题一";
	document.getElementById("labelQuestion1").innerText = labelQuestion1;
	var labelQuestion2 = "数据库获取的问题一";
	document.getElementById("labelQuestion2").innerText = labelQuestion2;
	var labelQuestion3 = "数据库获取的问题一";
	document.getElementById("labelQuestion3").innerText = labelQuestion3;

	function validate() {

		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		var passwordCheck = document.getElementById("passwordCheck").value;

		if (username == "") {
			confirm("注册失败，用户名不能为空");
			return false;
		} else if (password == "") {
			confirm("注册失败，密码不能为空");
			return false;
		}

	}
</script>

</html>