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
		<title>订单明细列表</title>
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
	        <h3 class="cur"><a href="javascript:void(0);">订单明细列表</a></h3>
	    </div>
		<form id="orderDetailForm" method="post" action="${path}/admin/orderDetail!list.action">
			<div class="main-action-bar">
				<a id="deleteButton" class="formButton" onclick="window.history.back(); return false;">返&nbsp;&nbsp;回</a>
		        <input type="hidden" name="orderId" value="${orderId}"/>
				订单编码：${orderCode}
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
							<a class="sort">企业</a>
						</th>
						<th class="th14">
							<a class="sort">商品类型</a>
						</th>
						<th class="th14">
							<a class="sort">商品名称</a>
						</th>
						<th class="th14">
							<a class="sort">商品数量</a>
						</th>
						<th class="th14">
							<a class="sort">创建时间</a>
						</th>
						<th class="th14">
							<a class="sort">单价</a>
						</th>
						<th class="th14">
							<a class="sort">单位</a>
						</th>
						<th class="th14">
							<a class="sort">物流号</a>
						</th>
						<c:if test="${delete}">
							<th class="th14">
								<span>操作</span>
							</th>
						</c:if>
					</tr>
					<c:forEach var="orderDetail" items="${pagination.list}" varStatus="e">
						<tr>
							<td>
								<input id="select${e.index}" type="checkbox" value="${orderDetail.id}" onclick="selected(this, '${pagination.rowSize}');">
							</td>
							<td>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</td>
							<td>${orderDetail.company.name}</td>
							<td>
								<c:forEach var="item" items="${commodityTypeList}">
									<c:if test="${orderDetail.commodityType == item.key}">${item.value}</c:if>
								</c:forEach>
							</td>
							<td>${orderDetail.commodityName}</td>
							<td>${orderDetail.commodityCount}</td>
							<td>
								<fmt:formatDate value="${orderDetail.createTime}" pattern="yyyy-MM-dd HH:mm"/>
							</td>
							<td>${orderDetail.discountedPrice}</td>
							<td>${orderDetail.commodityUnit}</td>
							<td>${orderDetail.logisticNumber}</td>
							<c:if test="${delete}">
								<td>
									<c:if test="${delete}">
										<a href="#" class="delete" onclick="deleteRow('orderDetailForm', '${path}/admin/orderDetail!delete.action', '${pagination.rowSize}', this);">删除</a>
									</c:if>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
				<div class="pagerBar">
					<c:if test="${delete}">
						<a id="deleteButton" class="formButton" disabled="disabled" onclick="deleteRow('orderDetailForm','${path}/admin/orderDetail!delete.action', '${pagination.rowSize}');">删&nbsp;&nbsp;除</a>
					</c:if>
				</div>
				<jsp:include page="../../page/common/pager.jsp"></jsp:include>
			</div>
		</form>
	</body>
</html>