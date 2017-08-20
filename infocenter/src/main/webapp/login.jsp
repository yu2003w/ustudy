<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
	String url = request.getRequestURL().toString();
	url = url.substring(0, url.indexOf('/', url.indexOf("//") + 2));
	String context = request.getContextPath();
	url += context;
	application.setAttribute("ctx", url);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="resources/css/normalize.min.css" rel="stylesheet">
	<link href="resources/css/bootstrap.css" rel="stylesheet">
	<link href="resources/css/bootstrap-theme.min.css" rel="stylesheet">
	<link href="resources/css/style.css" rel="stylesheet">
	<link href="resources/css/bootstrapValidator.min.css" rel="stylesheet">
    <script src="js/md5.min.js"></script>
	<title>login</title>
    <script type="text/javascript"> 
        function giveFocus(){ 
            document.login.username.focus() 
        } 
        function submitForm(){
            document.login.password.value = md5(document.login.password.value);
            document.login.submit();
        } 
        function resetForm(){ 
            document.login.reset();
            document.login.username.focus();
        }
</script>
</head>
<body onload="giveFocus()">
        <div id="loginModal" class="panel panel-default">
            <form id="login" name="login" method="post" class="form-horizontal" action="${ctx}/login"
				data-bv-message="输入错误"
				data-bv-feedbackicons-valid="glyphicon glyphicon-ok"
				data-bv-feedbackicons-invalid="glyphicon glyphicon-remove"
				data-bv-feedbackicons-validating="glyphicon glyphicon-refresh">
                 <div class="panel-heading">
                     <h4 >用户登录</h4>
                 </div>
                 <div class="panel-body">
                        <div class="form-group">
                            <label for="username" class="col-sm-3 control-label">姓名：</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="username" name="username" placeholder="请输入姓名" required data-bv-notempty-message="姓名不能为空">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-sm-3 control-label">密码：</label>
                            <div class="col-sm-9">
                                <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码" required data-bv-notempty-message="密码不能为空">
                            </div>
                        </div>
                 </div>
                 <div class="panel-footer text-center">
                     <button type="button" class="btn btn-default" data-dismiss="modal" onclick="resetForm()">取消</button>
                     <button type="button" class="btn btn-primary" onclick="submitForm()">确定</button>
                 </div>
            </form>
        </div>
</body>
</html>