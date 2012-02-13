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
<form:form action="${ctx }/conf/put" method="post" modelAttribute="configure" id="form">
<input type="hidden" name="_method" value="put" />
 <!--content start-->
<div class="content">
<div class="table">
<div class="contentNav"><h1><span class="allIco2 ico_6"></span>站点配置</h1></div>
 <div class="tips"><img src="${ctx}/resources/images/tips.gif" align="left" />所有带有<span class="red">*</span>为必填项</div>
<div class="info border">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tbody>
<tr>
<td width="240" align="right" nowrap="nowrap"><span class="red">*</span>模板存储路径：</td>
<td colspan="2">
	<form:input path="templatePath" cssClass="input5 fontMar" id="channel"/>
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap"><span class="red">*</span>网站资源路径：</td>
<td colspan="2">
	<form:input path="resourcePath" cssClass="input5 fontMar" id="channel"/>
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap"><span class="red">*</span>允许上传或浏览的资源文件类型：</td>
<td colspan="2">
	<form:input path="allowedResourceTypes" cssClass="input5 fontMar" id="channel"/>
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap"><span class="red">*</span>网站安全资源路径：</td>
<td colspan="2">
	<form:input path="securityResourcePath" cssClass="input5 fontMar" id="channel"/>
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap"><span class="red">*</span>允许上传或浏览的安全资源文件类型：</td>
<td colspan="2">
	<form:input path="allowedSecurityResourceTypes" cssClass="input5 fontMar" id="channel"/>
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap"><span class="red">*</span>图片存储路径：</td>
<td colspan="2">
	<form:input path="photoPath" cssClass="input5 fontMar" id="channel"/>
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap"><span class="red">*</span>允许上传图片类型：</td>
<td colspan="2">
	<form:input path="allowedPhotoTypes" cssClass="input5 fontMar" id="channel"/>
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap"><span class="red">*</span>附件存储路径：</td>
<td colspan="2">
	<form:input path="attachmentPath" cssClass="input5 fontMar" id="channel"/>
</td>
</tr>
<tr>
<td width="240" align="right" nowrap="nowrap"><span class="red">*</span>上传文件最大字节数（通用）：</td>
<td colspan="2">
	<form:input path="maxResourceSize" cssClass="input5 fontMar" id="channel"/>
</td>
</tr>
</tbody>
</table>
</div>
<div class="contactBbutton">
<input id="ok" type="submit" value="提交" class="button1" />&nbsp;
<input id="back" type="button" value="返回" class="button1" onclick="javascript:history.go(-1);"/>
</div>
  
</div></div>
</form:form>
</body>
</html>
