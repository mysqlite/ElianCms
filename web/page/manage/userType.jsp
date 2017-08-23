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
        <title>用户类型列表</title>
        <link rel="icon" type="image/x-icon" href="favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/page.css"/>
        <script language="javascript" type="text/javascript" src="${path}/js/jquery.js"></script>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/page.js"></script>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/base.js"></script>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/admin.js"></script>
    </head>
    <body class="list">
    <div class="main-top-hd clearfix">
        <h3 class="cur"><a href="javascript:void(0);">用户类型列表</a></h3>
    </div>
    <form id="userTypeFrom" name="userTypeFrom" method="post" action="${path}/admin/userType!list.action">
    <div class="main-action-bar">
        <a class="ui-btn-wrap" href="${path}/admin/userType!edit.action">
            <span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">添加</span>
        </a>
        <!-- 查询、分页 -->
        <jsp:include page="../../page/include/search.jsp"></jsp:include>
    </div>
    <div class="body">
        <table id="listtable" class="listtable">
            <tbody>
                <tr>
                    <th class="check"><input class="allCheck" type="checkbox"  id="selectAll" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')"> </th>
                    <th class="w70">序号</th>
                    <th>类型名称</th>
                    <th class="Ttitle">描述</th>
                    <th class="w90">状态</th>
                    <th class="w70">排序</th>
                    <c:if test="${update or delete}">
                    <th class="w150"><span>操作</span></th>
                    </c:if>
                </tr>
                <c:forEach var="type" items="${pagination.list}" varStatus="e">
                    <tr>
                        <td><input id="select${e.index}" type="checkbox" value="${type.id}" name=ids onclick="javascript:selected(this, '${pagination.rowSize}');"></td>
                        <td><span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span></td>
                        <td>${type.typeName}</td>
                        <td><div class="Ttitle">${type.typeDesc}</div></td>
                        <td><a href="#" style="color:${type.disable ? 'green' : 'red'}" onclick="disableRow('${path}/admin/userType!disable.action?id=${type.id}',${update},this);">${type.disable ? '已启用' : '未启用'}</a></td>
                        <td onclick="sortRow('userTypeFrom','${path}/admin/userType!sort.action?id=${type.id}',${update},this);">${type.typeSort}</td>
                        <c:if test="${delete or update}">
                          <td>
                          <c:if test="${update}"><a href="${path}/admin/userType!edit.action?id=${type.id}&edit=true" class="edit">修改</a>&nbsp;|&nbsp;</c:if>
                          <c:if test="${delete}"><a href="#" class="delete" onclick="deleteRow('userTypeFrom', '${path}/admin/userType!delete.action', '${pagination.rowSize}', this);">删除</a></c:if> 
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
               <input  id="deleteButton" disabled="disabled" class=formButton value="删 除" type=button onclick="deleteRow('userTypeFrom','${path}/admin/userType!delete.action', '${pagination.rowSize}');">
            </div> 
        </c:if><c:if test="${update}">
        <input  id="checkButton" disabled="disabled" class=formButton value="审核" type=button onclick="checkRow('userTypeFrom','${path}/admin/userType!check.action', '${pagination.rowSize}');">
        </c:if>
        </div>
    <jsp:include page="../../page/common/pager.jsp"></jsp:include>
    </div>
    </form>
    </body>
</html>
