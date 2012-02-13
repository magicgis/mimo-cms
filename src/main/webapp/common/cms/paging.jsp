<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/common/cms/taglibs.jsp"%>
<c:set var="totalPages" value="${(page.totalPages>2000)?2000:(page.totalPages) }"></c:set>
<div class="global-page">
<c:choose>
	<c:when test="${not empty param.pagingUrl && totalPages > 1 }">
	<c:choose>
		<c:when test="${page.pageNo>1 && not empty param.pagingUrl }">
			<a class="previous-page" href="${param.pagingUrl}/page/${((page.pageNo)-1)>0?(page.pageNo)-1:(page.pageNo)}/${page.orderBy}/${page.order}">上一页</a>
		</c:when>
	</c:choose>
	<c:choose>
		<c:when test="${page.pageNo eq 1}">
			<a class="previous-page" href="${param.pagingUrl}/page/1/${page.orderBy}/${page.order}">1</a>&nbsp;
		</c:when>						  
		<c:otherwise>
			<a href="${param.pagingUrl }/page/1/${page.orderBy}/${page.order}">1</a>&nbsp;</c:otherwise>
		</c:choose>
		<c:choose>
		<c:when test="${totalPages>0}">
		<c:choose>
			<c:when test="${page.pageNo<10}">
				<c:forEach var="i" begin="2" end="${page.pageNo}"><c:choose><c:when test="${page.pageNo eq i }"><a class="previous-page" href="${param.pagingUrl }/page/${i}/${page.orderBy}/${page.order}">${i}</a>&nbsp;</c:when><c:otherwise><a href="${param.pagingUrl }/page/${i}/${page.orderBy}/${page.order}">${i}</a>&nbsp;  </c:otherwise></c:choose></c:forEach>
			</c:when>
			<c:otherwise>  ...&nbsp;  
			<c:forEach var="i" begin="${page.pageNo-4}" end="${page.pageNo}"><c:choose><c:when test="${page.pageNo eq i }"><a class="previous-page" href="${param.pagingUrl }/page/${i}/${page.orderBy}/${page.order}">${i}</a>&nbsp;</c:when><c:otherwise><a href="${param.pagingUrl }/page/${i}/${page.orderBy}/${page.order}">${i}</a>&nbsp;  </c:otherwise></c:choose></c:forEach>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${page.pageNo>=totalPages-4  || totalPages-4<=0}">
				<c:forEach var="i" begin="${page.pageNo+1}" end="${totalPages}"><a href="${param.pagingUrl }/page/${i}/${page.orderBy}/${page.order}">${i}</a>&nbsp;  <c:choose><c:when test="${page.pageNo eq i }"><a class="previous-page" href="${param.pagingUrl }/page/${i}/${page.orderBy}/${page.order}">${i}</a>&nbsp;</c:when></c:choose></c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach var="i" begin="${page.nextPage}" end="${page.pageNo+4}"><c:choose><c:when test="${page.pageNo eq i }"><a class="previous-page"  href="${param.pagingUrl }/page/${i}/${page.orderBy}/${page.order}">${i}</a>&nbsp;</c:when><c:otherwise><a href="${param.pagingUrl }/page/${i}/${page.orderBy}/${page.order}">${i}</a>&nbsp;  </c:otherwise></c:choose></c:forEach>  ...&nbsp;  <a href="${param.pagingUrl }/page/${totalPages}/${page.orderBy}/${page.order}">${totalPages}</a>&nbsp;  
        	</c:otherwise>
		</c:choose>
	
	<c:choose>
		<c:when test="${page.pageNo < totalPages}">
			<a class="next-page" href="${param.pagingUrl }/page/${(page.nextPage) < totalPages?page.nextPage: totalPages}/${page.orderBy}/${page.order}">下一页</a>
		</c:when>
	</c:choose>
</c:when>
</c:choose>
</c:when>
</c:choose>
</div>