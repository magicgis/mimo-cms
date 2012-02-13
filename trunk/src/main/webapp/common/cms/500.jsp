<%@ page language="java"  pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/cms/taglibs.jsp" %>
<%@ include file="/common/cms/front-common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>亲，程序错误啦</title>
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
</p>
  </div>
        <div class="main404"><img src="${ctx}/resources/images/500.jpg" width="403" height="244" /><ul>
           <li class="main500-title">错误代码：<span>500 </span>(服务器内部出错)</li>
           <li><span>网站无法显示该页面！</span><span>  服务器可能病了</span></li>
            <li><span>该网站有程序错误 </span><span>该网站正在维护</span></li>
            <li class="main500-red">你可以尝试以下操作：</li>
            <li>按"F5"键进行刷新</li>
            <li><a href="#">返回首页</a></li>
        </ul>
        </div>
     <%@ include file="/common/cms/footer.jsp" %>
</body>
</html>
