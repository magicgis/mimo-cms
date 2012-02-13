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
	  
	  $(".del").each(function(){
		  
		 var $this = $(this);
		 $this.click(function(){
			 if(confirm("确定删除该数据吗？")){
				 var pathname = $this.attr("id");
				 $.ajax({
					 url : "${ctx}/security-resource/delete",
					 type : "POST",
					 data : {"pathname" : pathname , "_method" : "DELETE"},
					 success : function(){
						 location.reload();
					 },
					 error : function(){
						 alert("无法删除该数据！");
					 }
				 });
			 }
			 
			 return false;
		 });
	  });
	  
	  $("#uploadFile").click(function(){
			$("#fileForm").ajaxSubmit({
				type : "POST",
				data : {"pathname" : "${param.pathname}"},
				dataType : "json",
				success:function(data){
					if(data && data.type && data.type.indexOf("SUCCESS") != -1){
						location.reload();
						return;
					}
					
					alert(data.message);
				},
				error : function(request, e){
					alert(e);
				}
			});
			
			// must return false
			return false;
		});
	});  
</script>
</head>
<body>
 <!--content start-->
<div class="content">
<div class="table">
  <div class="contentNav"><h1><span class="allIco2 ico_6"></span>网站安全资源管理</h1></div>
  <!--内容 start-->
<div class="function">
<shiro:hasPermission name="security-resource:upload">
<ul>
  <form action="${ctx }/security-resource/upload" method="post" ENCTYPE="multipart/form-data" id="fileForm">  
  	 <li id="liNav">你可以：</li>
     <input type="file" name="file" />
     <shiro:hasPermission name="resource:upload">
     	<input id="uploadFile" type="submit" value=" 上传文件  "/>  
     </shiro:hasPermission>
  </form>
   <input id=“back” type="button" value="返回" class="button1" onclick="javascript:history.go(-1);"/>
</ul>
</shiro:hasPermission>
<div class="clear"></div>
</div>   
<form id="myForm" action="${ctx }/security-resource/list" method="get">
<input type="hidden" name="pathname" value="${param.pathname}"/>
<div class="addList">
  <table cellpadding="1" cellspacing="1"  class="table_bj">
    <tr class="table_top">
      <td class="dot" width="30%">文件名称</td>
      <td>文件路径</td>
      <td>文件大小</td>
      <td>文件最后修改时间</td>
      <td width="7%">操作</td>
    </tr>
    <c:choose>
    	<c:when test="${not empty page.result }">
    	<c:forEach items="${page.result }" var="bean" varStatus="status">
		<tr class="table_con">
			<td class="dot">
			<c:choose>
				<c:when test="${bean.directory }">
				<a href="${ctx }/security-resource/list?pathname=${codec:urlEncode(bean.path)}"> [目录]&nbsp;${bean.name}</a>
				</c:when>
				<c:otherwise>
				[普通文件]&nbsp;${bean.name}
				</c:otherwise>
			</c:choose>
			&nbsp;
			</td>
			<td>${bean.path }&nbsp;</td>
			<td class="fileSize">${bean.size }&nbsp;</td>
			<td class="date">${bean.lastModified }</td>
			<td>
				<shiro:hasPermission name="security-resource:delete">
					<c:choose>
						<c:when test="${not bean.directory }">
						<a href="javascript:void(0);" id ="${bean.path }" class="del">删除</a>
						</c:when>
					</c:choose>
					&nbsp;
				</shiro:hasPermission>
				<shiro:hasPermission name="security-resource:download">
					<a href="${ctx }/security-resource/download?pathname=${codec:urlEncode(bean.path)}" >下载</a>&nbsp;
				</shiro:hasPermission>
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
