<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/cms/taglibs.jsp" %>
<%@include file="/common/cms/common-header.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<div class="sideBar" id="firstpane">
 <div class="mainNav table">
	<h5 class="menu_head">
		<div class="side_link" onclick="openShutManager(this,'box4',false,'-','+')">
			<a href="#">-</a>
		</div>
		<span style="width:auto;">内容管理</span>
	
	</h5>
	<ul class="menu_body" id="box4">
		<shiro:hasPermission name="channel:list">
        <li><a href="${ctx}/channel/list" target="right"><span class="allIco ico15"></span>栏目管理</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="template:list">
        <li><a href="${ctx}/template/list" target="right"><span class="allIco ico15"></span>模板管理</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="article:list">
        <li><a href="${ctx}/article/list/online" target="right"><span class="allIco ico15"></span>文章管理</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="article:list">
        <li><a href="${ctx}/article/list/offline" target="right"><span class="allIco ico15"></span>文章回收站</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="article-comment:list">
        <li><a href="${ctx}/article/comment/list" target="right"><span class="allIco ico15"></span>文章评论管理</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="article-attachment:list">
        <li><a href="${ctx}/article/attachment/list" target="right"><span class="allIco ico15"></span>文章附件管理</a></li>
		</shiro:hasPermission>
        <li><a href="${ctx}/article/mining-task/list" target="right"><span class="allIco ico15"></span>文章采集任务管理</a></li>
    </ul>
</div>
</div>
</body>
</html>
