<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="codec" uri="/WEB-INF/tld/codec.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/cms/taglibs.jsp"%>
<%@include file="/common/cms/common-header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="${ctx }/resources/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">
  $(function(){
	  
	  $(".recycle").each(function(){
		  
		 var $this = $(this);
		 $this.click(function(){
			 if(confirm("确定还原该资源吗？")){
				 var id = $this.attr("id");
				 $.ajax({
					 url : "${ctx}/recycle-resource/" + id + "/recycle",
					 type : "POST",
					 data : {"_method" : "DELETE"},
					 success : function(){
						 location.reload();
					 },
					 error : function(){
						 alert("无法还原该数据！");
					 }
				 });
			 }
			 
			 return false;
		 });
	  });
	});  
</script>
</head>
<body>
 <!--content start-->
<div class="content">
<div class="table">
  <div class="contentNav"><h1><span class="allIco2 ico_6"></span>资源副本管理</h1></div>
  <!--内容 start-->
<div class="function">
<div class="clear"></div>
</div>   
  
   <!--信息搜索 start-->
    <div class="search"><ul>
    <li>名称： <input type="text" name="params[name]" value="${page.params.name }" class="input1" /></li>
   <li>
    <div class="searcBbutton"><input id="search" name="search" type="submit" value="提交" class="button1" id="OK" />&nbsp;<input id="reset" name="reset" type="button" value="清空" class="button1" id="Reset" /></div></li>
    </ul>
    <div class="clear"></div>
    </div>
        <!--信息搜索 end--> 
<form id="myForm" action="${ctx }/recycle-resource/list" method="get">
<input type="hidden" name="pathname" value="${param.pathname}"/>
<div class="addList">
  <table cellpadding="1" cellspacing="1"  class="table_bj">
    <tr class="table_top">
      <td class="dot" width="30%">文件名称</td>
      <td>文件路径</td>
      <td>文件版本</td>
      <td>创建时间</td>
      <td width="7%">操作</td>
    </tr>
    <c:choose>
    	<c:when test="${not empty page.result }">
    	<c:forEach items="${page.result }" var="bean" varStatus="status">
		<tr class="table_con">
			<td class="dot">${bean.name}&nbsp;</td>
			<td>${bean.path }&nbsp;</td>
			<td>${bean.version }&nbsp;</td>
			<td class="date">${bean.createTime }</td>
			<td>
				<shiro:hasPermission name="recycle-resource:recycle">
				<a href="javascript:void(0);" id ="${bean.id }" class="recycle">还原</a>
				</shiro:hasPermission>
				&nbsp;
			</td>
		</tr>
   	 	</c:forEach>
    	</c:when>
    	<c:otherwise>
    	<tr class="table_con"><td colspan="5" align="center"><b>暂无内容</b></td></tr>
    	</c:otherwise>
    </c:choose>
  </table>
</div> 

<div class="contactBbutton">
	<jsp:include page="/common/cms/page.jsp" />
</div>

<!--list end--> 
<!--内容 end--> 
</form>
</div></div>
  <!--content end-->
</body>
</html>
