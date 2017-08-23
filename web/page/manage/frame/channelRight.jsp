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
		<title>栏目列表</title>
		<link rel="icon" type="image/x-icon" href="favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/page.css"/>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/jquery.js"></script>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/page.js"></script>
    </head>
	<body class="list">
		<div class="main-top-hd clearfix">
	        <h3 class="cur"><a href="javascript:void(0);">
		        	栏目列表
	        </a></h3>
	    </div>
		<form id="channelForm" method="post" action="${path}/admin/channel!list.action">
			<input type="hidden" name="navigateId" value="${navigateId}"/>
			<div class="main-action-bar">
				<c:if test="${add}">
			        <a class="ui-btn-wrap" href="${path}/admin/channel!edit.action?navigateId=${navigateId}">
			            <span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">添加</span>
			        </a>
			    </c:if>
		        <!-- 查询、分页 -->
		        <jsp:include page="../../../page/include/search.jsp"></jsp:include>
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
						<th width="30%">
							<a class="sort">栏目名称</a>
						</th>
						<th width="10%">
							<a class="sort">静态化</a>
						</th>
						<th class="th6">
							<a class="sort">导航</a>
						</th>
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
					<c:forEach var="channel" items="${pagination.list}" varStatus="e">
						<tr>
							<c:if test="${!front}">
								<td>
									<input id="select${e.index}" type="checkbox" value="${channel.id}" onclick="selected(this, '${pagination.rowSize}');">
								</td>
							</c:if>
							<td>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</td>
							<td>${channel.channelName}</td>
							<td>
								${channel["static"] ? '是' : '否'}
							</td>
							<td>
								<a href="#" style="color:${channel.navigetor ? naviItem.trueDescription : naviItem.falseDescription}"
									onclick="disableRow('${path}/admin/channel!navigetor.action?id=${channel.id}', ${update}, this, '${naviItem.trueStr}', 
										'${naviItem.trueDescription}','${naviItem.falseStr}', '${naviItem.falseDescription}');">
									${channel.navigetor ? naviItem.trueStr : naviItem.falseStr}
								</a>
							</td>
							<td>
								<a href="#" style="color:${channel.disable ? disableItem.trueDescription : disableItem.falseDescription}"
									onclick="disableRow('${path}/admin/channel!disable.action?id=${channel.id}', ${update}, this, '${disableItem.trueStr}', 
										'${disableItem.trueDescription}','${disableItem.falseStr}', '${disableItem.falseDescription}');">
									${channel.disable ? disableItem.trueStr : disableItem.falseStr}
								</a>
							</td>
							<td onclick="sortRow('channelForm', '${path}/admin/channel!sort.action?id=${channel.id}', ${update}, this);">${channel.sort}</td>
							<c:if test="${update || delete}">
								<td>
									<c:if test="${update}">
										<a href="${path}/admin/channel!edit.action?id=${channel.id}&edit=true&navigateId=${navigateId}" class="edit">修改</a>
									</c:if>
									<c:if test="${delete}">
										&nbsp;|&nbsp;<a href="#" class="delete" onclick="deleteRow('channelForm', '${path}/admin/channel!delete.action', '${pagination.rowSize}', this);">删除</a>
									</c:if>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
				<div class="pagerBar">
					<c:if test="${delete}">
						<a id="deleteButton" class="formButton" disabled="disabled" onclick="deleteRow('channelForm','${path}/admin/channel!delete.action', '${pagination.rowSize}');">删&nbsp;&nbsp;除</a>
					</c:if>
				</div>
				<jsp:include page="../../../page/common/pager.jsp"></jsp:include>
			</div>
		</form>
	</body>
</html>