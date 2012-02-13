<%@ page language="java"  pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/cms/taglibs.jsp" %>
<%@ include file="/common/cms/front-common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>亲，找不到页面了哦</title>
<script type="text/javascript">
	$(function() {
		youboy.we().ready().wouldYouLoveMe().moreQueryMoreHappy("${ctx}/search");
	});
</script>
</head>

<body>
    <%@ include file="/common/cms/navigation.jsp" %>
    <div class="navy">
     <p class="navsecond">
  <span class="navall">所有分类</span>
  <a href="${ctx}/search/${codec:urlEncode('日韩')}" title="日韩" >日韩一族</a>
  <a href="${ctx}/search/${codec:urlEncode('意大利')}" title="意大利">情迷意大利</a>
  <a href="${ctx}/search/${codec:urlEncode('英伦风')}" title="英伦风">英伦风格</a>
  <a href="${ctx}/search/${codec:urlEncode('甜蜜')}" title="甜蜜教主">甜蜜教主</a>
  <a href="${ctx}/search/${codec:urlEncode('波西米亚')}" title="波西米亚风潮">波西米亚风潮</a>
</p></div>
        <div class="main404"><img src="${ctx}/resources/images/404.jpg" width="403" height="244" /><ul>
            <li class="main404-red">很抱歉，你要访问的页面不存在!</li>
           <li>1.你正在访问的页面可能已经删除、更名或暂时不能用</li>
            <li>2.有可能我们的页面和系统正在升级或者维护</li>
            <li>3.直接输入要访问的内容进行搜索</li>
        </ul>
        </div>
   <%@ include file="/common/cms/footer.jsp" %>
</body>
</html>
