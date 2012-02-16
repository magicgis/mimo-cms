<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/cms/taglibs.jsp"%>
<%@include file="/common/cms/common-header.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 

"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<form:form method="post" modelAttribute="channel" id="form">
<input type="hidden" name="_method" value="${_method }" />
 <!--content start-->
<div class="content">
<div class="table">
<div class="contentNav"><h1><span class="allIco2 ico_6"></span>栏目管理</h1></div>
 <div class="tips"><img src="${ctx}/resources/images/tips.gif" align="left" />所有带有<span class="red">*</span>为必填项</div>
<div class="info border">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tbody>
<tr>
<td width="240" align="right" nowrap="nowrap"><span class="red">*</span>分类名：</td>
<td colspan="2">
	<form:input path="name" cssClass="input5 fontMar" id="channel"/>
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap">父栏目：</td>
<td colspan="2">
<form:select path="father.id">
	<form:option value="">请选择</form:option>
	<form:options items="${channels}" itemValue="id" itemLabel="name"/>
</form:select>
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap">模板：</td>
<td colspan="2">
<form:select path="selfTemplate.id">
	<form:option value="">请选择</form:option>
	<form:options items="${templates}" itemValue="id" itemLabel="name"/>
</form:select>
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap">访问路径：</td>
<td colspan="2">
	<form:input path="path" cssClass="input6 fontMar" id="path"/>&nbsp;（如：aboutus）
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap">分类排序：</td>
<td colspan="2">
	<form:input path="priority" cssClass="input5 fontMar" id="sort"/>
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap">meta标题：</td>
<td colspan="2">
	<form:textarea path="metaTitle" cssClass="input5 fontMar" style="margin-top: 2px; margin-bottom: 2px; height: 80px; margin-left: 2px; margin-right: 2px; width: 318px;"  />
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap">meta描述：</td>
<td colspan="2">
	<form:textarea path="metaDescr" cssClass="input5 fontMar" style="margin-top: 2px; margin-bottom: 2px; height: 80px; margin-left: 2px; margin-right: 2px; width: 318px;"  />
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap">meta关键字：</td>
<td colspan="2">
	<form:textarea path="metaKeyword" cssClass="input5 fontMar" style="margin-top: 2px; margin-bottom: 2px; height: 80px; margin-left: 2px; margin-right: 2px; width: 318px;"  />
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
