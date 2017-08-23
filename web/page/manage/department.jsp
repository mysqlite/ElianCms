<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <title>科室列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
    </head>
    <body class="list">
    <div class="main-top-hd clearfix">
        <h3 class="cur"><a href="javascript:void(0);">科室列表</a></h3>
    </div>
    <form id="departForm" name="departForm" method="post" action="${path}/admin/department!list.action">
	    <input type="hidden" name="hospId" value="${hospId}"/>
	    <input type="hidden" name="ztree" value="${ztree}"/>
    <div class="main-action-bar">
        <c:if test="${add}">
        <a class="ui-btn-wrap" href="${path}/admin/department!edit.action?ztree=${ztree}&hospId=${hospId}">
            <span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">添加</span>
        </a>
        </c:if>
        <!-- 查询、分页 -->
        <jsp:include page="../../page/include/search.jsp"></jsp:include>
    </div>
    <div class="body">
        <table id="table" class="listtable">
                    <tr>
                        <th class="check"><input id="selectAll" type="checkbox" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')">
                        </th>
                        <th style="width:80px;">序号</th>
                        <th style="width:100px;">科室名称</th>
                        <th style="width:100px;">科室性质</th>
                        <th style="width:100px;">科室地址</th>
                        <th style="width:100px;">创建时间</th>
                        <th class="w90">状态</th>
                        <th class="w70">排序</th>
                        <c:if test="${update or delete or show}">
                            <th  class="w150">操作</th>
                        </c:if>
                    </tr>
                    <c:forEach var="depa" items="${pagination.list}" varStatus="e">
                        <tr style="text-align: center;">
                            <td>
                                <input id="select${e.index}" type="checkbox" value="${depa.id}" name="ids" onclick="javascript:selected(this, '${pagination.rowSize}');"/>
                            </td>
                            <td>
                                <span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span>
                            </td>
                            <td>
                               ${depa.deptName}
                            </td>
                            <td>
                                <c:forEach var="nav" items="${natureLists}">
                                    <c:if test="${nav.id eq depa.natureId}">${nav.typeName}</c:if>
                                </c:forEach>
                            </td>
                            <td>${depa.address}</td>
                            <td><fmt:formatDate value="${depa.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <a href="#" style="color:${depa.disable ? 'green' : 'red'}"
                                    onclick="disableRow('${path}/admin/department!disable.action?id=${depa.id}',${update},this);">${depa.disable ? '已启用' : '未启用'}</a>
                            </td>
                            <td onclick="sortRow('departForm', '${path}/admin/department!sort.action?id=${depa.id}',${update},this);">${depa.deptSort}</td>
                            <c:if test="${update or delete or show}">
	                            <td>
	                               <c:if test="${show}">
	                                <a href="${path}/admin/department!show.action?id=${depa.id}" class="show">查看</a> &nbsp;|&nbsp;
	                                </c:if>
	                                <c:if test="${update}">
	                                <a href="${path}/admin/department!edit.action?id=${depa.id}&edit=true&ztree=${ztree}&hospId=${hospId}" class="edit"
	                                    onclick="select('${e.index}', '${pagination.rowSize}');">修改</a>&nbsp;|&nbsp;
	                                </c:if>
	                                <c:if test="${delete}">
	                                <a href="#" class="delete"
	                                    onclick="deleteRow('departForm', '${path}/admin/department!deleteDepat.action', '${pagination.rowSize}', this);">删除</a>
	                                </c:if>
	                            </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
        <h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
        <div class=pagerBar>
        <c:if test="${delete}">
            <div class=delete>
               <input  id="deleteButton" disabled="disabled" class="formButton" value="删 除" type="button" onclick="deleteRow('departForm','${path}/admin/department!deleteDepat.action', '${pagination.rowSize}');">
            </div> 
        </c:if><c:if test="${update}">
        <input  id="checkButton" disabled="disabled" class="formButton" value="审核" type="button" onclick="checkRow('departForm','${path}/admin/department!check.action', '${pagination.rowSize}');">
        </c:if>
        </div>
    <jsp:include page="../../page/common/pager.jsp"></jsp:include>
    </div>
    </form>
    </body>
</html>