<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Random"%>
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
<title>注册页面</title>

<script type="text/javascript">
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
		} else if (password == passwordCheck) {
			return true;
		} else {
			confirm("注册失败，密码不一");
			return false;
		}
	}
	function openWindow() {
		window.open("openWindow.jsp","newwindow", "height=600, width=800, screenX=100,screenY=100,toolbar=no, menubar=no, location=no, status=no");
	}
	
	function changePic() {
		var i=Math.floor(Math.random()*189+1);
		var str1="img/";
		var str2=".png";
		var str=str1.concat(i,str2);
		document.getElementById('pic').src =str;
		document.getElementById('picID').value=i;
	}
</script>
</head>

<body>

	<div class="container " margin="50px,auto,auto,auto"margin-top="50px">
		<form method="post" action="RegisterServlet"
			onsubmit="return validate()" >
			<div class="row justify-content-md-center" margin-top="50px">
				<div class="form-group col-sm-12 col-md-7">
					<h1>欢迎注册疯聊</h1>
				</div>
				<div class="form-group col-sm-12 col-md-7">
					<h4>我在疯聊，你在哪</h4>
					<%
						if (request.getParameter("username") == null) {

						} else {
							String username = request.getParameter("username");
					%>
					<h5>
						<font color="red"><%=username%>已经存在，请重新注册</font>
					</h5>
					<%
						}
					%>
				</div>
				<div class="form-group col-sm-12 col-md-7" align="center">
					<%
						//String add1 = (String) session.getAttribute("picAddress");
						//if (session.getAttribute("picAddress") == null) {
							List<String> pic = new ArrayList<>();
							for (int i = 0; i < 189; i++) {
								pic.add("img/" + i + ".png");
							}
							int num = new Random().nextInt(pic.size() - 1);
							String add = pic.get(num);
							//session.setAttribute("picAddress", add);
					%>
					<img id="pic" name="pic" src="<%=add%>" 
						class="img-circle" width="100px" height="100px">
					<%
						//} else {
						//	String add = (String) session.getAttribute("picAddress");
					%>
					<!-- 
					<img id="pic" name="pic" src="<%=add%>" onclick="openWindow()"
						class="img-circle" width="100px" height="100px">
						-->
					<%
						//}
					%>
					<br>
					<br>
					<!-- 隐藏表单 传递图片ID -->
					<input id="picID" name="picID" type="hidden" value=""></input>
				<button type="button" class="btn btn-info" onclick="changePic()">换一张</button>
				</div>
				<div class="form-group col-sm-12 col-md-7">
					<label for="exampleInputEmail1">用户名</label> <input type="text"
						class="form-control" name="username" id="username"
						aria-describedby="emailHelp" placeholder="小明"> <small
						id="emailHelp" class="form-text text-muted">注册后，用户名不可修改</small>
				</div>
				<div class="form-group col-sm-12 col-md-7">
					<label for="exampleInputPassword1">密码</label> <input
						type="password" class="form-control" name="password" id="password"
						placeholder="输入密码">
				</div>
				<div class="form-group col-sm-12 col-md-7">
					<label for="exampleInputPassword2">确认密码</label> <input
						type="password" class="form-control" name="passwordCheck"
						id="passwordCheck" placeholder="再次输入密码">
				</div>

				<div class="form-group  col-sm-12 col-md-7 ">
					<button type="submit" class="btn btn-info form-control">注册</button>
				</div>
			</div>
		</form>
	</div>

</body>

</html>