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

		var answer1 = document.getElementById("answer1").value;
		var answer2 = document.getElementById("answer2").value;
		var answer3 = document.getElementById("answer3").value;

		if (answer1==null || answer2 == "" || answer3 == "") {
			confirm("答案不能为空");
			return false;
		} else {
			return true;
		}

	}
</script>
</head>
<%
String username=request.getParameter("username");
%>
<body>

	<div class="container ">
		<form method="post" action="RegisterSuccessServlet"
			onsubmit="return validate()">
			<div class="row justify-content-md-center">
				<div class="form-group col-sm-12 col-md-7">
					<h6>你好！<%=username %></h6>
					<h1>设置密保问题</h1>
				</div>
				<div class="form-group col-sm-12 col-md-7">
					<h4>忘记密码时可以用密保找回</h4>
				</div>

				<div class="form-group col-sm-12 col-md-7">
					<label for="exampleInputPassword1" id="labelQuestion1">第一个密保问题</label>
					<select class="form-control" id="select1" name="select1">
						<option>1.1</option>
						<option>1.2</option>
						<option>1.3</option>
					</select> <input type="text" class="form-control" name="answer1"
						id="username" aria-describedby="emailHelp" placeholder="答案一">
				</div>
				<div class="form-group col-sm-12 col-md-7">
					<label for="exampleInputPassword1" id="labelQuestion2">第二个密保问题</label>
					<select class="form-control" id="select2" name="select2">
						<option>2.1</option>
						<option>2.2</option>
						<option>2.3</option>
					</select> <input type="text" class="form-control" name="answer2"
						id="password" placeholder="答案二">
				</div>
				<div class="form-group col-sm-12 col-md-7">
					<label for="exampleInputPassword1" id="labelQuestion3">第三个密保问题</label>
					<select class="form-control" id="select3" name="select3">
						<option>3.1</option>
						<option>3.2</option>
						<option>3.3</option>
					</select><input type="text" class="form-control" name="answer3"
						id="passwordCheck" placeholder="答案三">
				</div>

				<div class="form-group  col-sm-12 col-md-7 ">
					<button type="submit" class="btn btn-primary form-control">保存</button>
				</div>
			</div>
		</form>
	</div>

</body>


</html>