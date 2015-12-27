<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr"
	uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>添加赞助</title>
<script type="text/javascript">
	function validateForm() {
		var udidElement = document.getElementById("udidId");
		if(udidElement.value.length <=0 || udidElement.value.length > 90) {
			alert("请输入合法的用户号");
			return false;
		}
		
		var priceElement = document.getElementById("priceId");
		if(isNaN(priceElement.value) || priceElement.value <= 0) {
			alert("赞助金额大于0元");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<h2>
		添加赞助
	</h2>
	<h3>
		<c:if test="${success}">
			<font color="red">创建成功</font>
		</c:if>
	</h3>
	<form action="/weather/cms/createDonate" method="post" onsubmit="return validateForm();">
		<input type="hidden" name="orderId" value="${orderId}"/>
		<table>
			<tr>
				<td>用户号:</td>
				<td><input id="udidId" type="text" name="udid" value="${donateForm.udid}" /></td>
				<td><font color="red"><c:if test="${errorCode == '00001' || errorCode == '00002'}">${error}</c:if></font></td>
			</tr>
			<tr>
				<td>赞助留名:</td>
				<td><input type="text" name="name" value="${donateForm.name}" /></td>
				<td><font color="red"><c:if test="${errorCode == '10001'}">${error}</c:if></font></td>
			</tr>
			<tr>
				<td>赞助金额:</td>
				<td><input id="priceId" type="text" name="price" value="${donateForm.price}" /></td>
				<td></td>
			</tr>
			<tr>
				<td>颜色编号:</td>
				<td><select name="colorIndex">
					<c:forEach begin="0" end="24" var="colorIndex">
						<option value="${colorIndex}" <c:if test="${donateForm.colorIndex==colorIndex}">selected="selected"</c:if>>${colorIndex}</option>
					</c:forEach>
				</select></td>
				<td></td>
			</tr>
			<tr>
				<td>所在城市:</td>
				<td><input type="text" name="city" value="${donateForm.city}" /></td>
				<td><font color="red"><c:if test="${errorCode == '10002'}">${error}</c:if></font></td>
			</tr>
			<tr>
				<td>手机来源:</td>
				<td><select name="origin">
					<option value="iphone" <c:if test="${donateForm.origin=='iphone'}">selected="selected"</c:if>>iphone</option>
					<option value="android" <c:if test="${donateForm.origin=='android'}">selected="selected"</c:if>>android</option>
				</select></td>
				<td></td>
			</tr>
			<tr>
				<td><input type="submit" value="提交" /></td>
			</tr>
		</table>
	</form>
</body>
</html>