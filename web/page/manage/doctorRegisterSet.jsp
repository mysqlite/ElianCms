<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		<title>医生排班设置列表</title>
		<link rel="icon" type="image/x-icon" href="favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/page.css"/>
        <script language="javascript" type="text/javascript" src="${path}/js/jquery.js"></script>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/page.js"></script>
    </head>
	<body class="list">
		<div class="main-top-hd clearfix">
	        <h3 class="cur"><a href="javascript:void(0);">医生排班设置列表</a></h3>
	    </div>
		<form id="doctorRegisterSetForm" method="post" action="${path}/admin/doctorRegisterSet!list.action">
			<div class="main-action-bar">
		        <input type="hidden" name="doctorId" value="${doctorId}"/>
				医生：${doctorName}
				<c:if test="${add}">
			        <a class="ui-btn-wrap" href="${path}/admin/doctorRegisterSet!edit.action?doctorId=${doctorId}">
			            <span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">添加</span>
			        </a>
			    </c:if>
		    </div>
		    <div class="body">
				<table id="table" class="listtable">
					<tr>
						<th class="check">
							<a class="sort">
								<input id="selectAll" type="checkbox" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')"/>
							</a>
						</th>
						<th width="10px">
							<a class="sort">序号</a>
						</th>
						<th width="30px">
							<a class="sort">星期</a>
						</th>
						<th width="30px">
							<a class="sort">班次</a>
						</th>
						<th width="30px">
							<a class="sort">开始时间</a>
						</th>
						<th width="30px">
							<a class="sort">结束时间</a>
						</th>
						<th width="30px">
							<a class="sort">频率</a>
						</th>
						<th width="30px">
							<a class="sort">金额</a>
						</th>
						<th width="30px">
							<a class="sort">门诊类型</a>
						</th>
						<th width="30px">
							<a class="sort">号源</a>
						</th>
						<th width="30px">
							<a class="sort">循环</a>
						</th>
						<c:if test="${update || delete}">
							<th width="50px">
								<span>操作</span>
							</th>
						</c:if>
					</tr>
					<c:forEach var="doctorRegisterSet" items="${pagination.list}" varStatus="e">
						<tr>
							<td>
								<input id="select${e.index}" type="checkbox" value="${doctorRegisterSet.id}" onclick="selected(this, '${pagination.rowSize}');">
							</td>
							<td>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</td>
							<td>
								<c:forEach var="item" items="${weekList}">
									<c:if test="${doctorRegisterSet.weeks == item.key}">${item.value}</c:if>
								</c:forEach>
							</td>
							<td>
								<c:forEach var="item" items="${rankList}">
									<c:if test="${doctorRegisterSet.rank == item.key}">${item.value}</c:if>
								</c:forEach>
							</td>
							<td>${doctorRegisterSet.startTime}</td>
							<td>${doctorRegisterSet.endTime}</td>
							<td>
								<c:forEach var="item" items="${stepList}">
									<c:if test="${doctorRegisterSet.step == item.key}">${item.value}</c:if>
								</c:forEach>
							</td>
							<td>${doctorRegisterSet.amount}</td>
							<td>
								<c:forEach var="item" items="${regTypeList}">
									<c:if test="${doctorRegisterSet.regType == item.key}">${item.value}</c:if>
								</c:forEach>
							</td>
							<td>${doctorRegisterSet.noSource}</td>
							<td>
								<c:if test="${doctorRegisterSet.cycle == 2}">
									<fmt:formatDate value="${doctorRegisterSet.closeTime}" pattern="yyyy-MM-dd"/>
								</c:if>
								<c:if test="${doctorRegisterSet.cycle != 2}">
									<c:forEach var="item" items="${cycleList}">
										<c:if test="${doctorRegisterSet.cycle == item.key}">${item.value}</c:if>
									</c:forEach>
								</c:if>
							</td>
							<c:if test="${update || delete}">
								<td>
									<c:if test="${update}">
										<a href="${path}/admin/doctorRegisterSet!edit.action?id=${doctorRegisterSet.id}&edit=true&doctorId=${doctorId}" class="edit">修改</a>
									</c:if>
									<c:if test="${delete}">
										&nbsp;|&nbsp;<a href="#" class="delete" onclick="deleteRow('doctorRegisterSetForm', '${path}/admin/doctorRegisterSet!delete.action', '${pagination.rowSize}', this);">删除</a>
									</c:if>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
				<div class="pagerBar">
					<c:if test="${delete}">
						<a id="deleteButton" class="formButton" disabled="disabled" onclick="deleteRow('doctorRegisterSetForm','${path}/admin/doctorRegisterSet!delete.action', '${pagination.rowSize}');">删&nbsp;&nbsp;除</a>
					</c:if>
				</div>
				<jsp:include page="../../page/common/pager.jsp"></jsp:include>
			</div>
		</form>
	</body>
</html>