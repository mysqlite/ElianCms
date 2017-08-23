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
		<title>订单列表</title>
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
	        <h3 class="cur"><a href="javascript:void(0);">订单列表</a></h3>
	    </div>
		<form id="orderForm" method="post" action="${path}/admin/order!list.action">
			<input type="hidden" name="compId" value="${compId}"/>
			<div class="main-action-bar">
				<c:if test="${add}">
			        <a class="ui-btn-wrap" href="${path}/admin/order!edit.action">
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
						<th class="th6">
							<a class="sort">订单编码</a>
						</th>
						<th class="th14">
							<a class="sort">用户名称</a>
						</th>
						<th class="th14">
							<a class="sort">区域</a>
						</th>
						<th class="th14">
							<a class="sort">支付类型</a>
						</th>
						<th class="th14">
							<a class="sort">状态</a>
						</th>
						<th class="th14">
							<a class="sort">创建时间</a>
						</th>
						<th class="th14">
							<span>操作</span>
						</th>
					</tr>
					<c:forEach var="order" items="${pagination.list}" varStatus="e">
						<tr>
							<td>
								<input id="select${e.index}" type="checkbox" value="${order.id}" onclick="selected(this, '${pagination.rowSize}');">
							</td>
							<td>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</td>
							<td>${order.orderCode}</td>
							<td>${order.user.realName}</td>
							<td>${order.area.areaName}</td>
							<td>${order.deliveryType}</td>
							<td>
								<c:forEach var="item" items="${paymentStatusList}">
									<c:if test="${order.paymentStatus == item.key}">${item.value}</c:if>
								</c:forEach>
							</td>
							<td>${order.createTime}</td>
							<td>
								<a href="${path}/admin/orderDetail!list.action?orderId=${order.id}" class="show">明细</a>
								<c:if test="${delete}">
									&nbsp;|&nbsp;<a href="#" class="delete" onclick="deleteRow('orderForm', '${path}/admin/order!delete.action', '${pagination.rowSize}', this);">删除</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
				<div class="pagerBar">
					<c:if test="${delete}">
						<a id="deleteButton" class="formButton" disabled="disabled" onclick="deleteRow('orderForm','${path}/admin/order!delete.action', '${pagination.rowSize}');">删&nbsp;&nbsp;除</a>
					</c:if>
				</div>
				<jsp:include page="../../page/common/pager.jsp"></jsp:include>
			</div>
		</form>
	</body>
</html>