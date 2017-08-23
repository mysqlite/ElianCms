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
		<title>编辑站点</title>
		<link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" />
		<link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />
	</head>
	<body class="input goods">
		<div class="body">
			<form name="editRoleForm" action="${path}/admin/site!save.action" method="post">
				<input type="hidden" name="site.id" value="${site.id}">
				<input type="hidden" name="site.version" value="${site.version}">
				<div class="bar">
					站点详细信息
				</div>
				<table class="inputTable tabContent">
					<tr>
						<th>
						    <span>*</span>
							状态
						</th>
						<td>
								<c:if test="${site.status==0}">未开通</c:if>
								<c:if test="${site.status==1}">已开通</c:if>
								<c:if test="${site.status==2}">已注销</c:if>
								<c:if test="${site.status==3}">已删除</c:if>
						</td>
					</tr>
					<tr>
						<th>
						    <span>*</span>
							站点名称
						</th>
						<td>
							${site.siteName}
						</td>
					</tr>
					<tr>
						<th>
						    <span>*</span>
							站点简称
						</th>
						<td>
							${site.shortName}
						</td>
					</tr>
					<tr>
						<th>
						    <span>*</span>
							组织类型
						</th>
						<td>
							${site.comType}
						</td>
					</tr>
					<tr>
						<th>
						    <span>*</span>
							组织ID
						</th>
						<td>
							${site.comId}
						</td>
					</tr>
					<tr>
						<th>
						    <span>*</span>
							排序
						</th>
						<td>
							${site.siteSort}
						</td>
					</tr>
					<tr>
						<th>
						    <span>*</span>
							等级
						</th>
						<td>
							${site.gradeId}
						</td>
					</tr>
					<tr>
						<th>
						    <span>*</span>
							模板
						</th>
						<td>
							${site.tempId}
						</td>
					</tr>
					<tr>
						<th>
						    <span>*</span>
							FTP
						</th>
						<td>
							${site.ftpId}
						</td>
					</tr>
					<tr>
						<th>
						    <span>*</span>
							站点域名
						</th>
						<td>
							${site.domain}
						</td>
					</tr>
					<tr>
						<th>
						    <span>*</span>
							其他域名
						</th>
						<td>
							${site.alias}
						</td>
					</tr>
					<tr>
						<th>
						    <span>*</span>
							站点重定向
						</th>
						<td>
							${site.redirect}
						</td>
					</tr>
					<tr>
						<th>
						    <span>*</span>
							LOGO
						</th>
						<td>
							${site.logo}
						</td>
					</tr>
					<tr>
						<th>
						    <span>*</span>
							站点路径
						</th>
						<td>
							${site.sitePath}
						</td>
					</tr>
				</table>
				<div class="listbar">
					<a class="formButton" href="${path}/admin/site!edit.action?id=${site.id}&edit=true">修&nbsp;&nbsp;改</a>
					<a class="formButton" onclick="this.form.submit();">审&nbsp;&nbsp;核</a>
					<%--<a class="formButton" href="javascript:void(0);" style="color: green">预&nbsp;&nbsp;览</a>
					--%><a class="formButton" href="${path}/admin/site!list.action">返&nbsp;&nbsp; 回</a>
				</div>
			</form>
		</div>
	</body>
</html>