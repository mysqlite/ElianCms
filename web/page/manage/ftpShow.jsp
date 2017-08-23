<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>编辑FTP</title>
		<link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" />
		<link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />
	</head>
	<body class="input goods">
		<div class="body">
			<form name="editRoleForm" action="${path}/admin/ftp!save.action" method="post">
				<input type="hidden" name="ftp.id" value="${ftp.id}">
				<input type="hidden" name="ftp.version" value="${ftp.version}">
				<div class="bar">
					FTP详细信息
				</div>
				<table class="inputTable tabContent">
					<tr>
						<th>
							是否启用
						</th>
						<td>
							<c:if test="${ftp.disable}">是</c:if>
							<c:if test="${!ftp.disable}">否</c:if>
						</td>
					</tr>
					<tr>
						<th>
							FTP名称
						</th>
						<td>
							${ftp.ftpName}
						</td>
					</tr>
					<tr>
						<th>
							排序
						</th>
						<td>
							${ftp.ftpSort}
						</td>
					</tr>
					<tr>
						<th>
							服务器IP
						</th>
						<td>
							${ftp.serverIp}
						</td>
					</tr>
					<tr>
						<th>
							端口
						</th>
						<td>
							${ftp.ftpPort}
						</td>
					</tr>
					<tr>
						<th>
							登陆账号
						</th>
						<td>
							${ftp.account}
						</td>
					</tr>
					<tr>
						<th>
							登陆密码
						</th>
						<td>
							${ftp.account}
						</td>
					</tr>
					<tr>
						<th>
							编码
						</th>
						<td>
							${ftp.encoding}
						</td>
					</tr>
					<tr>
						<th>
							访问超时
						</th>
						<td>
							${ftp.timeout}
						</td>
					</tr>
					<tr>
						<th>
							ftp路径
						</th>
						<td>
							${ftp.ftpPath}
						</td>
					</tr>
					<tr>
						<th>
							访问地址
						</th>
						<td>
							${ftp.ftpUrl}
						</td>
					</tr>
				</table>
				<div class="listbar">
					<a class="formButton" href="${path}/admin/ftp!edit.action?id=${ftp.id}&edit=true">修&nbsp;&nbsp;改</a>
					<a class="formButton" onclick="this.form.submit();">审&nbsp;&nbsp;核</a>
					<%--<a class="formButton" href="javascript:void(0);" style="color: green">预&nbsp;&nbsp;览</a>
					--%><a class="formButton" href="${path}/admin/ftp!list.action">返&nbsp;&nbsp; 回</a>
				</div>
			</form>
		</div>
	</body>
</html>