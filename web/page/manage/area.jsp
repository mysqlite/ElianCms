<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!doctype html>
<html lang="zh-CN">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=9"/>
        <title>区域列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
    </head>
<body class="list">
<div class="main-top-hd clearfix">
    <h3 class="cur"><a href="javascript:void(0);">区域列表</a></h3>
</div>
<form id="areaForm" name="areaForm" method="post" action="${path}/admin/area!list.action">
<div class="main-action-bar">
    <c:if test="${add}">
    <a class="ui-btn-wrap" href="${path}/admin/area!edit.action">
        <span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">添加</span>
    </a>
    </c:if>
    <!-- 查询、分页 -->
    <jsp:include page="../../page/include/search.jsp"></jsp:include>
</div>
<div class="body">
    <table id="listtable" class="listtable">
        <tbody>
            <tr>
                <th class="check"><input class="allCheck" type="checkbox"  id="selectAll" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')"> </th>
                <th class="w70">序号</th>
                <th>区域编码</th>
                <th>区域名称</th>
                <th>上级编码</th>
                <th class="w90">状态</th>
                <th class="w70">排序</th>
                <c:if test="${update or delete}">
                <th class="w150"><span>操作</span></th>
                </c:if>
            </tr>
            <c:forEach var="area" items="${pagination.list}" varStatus="e">
                <tr>
                    <td>
                        <input id="select${e.index}" type="checkbox" value="${area.areaCode}" name=ids onclick="javascript:selected(this, '${pagination.rowSize}');">
                    </td>
                    <td>
                        <span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span>
                    </td>
                    <td>${area.areaCode}</td>
                    <td>${area.areaName}</td>
                    <td>${area.areaCode}</td>
                    <td>
                        <a href="#" style="color:${area.disable ? 'green' : 'red'}" onclick="disableRow('${path}/admin/area!disable.action?id=${area.areaCode}',${update},this);">${area.disable ? '已启用' : '未启用'}</a>
                    </td>
                    <td onclick="sortRow('areaForm','${path}/admin/area!sort.action?id=${area.areaCode}',${update},this);">${area.areaSort}</td>
                    <c:if test="${delete or update}">
                        <td>
                           <c:if test="${update}">
                            <a href="${path}/admin/area!edit.action?id=${area.areaCode}&edit=true" class="edit">修改</a>&nbsp;|&nbsp;
                           </c:if><c:if test="${delete}">   
                            <a href="#" class="delete" onclick="deleteRow('areaForm', '${path}/admin/area!delete.action', '${pagination.rowSize}', this);">删除</a>
                            </c:if> 
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
	<div class=pagerBar>
	<c:if test="${delete}">
    <div class=delete>
       <input  id="deleteButton" disabled="disabled" class=formButton value="删 除" type=button onclick="deleteRow('areaForm','${path}/admin/area!delete.action', '${pagination.rowSize}');">
    </div> 
	</c:if><c:if test="${update}">
	<input  id="checkButton" disabled="disabled" class=formButton value="状 态" type=button onclick="checkRow('areaForm','${path}/admin/area!check.action', '${pagination.rowSize}');">
	</c:if>
	<jsp:include page="../../page/common/pager.jsp" flush="false"></jsp:include>
	</div>
</form>
</body>
</html>
