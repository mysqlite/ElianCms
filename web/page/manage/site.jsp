<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		<title>站点列表</title>
		<jsp:include page="../../page/include/headList.jsp"></jsp:include>
	</head>
<body class="list">
<div class="main-top-hd clearfix">
	<h3 class="cur"><a href="javascript:void(0);">站点列表</a></h3>
</div>
<form id="siteForm" method="post" action="${path}/admin/site!list.action">
<div class="main-action-bar">
<c:if test="${add}">
    <a class="ui-btn-wrap" href="${path}/admin/site!edit.action">
    	<span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">添加</span>
    </a>
    </c:if>
    <!-- 查询、分页 -->
	<jsp:include page="../../page/include/search.jsp"></jsp:include>
</div>
<div class="main-action-bar" style="font-size:14px;height:30px;">
   <c:forEach var="item" items="${comList}">
       <input type="radio" name="compType" value="${item.key}"  onclick="searchSubmit()" <c:if test="${compType == item.key}"> checked="checked" </c:if>/>${item.value}
   </c:forEach>
</div>
<div class="body">
	<table id="listtable" class="listtable">
		<tbody>
			<tr>
			    <th class="check"><input class="allCheck" type="checkbox"  id="selectAll" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')"> </th>
			    <th class="w70">序号</th>
			    <th>站点名称</th>
			    <th>站点域名</th>
			    <th>站点类型</th>
			    <th class="w90">状态</th>
			    <th class="w70">排序</th>
			    <th class="w150">
			    <c:if test="${update || delete}">
			    <span>操作</span>
			    </c:if>
			    </th>
			</tr>
		    <c:forEach var="site" items="${pagination.list}" varStatus="e">
			    <tr>
				    <td>
				    	<input id="select${e.index}" type="checkbox" value="${site.id}" name=ids onclick="javascript:selected(this, '${pagination.rowSize}');">
				    </td>
				    <td>
						<span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span>
					</td>
				    <td>
				    	<c:choose>
							<c:when test="${fn:length(site.siteName) > 18}">
								<c:out value="${fn:substring(site.siteName, 0, 18)}..." />
							</c:when>
							<c:otherwise>
								<c:out value="${site.siteName}" />
							</c:otherwise>
						</c:choose>
				    </td>
				    <td title="${site.domain}">
				    	<div class="Ttitle">
				    	${site.domain}
				    	</div>
				    </td>
				    <td>
					    <c:forEach var="item" items="${pageCompTypeList}">
								<c:if test="${site.comType == item.key}">${item.value}</c:if>
					    </c:forEach>
				    </td>
				    <td>
					    <div class="statusDiv-wrap">
                          <a href="#" onclick="showStatusDiv('statusDiv', ${e.index}, this);">
							<c:forEach var="item" items="${siteStatusList}">
								<c:if test="${site.status == item.key}"><span id="status${e.index}" style="color: ${item.description};">${item.value}</span></c:if>
							</c:forEach>
						 </a>
						 <c:if test="${site.status< 4}">
						 <div id="statusDiv${e.index}" class="statusDiv">
							<c:forEach var="item" items="${mainSiteStatusList}">
								<a onclick="statusRow('${path}/admin/site!status.action?id=${site.id}', 'status', 'statusDiv', ${e.index}, '${item.value}', '${item.key}', '${item.description}');">${item.value}</a><br/>
							</c:forEach>
						 </div>  
						 </c:if>
					   </div>
				    </td>
				    <td onclick="sortRow('siteForm', '${path}/admin/site!sort.action?id=${site.id}', ${update}, this);">${site.siteSort}</td>
				    <td>
				    <c:if test="${update}">
				    <a href="${path}/admin/site!edit.action?id=${site.id}&edit=true" class="edit">修改</a>&nbsp;|&nbsp;
						<a href="#" class="delete" onclick="deleteRow('siteForm', '${path}/admin/site!delete.action', '${pagination.rowSize}', this);">删除</a>
						</c:if>
						</td>
			    </tr>
		    </c:forEach>
		</tbody>
	</table>
	<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
<div class=pagerBar>
<c:if test="${delete}">
<div class=delete>
<input  id="deleteButton" disabled="disabled" class=formButton value="删 除" type=button onclick="deleteRow('siteForm','${path}/admin/site!delete.action', '${pagination.rowSize}');">
</div>
</c:if>
<c:if test="${check}">
<input  id="checkButton" disabled="disabled" class=formButton value="审核" type=button onclick="checkRow('siteForm','${path}/admin/site!check.action', '${pagination.rowSize}');">
</c:if>
</div>
<jsp:include page="../../page/common/pager.jsp"></jsp:include>
</div>
</form>
</body>
</html>