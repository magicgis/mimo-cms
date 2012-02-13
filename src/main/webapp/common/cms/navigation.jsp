<%@ taglib prefix="codec" uri="/WEB-INF/tld/codec.tld"%>
<%@ page language="java"  pageEncoding="utf-8"%>
<%@ include file="/common/cms/taglibs.jsp"%>
<link href="${ctx}/resources/css/banner.css" rel="stylesheet" type="text/css" />
<div class="cell">
			<div id="logo">
				<a href="${ctx }/" title="一呼百应服装网导航"><img src="${ctx}/resources/images/logo.gif" width="190" height="53" title="一呼百应服装网购导航" alt="一呼百应服饰导航"/></a>
		  </div>
            <div class="midheader">
			<div class="search">
                <input type="text" class="write" value="${q }" maxlength="20">
		        <input type="button" class="bt" value="搜索" id="search">
		    <div class="clear"></div>
            </div>
            <p>
			 	<a href="${ctx}/search/${codec:urlEncode('圣诞')}">圣诞</a>
			 	<a href="${ctx}/search/${codec:urlEncode('军装风')}">军装风</a>
                <a href="${ctx}/search/${codec:urlEncode('河智苑同款')}">河智苑同款</a>
			 	<a href="${ctx}/search/${codec:urlEncode('情侣')}">情侣</a>
			 	<a href="${ctx}/search/${codec:urlEncode('GUESS')}">GUESS</a>
			 	<a href="${ctx}/search/${codec:urlEncode('party')}">party</a>
			</p></div> 
            <span class="collect_head"><a id="favorite" href="#" title="收藏一呼百应服饰导航">收藏本页</a>|<a href="${ctx}/" title="一呼百应服装导航">首页</a></span>
		</div>
	<div class="nav">
			<ul class="navfirst">
				<li id="home"><a href="${ctx}/" title="首页">首页</a></li>
				<li id="women"><a href="${ctx}/channel/women" title="女装">女装</a></li>
				<li id="man"><a href="${ctx}/channel/man" title="男装">男装</a></li>
				<li id="child"><a href="${ctx}/channel/child" title="童装">童装</a></li>
                <li id="underwear"><a href="${ctx}/channel/underwear" title="内衣">内衣</a></li>
			</ul>
	</div>
	