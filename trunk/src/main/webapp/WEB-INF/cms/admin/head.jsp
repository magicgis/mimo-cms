<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="org.apache.shiro.SecurityUtils" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/cms/taglibs.jsp" %>
<%@include file="/common/cms/common-header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
$(document).ready(function(){      
	 $('.rb ul li').click(function(){
	  	$(this).addClass('bluebg');
	 });
	 $('.rb').mousedown(function(){
		  $('.rb ul li').removeClass('bluebg');
	});
});
</script>
</head>
<body>
<div class="head">
  <div class="imgCont">Mini & More</div>
  <div class="loginbar">
  <p class="floatl"><span>欢迎你：<strong ><%= SecurityUtils.getSubject().getPrincipal().toString() %></strong>&nbsp;&nbsp;
  	<a href="${ctx}/admin/logout" target="_parent" class="a_exit"><strong style="color:#004aba;">安全退出</strong></a>
 <!--  -->
 <div class="rb">
 <ul>
	<li class="bluebg"><a target="right" href="${ctx}/admin/right">首页</a></li>
	<li><a target="left" href="${ctx}/admin/sys" >系统管理</a></li>
	<li><a target="left" href="${ctx}/admin/content">内容管理</a></li>
</ul>
</div>
  	</p>
  </div>
</div>
</body>
</html>
