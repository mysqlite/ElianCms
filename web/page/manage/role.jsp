<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML >
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=9" />
		<title>角色列表</title>
		<link rel="icon" type="image/x-icon" href="favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/page.css"/>
        <script language="javascript" type="text/javascript" src="${path}/js/jquery.js"></script>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/page.js"></script>
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/jquery_message.css"/>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/jquery_message.js"></script>
        <script type="text/javascript">
			function cccc(){
			   $.message('操作成功!','success');
			}
		</script>
    </head>
	<body class="list">
		<div class="main-top-hd clearfix">
	        <h3 class="cur"><a href="javascript:void(0);">用户组列表</a></h3>
	    </div>
		<form id="roleForm" method="post" action="${path}/admin/role!list.action">
			<div class="main-action-bar">
				<c:if test="${add}">
			        <a class="ui-btn-wrap" href="${path}/admin/role!edit.action">
			            <span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">添加</span>
			        </a>
			    </c:if>
		        <!-- 查询、分页 -->
		        <jsp:include page="../../page/include/search.jsp"></jsp:include>
		    </div>
		    <div class="body">
				<table id="table" class="listtable">
					<tr>
						<th class="check">
							<a class="sort">
								<input id="selectAll" type="checkbox" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')"/>
							</a>
						</th>
						<th class="th6">
							<a class="sort">序号</a>
						</th>
						<th width="20%">
							<a class="sort">用户组名称</a>
						</th>
						<th width="30%">
							<a class="sort">用户组描述</a>
						</th>
						<c:if test="${mainStation}">
							<th width="10%">
								<a class="sort">是否默认</a>
							</th>
						</c:if>
						<th class="th6">
							<a class="sort">状态</a>
						</th>
						<th class="th6">
							<a class="sort">排序</a>
						</th>
						<c:if test="${update || delete}">
							<th class="th14">
								<span>操作</span>
							</th>
						</c:if>
					</tr>
					<c:forEach var="role" items="${pagination.list}" varStatus="e">
						<tr>
							<td>
								<input id="select${e.index}" type="checkbox" value="${role.id}" onclick="selected(this, '${pagination.rowSize}');">
							</td>
							<td>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</td>
							<td>${role.roleName}</td>
							<td>${role.roleDesc}</td>
							<c:if test="${mainStation}">
								<td>
									<a href="#" style="color:${role.default?defaultItem.trueDescription : defaultItem.falseDescription}"
										onclick="disableRow('${path}/admin/role!defaultData.action?id=${role.id}', ${update}, this, '${defaultItem.trueStr}', 
											'${defaultItem.trueDescription}','${defaultItem.falseStr}', '${defaultItem.falseDescription}');">
										${role.default ? defaultItem.trueStr : defaultItem.falseStr}
									</a>
								</td>
							</c:if>
							<td>
								<a href="#" style="color:${role.disable ? disableItem.trueDescription : disableItem.falseDescription}"
									onclick="disableRow('${path}/admin/role!disable.action?id=${role.id}', ${update}, this, '${disableItem.trueStr}', 
										'${disableItem.trueDescription}','${disableItem.falseStr}', '${disableItem.falseDescription}');">
									${role.disable ? disableItem.trueStr : disableItem.falseStr}
								</a>
							</td>
							<td onclick="sortRow('roleForm', '${path}/admin/role!sort.action?id=${role.id}', ${update}, this);">${role.roleSort}</td>
							<c:if test="${update || delete}">
								<td>
									<c:if test="${update}">
										<a href="${path}/admin/role!edit.action?id=${role.id}&edit=true" class="edit">修改</a>
										&nbsp;|&nbsp;<a href="${path}/admin/roleAction!edit.action?id=${role.id}&edit=true" class="edit">权限</a>
									</c:if>
									<c:if test="${delete}">
										&nbsp;|&nbsp;<a href="#" class="delete" onclick="deleteRow('roleForm', '${path}/admin/role!delete.action', '${pagination.rowSize}', this);">删除</a>
									</c:if>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
				<div class="pagerBar">
					<c:if test="${delete}">
						<a id="deleteButton" class="formButton" disabled="disabled" onclick="deleteRow('roleForm','${path}/admin/role!delete.action', '${pagination.rowSize}');">删&nbsp;&nbsp;除</a>
					</c:if>
				</div>
				<jsp:include page="../../page/common/pager.jsp"></jsp:include>
			</div>
		</form>
	</body>
</html>