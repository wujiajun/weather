<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>视频列表</title>
<script type="text/javascript" src="http://js.51juzhai.com/js/jquery/jquery-1.6.3.min.js"></script>
<script>
</script>
</head>
<body>
	<table border="0" cellspacing="4">
		<tr>
			<td colspan="7"><c:forEach var="pageId" items="${pager.showPages}">
				<c:choose>
					<c:when test="${pageId!=pager.currentPage}">
						<a href="/weather/cms/listDonate?page=${pageId}">${pageId}</a>
					</c:when>
					<c:otherwise>
						<strong>${pageId}</strong>
					</c:otherwise>
				</c:choose>
			</c:forEach></td>
		</tr>
		<tr style="background-color: #CCCCCC;">
			<td width="300">赞助人号</td>
			<td width="100">赞助者</td>
			<td width="100">累计金额</td>
			<td width="100">城市</td>
			<td width="100">颜色序号</td>
			<td width="100">最后赞助时间</td>
			<td width="100">来源手机</td>
		</tr>
		<c:forEach var="donate" items="${donateList}">
			<tr>
				<td><c:out value="${donate.udid}" /></td>
				<td><c:out value="${donate.name}" /></td>
				<td><c:out value="${donate.sum}"></c:out></td>
				<td><c:out value="${donate.city}"></c:out></td>
				<td><c:out value="${donate.colorIndex}"></c:out></td>
				<td><fmt:formatDate value="${donate.lastDonateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><c:out value="${donate.origin}"></c:out></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7"><c:forEach var="pageId" items="${pager.showPages}">
				<c:choose>
					<c:when test="${pageId!=pager.currentPage}">
						<a href="/weather/cms/listDonate?page=${pageId}">${pageId}</a>
					</c:when>
					<c:otherwise>
						<strong>${pageId}</strong>
					</c:otherwise>
				</c:choose>
			</c:forEach></td>
		</tr>
	</table>
</body>
</html>
