<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/cms/taglibs.jsp"%>
<%@include file="/common/cms/common-header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

</head>
<body>
<div class="content">
<div class="table">
  <div class="contentNav"><h1><span class="allIco2 ico_6"></span>欢迎页面</h1></div>
<div class="addList">
  	<p>亲！欢迎你的到来！</p>
  	<p>操作系统版本：${props['os.name']}</p>
  	<p>JAVA运行环境：${props['java.runtime.name']}</p>
	<p>JAVA虚拟机：${props['java.vm.name']}</p>
</div> 

  
</div></div>
</body>
</html>
