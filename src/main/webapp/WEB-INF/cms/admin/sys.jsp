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
		<span style="width:auto;">系统管理</span>
	
	</h5>
	<ul class="menu_body" id="box4">
		<shiro:hasRole name="admin">
        <li><a href="${ctx}/security/user/list" target="right"><span class="allIco ico15"></span>用户管理</a></li>
        <li><a href="${ctx}/security/role/list" target="right"><span class="allIco ico15"></span>角色管理</a></li>
        <li><a href="${ctx}/security/authority/list" target="right"><span class="allIco ico15"></span>权限管理</a></li>
        <li><a href="${ctx}/conf/get" target="right"><span class="allIco ico15"></span>站点配置</a></li>
        <li><a href="${ctx}/monitoring-record/list" target="right"><span class="allIco ico15"></span>日志管理</a></li>
		</shiro:hasRole>
		<shiro:hasPermission name="resource:list">
        	<li><a href="${ctx}/resource/list" target="right"><span class="allIco ico15"></span>网站资源管理</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="photo-resource:list">
	        <li><a href="${ctx}/photo-resource/list" target="right"><span class="allIco ico15"></span>图片资源管理</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="security-resource:list">
	        <li><a href="${ctx}/security-resource/list" target="right"><span class="allIco ico15"></span>网站安全资源管理</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="recycle-resource:list">
	    <li><a href="${ctx}/recycle-resource/list" target="right"><span class="allIco ico15"></span>资源副本管理</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="guestbook:list">
	        <li><a href="${ctx}/guestbook/list" target="right"><span class="allIco ico15"></span>留言管理</a></li>
		</shiro:hasPermission>
    </ul>
</div>
</div>
</body>
</html>
