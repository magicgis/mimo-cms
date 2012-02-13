<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/cms/taglibs.jsp"%>
<%@include file="/common/cms/common-header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx }/resources/js/markitup/jquery.markitup.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx }/resources/js/markitup/skins/markitup/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx }/resources/js/markitup/sets/default/style.css" />
<script type="text/javascript" src="${ctx }/resources/js/ueditor/editor_config.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/ueditor/editor_all.js"></script>
<link rel="stylesheet" href="${ctx }/resources/js/ueditor/themes/default/ueditor.css"/>
<script type="text/javascript">
	$(document).ready(function(){
		var editor = new baidu.editor.ui.Editor();
	    editor.render("content");
	});
</script>
</head>
<body>

<form:form method="post" modelAttribute="guestbook">
<input type="hidden" name="_method" value="${_method }" />
 <!--content start-->
<div class="content">
<div class="table">
<div class="contentNav"><h1><span class="allIco2 ico_6"></span>文章管理</h1></div>
 <div class="tips"><img src="${ctx}/resources/images/tips.gif" align="left" />所有带有<span class="red">*</span>为必填项</div>
<div class="info border">

<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tbody>
<tr>
<td width="150" align="right" nowrap="nowrap">留言者：</td>
<td colspan="2"><form:input path="email" cssClass="input5 fontMar"/></td>
</tr>
<td width="150" align="right" nowrap="nowrap">内容：</td>
<td colspan="2">
	<form:textarea path="content" rows="30" cols="120" />
</td>
</tr>
</tbody>
</table>
</div>
<div class="contactBbutton">
<input id="ok" type="submit" value="提交" class="button1" />&nbsp;
<input id=“back” type="button" value="返回" class="button1" onclick="javascript:history.go(-1);"/>
</div>
</div></div>
</form:form>
</body>
</html>
