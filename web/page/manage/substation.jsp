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
		<title>分站列表</title>
		<link rel="icon" type="image/x-icon" href="favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/page.css"/>
        <script language="javascript" type="text/javascript" src="${path}/js/jquery.js"></script>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/page.js"></script>
    </head>
	<body class="list">
		<div class="main-top-hd clearfix">
	        <h3 class="cur"><a href="javascript:void(0);">分站列表</a></h3>
	    </div>
		<form id="substationForm" method="post" action="${path}/admin/substation!list.action">
			<div class="main-action-bar">
		        <a class="ui-btn-wrap" href="${path}/admin/substation!edit.action">
		            <span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">添加</span>
		        </a>
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
							<a class="sort">分站名称</a>
						</th>
						<th width="10%">
							<a class="sort">联系电话</a>
						</th>
						<th width="30%">
							<a class="sort">分站地址</a>
						</th>
						<th class="th6">
							<a class="sort">状态</a>
						</th>
						<c:if test="${update || delete}">
							<th class="th14">
								<span>操作</span>
							</th>
						</c:if>
					</tr>
					<c:forEach var="substation" items="${pagination.list}" varStatus="e">
						<tr>
							<td>
								<input id="select${e.index}" type="checkbox" value="${substation.id}" onclick="selected(this, '${pagination.rowSize}');">
							</td>
							<td>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</td>
							<td>${substation.subName}</td>
							<td>${substation.phone}</td>
							<td>${substation.address}</td>
							<td>
								<div class="statusDiv-wrap">
                                <a href="#" onclick="showStatusDiv('statusDiv', ${e.index}, this);">
                                   <c:forEach var="item" items="${substationStatusList}">
                                       <c:if test="${substation.status == item.key}"><span id="status${e.index}" style="color: ${item.description};">${item.value}</span></c:if>
                                   </c:forEach>
                                </a>
                                <c:if test="${update}">
                                  <c:if test="${substation.status < 4}">
                                <div id="statusDiv${e.index}" class="statusDiv">
                                   <c:forEach var="item" items="${mainSubstationStatusList}">
                                       <a onclick="statusRow('${path}/admin/substation!status.action?id=${substation.id}', 'status', 'statusDiv', ${e.index}, '${item.key}', '${item.value}', '${item.description}');">${item.key}</a><br/>
                                   </c:forEach>
                                </div>
                                </c:if>
                                </c:if>
                         </div>
							</td>
							<c:if test="${update || delete}">
								<td>
									<c:if test="${update}">
										<a href="${path}/admin/substation!edit.action?id=${substation.id}&edit=true" class="edit">修改</a>
									</c:if>
									<c:if test="${delete}">
										&nbsp;|&nbsp;<a href="#" class="delete" onclick="deleteRow('substationForm', '${path}/admin/substation!delete.action', '${pagination.rowSize}', this);">删除</a>
									</c:if>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">没有符合条件的数据!</h4>
				<div class="listbar">
					<c:if test="${delete}">
						<a id="deleteButton" class="formButton" disabled="disabled" onclick="deleteRow('substationForm','${path}/admin/substation!delete.action', '${pagination.rowSize}');">删&nbsp;&nbsp;除</a>
					</c:if>
				</div>
				<jsp:include page="../../page/common/pager.jsp"></jsp:include>
			</div>
		</form>
	</body>
</html>