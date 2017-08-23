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
		<title>医生排班列表</title>
		<link rel="icon" type="image/x-icon" href="favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/page.css"/>
        <script language="javascript" type="text/javascript" src="${path}/js/jquery.js"></script>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/page.js"></script>
    </head>
	<body class="list">
		<div class="main-top-hd clearfix">
	        <h3 class="cur"><a href="javascript:void(0);">医生排班列表</a></h3>
	    </div>
		<form id="doctorWorkForm" method="post" action="${path}/admin/doctorWork!list.action">
			<div class="main-action-bar">
		        <input type="hidden" name="doctorId" value="${doctorId}"/>
				医生：${doctorName}
				<c:if test="${add}">
			        <a class="ui-btn-wrap" href="${path}/admin/doctorWork!edit.action?doctorId=${doctorId}">
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
							<a class="sort">班次</a>
						</th>
						<th width="40px">
							<a class="sort">开始时间</a>
						</th>
						<th width="40px">
							<a class="sort">结束时间</a>
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
							<a class="sort">创建人</a>
						</th>
						<th width="40px">
							<a class="sort">创建时间</a>
						</th>
						<th width="20px">
							<a class="sort">停诊</a>
						</th>
						<c:if test="${update || delete}">
							<th width="50px">
								<span>操作</span>
							</th>
						</c:if>
					</tr>
					<c:forEach var="doctorWork" items="${pagination.list}" varStatus="e">
						<tr>
							<td>
								<input id="select${e.index}" type="checkbox" value="${doctorWork.id}" onclick="selected(this, '${pagination.rowSize}');">
							</td>
							<td>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</td>
							<td>
								<c:forEach var="item" items="${rankList}">
									<c:if test="${doctorWork.rank == item.key}">${item.value}</c:if>
								</c:forEach>
							</td>
							<td>
								<fmt:formatDate value="${doctorWork.startTime}" pattern="yyyy-MM-dd HH:mm"/>
							</td>
							<td>
								<fmt:formatDate value="${doctorWork.endTime}" pattern="yyyy-MM-dd HH:mm"/>
							</td>
							<td>${doctorWork.amount}</td>
							<td>
								<c:forEach var="item" items="${regTypeList}">
									<c:if test="${doctorWork.regType == item.key}">${item.value}</c:if>
								</c:forEach>
							</td>
							<td>${doctorWork.noSource}</td>
							<td>${doctorWork.creater.realName}</td>
							<td><fmt:formatDate value="${doctorWork.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>
								<a href="#" style="color:${doctorWork.stopWork ? disableItem.trueDescription : disableItem.falseDescription}"
									onclick="disableRow('${path}/admin/doctorWork!stopWork.action?id=${doctorWork.id}', ${update}, this, '${disableItem.trueStr}', 
										'${disableItem.trueDescription}','${disableItem.falseStr}', '${disableItem.falseDescription}');">
									${doctorWork.stopWork ? disableItem.trueStr : disableItem.falseStr}
								</a>
							</td>
							<c:if test="${update || delete}">
								<td>
									<c:if test="${update}">
										<a href="${path}/admin/doctorWork!edit.action?id=${doctorWork.id}&edit=true&doctorId=${doctorId}" class="edit">修改</a>
									</c:if>
									<c:if test="${delete}">
										&nbsp;|&nbsp;<a href="#" class="delete" onclick="deleteRow('doctorWorkForm', '${path}/admin/doctorWork!delete.action', '${pagination.rowSize}', this);">删除</a>
									</c:if>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
				<div class="pagerBar">
					<c:if test="${delete}">
						<a id="deleteButton" class="formButton" disabled="disabled" onclick="deleteRow('doctorWorkForm','${path}/admin/doctorWork!delete.action', '${pagination.rowSize}');">删&nbsp;&nbsp;除</a>
					</c:if>
					<c:if test="${update}">
						<a id="checkButton" onclick="checkRow('doctorWorkForm','${path}/admin/doctorWork!stopWorks.action', '${pagination.rowSize}','确认停诊？');" class="formButton">停&nbsp;&nbsp;诊</a>
					</c:if>
				</div>
				<jsp:include page="../../page/common/pager.jsp"></jsp:include>
			</div>
		</form>
	</body>
</html>