<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/cms/taglibs.jsp"%>
<%@include file="/common/cms/common-header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	$(function(){
		
		$("#online").click(function(){
			var items = mimo.select();
			if(items && items.length > 0 && confirm("你确定要把选中的内容还原吗?")){
				var itemsAsString = items.join(",");
				$.ajax({
					url : "${ctx}/article/online",
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
		
		$("#del").click(function(){
			var items = mimo.select();
			if(items && items.length > 0 && confirm("你确定要删除这些内容吗?")){
				$('input[name="_method"]').remove();
				$("#myForm").attr("action", "${ctx}/article/delete")
							.attr("method","post")
							.append('<input type="hidden" name="_method" value="DELETE" />')
							.submit();
				
				return false;
			}
			
			alert("请先选择要删除的内容");
			return false;
		});
		
		 $(".date").each(function(){
			 var $this = $(this);
			 var dateAsLong = $this.text();
	 		 $this.empty().append(mimo.formatDate(dateAsLong)+"&nbsp;");
		  });
		
	});
</script>
</head>
<body>
<form id="myForm" action="${ctx }/article/list" method="get">
 <!--content start-->
<div class="content">
<div class="table">
  <div class="contentNav"><h1><span class="allIco2 ico_6"></span>文章回收站管理</h1></div>

  <!--内容 start-->
<div class="function">
<ul>
  <li id="liNav">你可以：</li>
  <shiro:hasPermission name="article:delete">
  <li><a id="del" href="javascript:void(0);">删除</a></li>
  </shiro:hasPermission>
  <shiro:hasPermission name="article:edit">
  <li><a id="online" href="javascript:void(0);">还原</a></li>
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
						${article.modifyTime}&nbsp;
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
