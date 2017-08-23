<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=9" />
		<title>权限列表</title>
		<link rel="stylesheet" type="text/css"
			href="${path}/css/manage/base.css" />
		<link rel="stylesheet" type="text/css"
			href="${path}/css/manage/admin.css" />
		<link rel="stylesheet" type="text/css"
			href="${path}/css/manage/page.css" />
		<script language="javascript" type="text/javascript"
			src="${path}/js/jquery.js">
</script>
		<script language="javascript" type="text/javascript"
			src="${path}/js/manage/page.js">
</script>
	</head>
	<body class="list">
		<div class="main-top-hd clearfix">
			<h3 class="cur">
				<a href="javascript:void(0);">权限列表</a>
			</h3>
		</div>
		<form id="actionForm" method="post" action="${path}/admin/action!list.action">
			<div class="main-action-bar">
				<a class="ui-btn-wrap" href="${path}/admin/action!edit.action"> <span
					class="ui-action-btn ui-add-btn-ico" style="font-size: 14px">添加</span>
				</a>
				<jsp:include page="../../page/include/search.jsp"></jsp:include>
			</div>
			<div class="body">
				<table id="table" class="listtable">
					<tr>
						<th class="check">
							<a class="sort"> <input id="selectAll" type="checkbox"
									onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')">
							</a>
						</th>
						<th class="th6">
							<a class="sort">序号</a>
						</th>
						<th class="th6">
							<a class="sort">ID</a>
						</th>
						<th class="th6">
							<a class="sort">父ID</a>
						</th>
						<th class="th6">
							<a class="sort">级数</a>
						</th>
						<th width="8%">
							<div class="Ttime"><a class="sort">权限名称</a></div>
						</th>
						<th width="8%">
							<div class="Ttime"><a class="sort">权限描述</a></div>
						</th>
						<th width="10%">
						    <div class="Ttitle">
							<a class="sort">权限URL</a>
							</div>
						</th>
						<th class="th6">
							<a class="sort">状态</a>
						</th>
						<th class="th6">
							<a class="sort">排序</a>
						</th>
						<th class="th14">
							<span>操作</span>
						</th>
					</tr>
					<c:forEach var="action" items="${pagination.list}" varStatus="e">
						<tr>
							<td>
								<input id="select${e.index}" type="checkbox"
									value="${action.id}"
									onclick="selected(this, '${pagination.rowSize}');">
							</td>
							<td>
								${e.index+1+pagination.rowSize*(pagination.pageNo-1)}
							</td>
							<td>
								${action.id}
							</td>
							<td>
								${action.parentId==0 ? "" : action.parentId}
							</td>
							<td>
								${action.depth}
							</td> 
							<td>
								<div class="Ttime">${action.actionName}</div>
							</td>
							<td>
								<div class="Ttime">${action.actionDesc}</div>
							</td>
							<td>
								 <div class="Ttitle">${action.actionUrl}</div>
							</td>
							<td>
								<a href="#"
									style="color:${action.disable ? disableItem.trueDescription : disableItem.falseDescription}"
									onclick="disableRow('${path}/admin/action!disable.action?id=${action.id}', ${update}, this, '${disableItem.trueStr}', 
										'${disableItem.trueDescription}','${disableItem.falseStr}', '${disableItem.falseDescription}');">
									${action.disable ? disableItem.trueStr : disableItem.falseStr}
								</a>
							</td>
							<td
								onclick="sortRow('actionForm', '${path}/admin/action!sort.action?id=${action.id}', ${update}, this);">
								${action.actionSort}
							</td>
							<td>
								<a href="${path}/admin/action!edit.action?id=${action.id}&edit=true"
									class="edit">修改</a>&nbsp;|&nbsp;
								<a href="#" class="delete"
									onclick="deleteRow('actionForm', '${path}/admin/action!delete.action', '${pagination.rowSize}', this);">删除</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<h4 align="center"
					style="display:${empty pagination.list ? 'block' : 'none' }">
					暂无数据!
				</h4>
				<div class="pagerBar">
					<c:if test="${delete}">
						<a id="deleteButton" class="formButton"
							onclick="deleteRow('actionForm','${path}/admin/action!delete.action', '${pagination.rowSize}');">删&nbsp;&nbsp;除</a>
					</c:if>
				</div>
				<jsp:include page="../../page/common/pager.jsp"></jsp:include>
			</div>
		</form>
	</body>
</html>