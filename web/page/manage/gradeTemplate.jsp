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
        <title>等级模板列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
    </head>
    <body class="list">
    <div class="main-top-hd clearfix">
        <h3 class="cur"><a href="javascript:void(0);">等级模板列表</a></h3>
    </div>
    <form id="gradeTemplateForm" name="gradeTemplateForm" method="post" action="${path}/admin/gradeTemplate!list.action">
    <div class="main-action-bar">
        <c:if test="${add}">
        <a class="ui-btn-wrap" href="${path}/admin/gradeTemplate!edit.action">
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
                    <th>等级名称</th>
                    <th class="Ttitle">等级描述</th>
                    <c:if test="${update or delete or show}">
                        <th class="w150"><span>操作</span></th>
                    </c:if>
                </tr>
                <c:forEach var="grade" items="${pagination.list}" varStatus="e">
                    <tr>
                        <td>
                            <input id="select${e.index}" type="checkbox" value="${grade.id}" name=ids onclick="javascript:selected(this, '${pagination.rowSize}');">
                        </td>
                        <td>
                            <span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span>
                        </td>
                        <td>${grade.gradeName}</td>
                        <td>
                            <div class="Ttitle"> ${grade.gradeDesc}</div>
                        </td>
                        <c:if test="${delete or update or show}">
                            <td>
                                <c:if test="${update}">
                                    <a href="${path}/admin/gradeTemplate!edit.action?id=${grade.id}&edit=true" class="edit">修改</a>&nbsp;|&nbsp;
                                </c:if>
                                <c:if test="${delete}">
                                    <a href="#" class="delete" onclick="deleteRow('gradeTemplateForm', '${path}/admin/gradeTemplate!delete.action', '${pagination.rowSize}', this);">删除</a>
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
                   <input  id="deleteButton" disabled="disabled" class=formButton value="删 除" type=button onclick="deleteRow('gradeTemplateForm','${path}/admin/gradeTemplate!delete.action', '${pagination.rowSize}');">
                </div> 
            </c:if>
        </div>
    <jsp:include page="../../page/common/pager.jsp"></jsp:include>
    </div>
    </form>
    </body>
</html>