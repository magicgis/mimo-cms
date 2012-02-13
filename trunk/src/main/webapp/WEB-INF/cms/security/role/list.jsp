<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/cms/taglibs.jsp"%>
<%@include file="/common/cms/common-header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	$(function(){
		$("#add").click(function(){
			location.href = "${ctx}/security/role/create";
		});
		
		$("#bulkDel").click(function(){
			var items = mimo.select();
			if(items && items.length > 0 && confirm("你确定要删除这些内容吗?")){
				
				$('input[name="_method"]').remove();
				$("#myForm").attr("action", "${ctx}/security/role/delete")
							.attr("method","post")
							.append('<input type="hidden" name="_method" value="DELETE" />')
							.submit();
				//加了这句之后 下面那就不在执行
				return false;
			}
			
			alert("请先选择要删除的内容");
			return false;
		});
	});
</script>
</head>
<body>
<form id="myForm" action="${ctx }/security/role/list" method="get">
 <!--content start-->
<div class="content">
<div class="table">
  <div class="contentNav"><h1><span class="allIco2 ico_6"></span>角色管理</h1></div>

  <!--内容 start-->
<div class="function">
<shiro:hasRole name="admin">
<ul>
  <li id="liNav">你可以：</li>
  <li><a id="add" href="javascript:void(0);">新增</a></li>
  <li><a id="bulkDel" href="javascript:void(0);">删除</a></li>
</ul>
</shiro:hasRole>
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
   
<div class="addList">
  <table cellpadding="1" cellspacing="1"  class="table_bj">
    <tr class="table_top">
      <td width="5%"><jsp:include page="/common/cms/checkall.jsp"></jsp:include>&nbsp;</td>
      <td class="dot" width="30%">显示名称</td>
      <td>名称</td>
      <td>操作</td>
    </tr>
    <c:choose>
		<c:when test="${not empty page.result}">
			<c:forEach items="${page.result}" var="role" begin="0">
				
						<tr class="table_con">
							<td><input type="checkbox" name="items" id="checkbox" value="${role.id}"/></td>
							<td class="dot">${role.showName}&nbsp;</td>
							<td>${role.name }&nbsp;</td>
							<td>
							<shiro:hasRole name="admin">
								<a href="${ctx }/security/role/${role.id}/edit" >编辑</a>&nbsp;
							</shiro:hasRole>
							</td>
						</tr>				
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr class="table_con"><td colspan="4" align="center"><b>暂无内容</b></td></tr>
		</c:otherwise>
	</c:choose>
  </table>
</div> 

<!--list end--> 

<div class="contactBbutton">
	<jsp:include page="/common/cms/page.jsp" />
</div>
<!--内容 end--> 
  
</div></div>
</form>
  <!--content end-->
</body>
</html>
