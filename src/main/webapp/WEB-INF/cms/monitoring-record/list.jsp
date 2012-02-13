<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/cms/taglibs.jsp"%>
<%@include file="/common/cms/common-header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<form id="myForm" action="${ctx }/monitoring-record/list" method="get">
 <!--content start-->
<div class="content">
<div class="table">
  <div class="contentNav"><h1><span class="allIco2 ico_6"></span>日志管理</h1></div>

  <!--内容 start-->
<div class="function">
  <ul>
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
      <td>操作人</td>
      <td>操作</td>
      <td>来源</td>
      <td>目标</td>
      <td>耗时</td>
      <td>记录时间</td>
    </tr>
    <c:choose>
		<c:when test="${not empty page.result}">
			<c:forEach items="${page.result}" var="monitoringRecord" begin="0">
				<tr class="table_con">
					<td><input type="checkbox" name="items" id="checkbox" value="${template.id}"/></td>
					<td>${monitoringRecord.actor}&nbsp;</td>
					<td>${monitoringRecord.action}&nbsp;</td>
					<td>
						${monitoringRecord.source}&nbsp;
					</td>
					<td>
						${monitoringRecord.target}&nbsp;
					</td>
					<td>
						${monitoringRecord.elapsedTime}毫秒&nbsp;
					</td>
					<td class="date">
						${monitoringRecord.createTime}&nbsp;
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
