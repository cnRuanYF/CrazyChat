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

<script type="text/javascript">
	function validate() {

		
		var password = document.getElementById("password").value;
		var passwordCheck = document.getElementById("passwordCheck").value;

		if (password == "") {
			confirm("设置失败，密码不能为空");
			return false;
		} else if (password == passwordCheck) {
			confirm("设置成功,请登录");
			return true;
		} else {
			confirm("设置失败，密码不一");
			return false;
		}

	}
</script>
</head>

<body>

	<div class="container ">
		<form method="post" action="RegistServlet"
			onsubmit="return validate()">
			<div class="row justify-content-md-center">
				<div class="form-group col-sm-12 col-md-7">
					<h1>重新设置密码</h1>
				</div>
				<div class="form-group col-sm-12 col-md-7">
					<h4>忘记过去，重新开始</h4>
				</div>

				
				<div class="form-group col-sm-12 col-md-7">
					<label for="exampleInputPassword1">输入新密码</label> <input
						type="password" class="form-control" name="password" id="password"
						placeholder="输入密码">
				</div>
				<div class="form-group col-sm-12 col-md-7">
					<label for="exampleInputPassword2">确认密码</label> <input
						type="password" class="form-control" name="passwordCheck"
						id="passwordCheck" placeholder="再次输入密码">
				</div>

				<div class="form-group  col-sm-12 col-md-7 ">
					<button type="submit" class="btn btn-primary form-control">完成</button>
				</div>
			</div>
		</form>
	</div>

</body>

</html>