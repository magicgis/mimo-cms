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
			location.href = "${ctx}/article/create";
		});
		
		$("#offline").click(function(){
			var items = mimo.select();
			if(items && items.length > 0 && confirm("你确定要把选中的内容放入回收站吗?")){
				var itemsAsString = items.join(",");
				$.ajax({
					url : "${ctx}/article/offline",
					type : "POST",
					data : {"_method" : "PUT", "items" : itemsAsString},
					dataType : "json",
					success : function(data){
						if(data && data.type && (data.type.indexOf("ERROR") != -1)){
							alert(data.message);
							return;
						}
						
						location.reload();
					}
				});
				
				return false;
			}
			
			alert("请先选择要放入回收站的内容");
			return false;
		});
		
		$("#on-top").click(function(){
			var items = mimo.select();
			if(items && items.length > 0 && confirm("你确定要把选中的内容置顶吗?")){
				var itemsAsString = items.join(",");
				$.ajax({
					url : "${ctx}/article/on-top",
					type : "POST",
					data : {"_method" : "PUT", "items" : itemsAsString},
					dataType : "json",
					success : function(data){
						if(data && data.type && (data.type.indexOf("ERROR") != -1)){
							alert(data.message);
							return;
						}
						
						location.reload();
					}
				});
				
				return false;
			}
			
			alert("请先选择置顶的内容");
			return false;
		});
		
		$("#not-on-top").click(function(){
			var items = mimo.select();
			if(items && items.length > 0 && confirm("你确定要把选中的内容取消置顶吗?")){
				var itemsAsString = items.join(",");
				$.ajax({
					url : "${ctx}/article/not-on-top",
					type : "POST",
					data : {"_method" : "PUT", "items" : itemsAsString},
					dataType : "json",
					success : function(data){
						if(data && data.type && (data.type.indexOf("ERROR") != -1)){
							alert(data.message);
							return;
						}
						
						location.reload();
					}
				});
				
				return false;
			}
			
			alert("请先选择取消置顶的内容");
			return false;
		});
		
		$("#not-comments").click(function(){
			var items = mimo.select();
			if(items && items.length > 0 && confirm("你确定要把选中的内容禁止评论吗?")){
				var itemsAsString = items.join(",");
				$.ajax({
					url : "${ctx}/article/not-comments",
					type : "POST",
					data : {"_method" : "PUT", "items" : itemsAsString},
					dataType : "json",
					success : function(data){
						if(data && data.type && (data.type.indexOf("ERROR") != -1)){
							alert(data.message);
							return;
						}
						
						location.reload();
					}
				});
				
				return false;
			}
			
			alert("请先选择禁止评论的内容");
			return false;
		});
		
		$("#allow-comments").click(function(){
			var items = mimo.select();
			if(items && items.length > 0 && confirm("你确定要把选中的内容允许评论吗?")){
				var itemsAsString = items.join(",");
				$.ajax({
					url : "${ctx}/article/allow-comments",
					type : "POST",
					data : {"_method" : "PUT", "items" : itemsAsString},
					dataType : "json",
					success : function(data){
						if(data && data.type && (data.type.indexOf("ERROR") != -1)){
							alert(data.message);
							return;
						}
						
						location.reload();
					}
				});
				
				return false;
			}
			
			alert("请先选择允许评论的内容");
			return false;
		});
		
	});
</script>
</head>
<body>
<form id="myForm" action="${ctx }/article/list" method="get">
 <!--content start-->
<div class="content">
<div class="table">
  <div class="contentNav"><h1><span class="allIco2 ico_6"></span>文章管理</h1></div>

  <!--内容 start-->
<div class="function">
<ul>
  <li id="liNav">你可以：</li>
  <shiro:hasPermission name="article:create">
  <li><a id="add" href="javascript:void(0);">新增</a></li>
  </shiro:hasPermission>
  <shiro:hasPermission name="article:edit">
  <li><a id="offline" href="javascript:void(0);">回收站</a></li>
  <li><a id="on-top" href="javascript:void(0);">置顶</a></li>
  <li><a id="not-on-top" href="javascript:void(0);">不置顶</a></li>
  <li><a id="not-comments" href="javascript:void(0);">禁止评论</a></li>
  <li><a id="allow-comments" href="javascript:void(0);">允许评论</a></li>
  </shiro:hasPermission>
  </ul>
  <div class="clear"></div>
  </div>   
  
  
   <!--信息搜索 start-->
    <div class="search"><ul>
    <li>名称： <input type="text" name="name" value="${param.name }" class="input1" /></li>
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
      <td>所属栏目</td>
      <td>文章标题</td>
      <td>文章来源</td>
      <td>最后更新时间</td>
      <td>属性</td>
      <td>操作</td>
    </tr>
    <c:choose>
		<c:when test="${not empty page.result}">
			<c:forEach items="${page.result}" var="article" begin="0">
				<tr class="table_con">
					<td><input type="checkbox" name="items" id="checkbox" value="${article.id}"/></td>
					<td>${article.channel.name}&nbsp;</td>
					<td>${article.title}&nbsp;</td>
					<td>
						${article.source}&nbsp;
					</td>
					<td class="date">
						${article.modifyTime}
					</td>
					<td>
						${article.onTop == 'true' ? '[置顶]&nbsp;' : '[非置顶]&nbsp;' }
						${article.forbidComments == 'true' ? '[禁止评论]&nbsp;' : '[允许评论]&nbsp;' }
						[优先级:${article.priority }]&nbsp;
					</td>
					<td>
						<shiro:hasPermission name="article:edit">
							<a href="${ctx }/article/${article.id}/edit" >编辑</a>
						</shiro:hasPermission>
						&nbsp;
					</td>
				</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr class="table_con"><td colspan="7" align="center"><b>暂无内容</b></td></tr>
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
