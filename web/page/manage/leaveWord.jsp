<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=9"/>
		<title>留言板列表</title>
		<jsp:include page="../../page/include/headList.jsp"></jsp:include>
	</head>
<body class="list">
<div class="main-top-hd clearfix">
	<h3 <c:if test="${status==0}">class="cur"</c:if>><a href="javascript:void(0);" onclick="window.location='${path}/admin/leaveWord!list.action?status=0'">新留言列表</a></h3>
	<h3 <c:if test="${status==4}">class="cur"</c:if>><a href="javascript:void(0);" onclick="window.location='${path}/admin/leaveWord!list.action?status=4'">审核列表</a></h3>
	<h3 <c:if test="${status==1}">class="cur"</c:if>><a href="javascript:void(0);" onclick="window.location='${path}/admin/leaveWord!list.action?status=1'">已回复列表</a></h3>
	<h3 <c:if test="${status==2}">class="cur"</c:if>><a href="javascript:void(0);" onclick="window.location='${path}/admin/leaveWord!list.action?status=2'">举报列表</a></h3>
	<h3 <c:if test="${status==3}">class="cur"</c:if>><a href="javascript:void(0);" onclick="window.location='${path}/admin/leaveWord!list.action?status=3'">禁用列表</a></h3>
</div>
<form id="leaveWordForm" method="post" action="${path}/admin/leaveWord!list.action">
	<input type="hidden" name="status" value="${status }"/>
<div class="main-action-bar">
    <!-- 查询、分页 -->
	<jsp:include page="../../page/include/search.jsp"></jsp:include>
</div>
<div class="body">
	<table id="listtable" class="listtable">
		<tbody>
			<tr>
			    <th class="check"><input class="allCheck" type="checkbox"  id="selectAll" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')"> </th>
			    <th class="w70">序号</th>
			    <th>留言主题</th>
			    <th>真实姓名</th>
			    <th>手机号码</th>
			    <th>电子邮箱</th>
			    <th>留言日期</th>
			    <th class="w150">
				    <c:if test="${update || delete || show}">
				    	<span>操作</span>
				    </c:if>
			    </th>
			</tr>
		   <c:forEach var="item" items="${pagination.list}" varStatus="e">
		   		<tr>
				    <td>
				    	<input id="select${e.index}" type="checkbox" value="${item.id}" name=ids onclick="javascript:selected(this, '${pagination.rowSize}');">
				    </td>
				    <td>
						<span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span>
					</td>
				    <td>
						<span>${item.title}</span>
					</td>
				    <td>
						<span>${item.realName}</span>
					</td>
				    <td>
						<span>${item.phoneNumber}</span>
					</td>
				    <td>
						<span>${item.email}</span>
					</td>
				    <td>
						<span><f:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
					</td>
					<c:if test="${update || delete || show}">
				    	<td>
				    		<c:if test="${show}">
				    			<a href="leaveWord!show.action?leaveWord.id=${item.id}&status=${status}" class="show">查看</a>
				    		</c:if>
				    		<c:if test="${update}">
				    			<c:if test="${status==0}">
									&nbsp;|&nbsp;<a href="leaveWord!edit.action?leaveWord.id=${item.id}&status=${status}">回复</a>
									&nbsp;|&nbsp;<a href="leaveWord!audit.action?leaveWord.id=${item.id}&status=${status}">审核</a>
								</c:if>
								<c:if test="${status==1}">
									&nbsp;|&nbsp;<a href="leaveWord!edit.action?leaveWord.id=${item.id}&status=${status}&edit=true">修改</a>
								</c:if>
								<c:if test="${status==4}">
									&nbsp;|&nbsp;<a href="leaveWord!edit.action?leaveWord.id=${item.id}&status=${status}">回复</a>
								</c:if>
							</c:if>
				    		<c:if test="${delete}">
								<c:if test="${status==1}">
									&nbsp;|&nbsp;<a href="#" class="delete" onclick="deleteRow('leaveWordForm', '${path}/admin/leaveWord!forbidden.action', '${pagination.rowSize}', this,'禁用后前台将不再显示，是否继续？');">禁用</a>
								</c:if>
								<c:if test="${status==2}">
									&nbsp;|&nbsp;<a href="#" class="delete" onclick="deleteRow('leaveWordForm', '${path}/admin/leaveWord!release.action', '${pagination.rowSize}', this);">解除</a>
									&nbsp;|&nbsp;<a href="#" class="delete" onclick="deleteRow('leaveWordForm', '${path}/admin/leaveWord!forbidden.action', '${pagination.rowSize}', this,'禁用后前台将不再显示，是否继续？');">禁用</a>
								</c:if>
								<c:if test="${status==3}">
									&nbsp;|&nbsp;<a href="#" class="delete" onclick="deleteRow('leaveWordForm', '${path}/admin/leaveWord!release.action', '${pagination.rowSize}', this);">解除</a>
								</c:if>
								&nbsp;|&nbsp;<a href="#" class="delete" onclick="deleteRow('leaveWordForm', '${path}/admin/leaveWord!delete.action', '${pagination.rowSize}', this);">删除</a>
							</c:if>
						</td>
				    </c:if>
				</tr>
		   </c:forEach>
		</tbody>
	</table>
	<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
	<div class=pagerBar>
		<jsp:include page="../../page/common/pager.jsp"></jsp:include>
	</div>
</form>
</body>
</html>