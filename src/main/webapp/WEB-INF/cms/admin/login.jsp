<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Mimo --- mini & more</title>
<%@include file="/common/cms/taglibs.jsp" %>
<script src="${ctx }/resources/js/jquery.js" type="text/javascript"></script>
<link href="${ctx }/resources/css/login.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="head">
<div class="logo"></div>
<div class="head_word"></div>
</div>
<div class="center">
<div class="left">
</div>
<div class="right">
<form action="${ctx}/login" method="post">
<div class="loginbg">
<div class="login_zliao">
<div class="user">
<div class="font1">用户名：</div>
<div class="input1">
<input id="username" type="text" value="" name="username" maxlength="20" size="3" style="border:0px; background-repeat:no-repeat; background-image:url(${ctx}/resources/images/kk.jpg); width:153px; height:20px; padding-top:6px; padding-left:2px;" />
</div>
</div>
<div class="user_text">&nbsp;</div>
<div class="password">
<div class="font1">密&nbsp;&nbsp;码：</div>
<div class="input1"><input id="password" type="password" value="" name="password" maxlength="20" size="3" style="border:0px; background-repeat:no-repeat;background-image:url(${ctx}/resources/images/kk.jpg); width:153px; height:20px; padding-top:6px; padding-left:2px;" />
</div>
<div class="clear"></div>
</div>
<div class="user_text" id="showError">&nbsp;</div>
<div class="logo_wk_tijiao">
<input id="sub" type="submit" value="登  录" name="" style=" width:90px;height:25px;">
</div>
</div>
</div>
</form>
</div>
<div class="clear"></div>
</div>
</body>
</html>