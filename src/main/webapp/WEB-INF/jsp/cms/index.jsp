<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>后台设置</title>
<script type="text/javascript">
	function showChild(divId) {
		var div_obj = document.getElementById(divId);
		if (div_obj.style.display == '') {
			div_obj.style.display = "none";
		} else {
			div_obj.style.display = '';
		}
	}
</script>
<style type="text/css">
.headInfo {
	margin: 5px auto;
	width: 100%;
	background-color: #104E8B;
}

.leftInfo {
	width: 160px;
	height: 400px;
	float: left;
	position: absolute;
	left: 8px;
}

.leftInfo ul {
	padding: 1px;
	margin: 4px;
}

.leftInfo ul li {
	list-style-image: none;
	list-style-type: none;
	margin-bottom: 5px;
	text-align: center;
	padding: 5px;
	color: white;
	background-color: #104E8B;
}

.leftInfo ul div {
	background-color: white;
}

.leftInfo ul div ul li {
	list-style-image: none;
	list-style-type: none;
}

a {
	color: white;
	text-decoration: none;
}

a:hover {
	color: white
}

.rightInfo {
	float: left;
	margin-left: 170px;
	width: 1200px;
	height: 800px;
}

.child {
	background-color: #A4D3EE;;
}
</style>
</head>
<body>
	<div class="headInfo">
		<table width="100%">
			<tr>
				<td style="font-size: 22px; padding-left: 10px; color: white;">Easylife@后台管理</td>
			</tr>
			<tr>
				<td style="text-align: right; padding-right: 10px; color: white;">admin</td>
			</tr>
		</table>
	</div>
	<div class="leftInfo">
		<ul>
			<li>
				<a href="javascript:showChild('managerVideo');">赞助信息</a>
				<ul style="display: none;" id="managerVideo" class="child">
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/weather/cms/listDonate"
						target="config">赞助列表</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/weather/cms/prepareCreateDonate"
						target="config">手动添加赞助</a>
					</li>
				</ul>
			</li>
		</ul>
	</div>
	<div class="rightInfo">
		<iframe frameborder="0" width="100%" name="config" height="3000">
		</iframe>
	</div>
</body>
</html>