<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
        <title>菜单列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
    </head>
    <body class="list">
    <div class="main-top-hd clearfix">
        <h3 class="cur"><a href="javascript:void(0);">FTP列表</a></h3>
    </div>
    <form id="ftpForm" name="ftpForm" method="post" action="${path}/admin/ftp!list.action">
    <div class="main-action-bar">
        <c:if test="${add}">
        <a class="ui-btn-wrap" href="${path}/admin/ftp!edit.action">
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
                        <th  class="w70">序号</th>
                        <th>FTP名称</th>
                        <th>FTP服务器</th>
                        <th>FTP类型</th>
                        <c:if test="${mainStation}">
                            <th width="10%">
                                <a class="sort">是否默认</a>
                            </th>
                        </c:if>
                        <th class="w90">状态</th>
                        <th class="w70">排序</th>
                        <c:if test="${update or delete or show}">
                            <th  class="w150">操作</th>
                        </c:if>
                    </tr>
                    <c:forEach var="ftp" items="${pagination.list}" varStatus="e">
                        <tr style="text-align: center;">
                            <td>
                                <input id="select${e.index}" type="checkbox" value="${ftp.id}" name="ids" onclick="javascript:selected(this, '${pagination.rowSize}');"/>
                            </td>
                            <td>
                                <span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span>
                            </td>
                            <td>
                               <div class="Ttitle">${ftp.ftpName}</div>
                            </td>
                            <td>
                                <div class="Ttitle">${ftp.serverIp}</div>
                            </td>
                            <td>${ftp.type.typeName}</td>
                            <c:if test="${mainStation}">
                                <td>
                                    <a href="#" style="color:${ftp.default?defaultItem.trueDescription:defaultItem.falseDescription}"
                                        onclick="disableRowSub('ftpForm','${path}/admin/ftp!defaultData.action?id=${ftp.id}', ${update}, this, '${defaultItem.trueStr}', 
                                            '${defaultItem.trueDescription}','${defaultItem.falseStr}', '${defaultItem.falseDescription}');">
                                        ${ftp.default?defaultItem.trueStr:defaultItem.falseStr}
                                    </a>
                                </td>
                            </c:if>
                            <td>
                                <a href="#" style="color:${ftp.disable ? 'green' : 'red'}"
                                    onclick="disableRow('${path}/admin/ftp!disable.action?id=${ftp.id}',${update},this);">${ftp.disable ? '已启用' : '未启用'}</a>
                            </td>
                            <td onclick="sortRow('ftpForm', '${path}/admin/ftp!sort.action?id=${ftp.id}',${update},this);">${ftp.ftpSort}</td>
                            <c:if test="${update or delete or show}">
	                            <td>
	                               <c:if test="${show}">
	                                <a href="${path}/admin/ftp!show.action?id=${ftp.id}" class="show">查看</a>&nbsp;|
	                                &nbsp;
	                                </c:if>
	                                <c:if test="${update}">
	                                <a href="${path}/admin/ftp!edit.action?id=${ftp.id}&edit=true" class="edit"
	                                    onclick="select('${e.index}', '${pagination.rowSize}');">修改</a>&nbsp;|&nbsp;
	                                </c:if>
	                                <c:if test="${delete}">
	                                <a href="#" class="delete"
	                                    onclick="deleteRow('ftpForm', '${path}/admin/ftp!delete.action', '${pagination.rowSize}', this);">删除</a>
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
               <input  id="deleteButton" disabled="disabled" class="formButton" value="删 除" type="button" onclick="deleteRow('ftpForm','${path}/admin/ftp!delete.action', '${pagination.rowSize}');">
            </div> 
        </c:if><c:if test="${update}">
        <input  id="checkButton" disabled="disabled" class="formButton" value="审核" type="button" onclick="checkRow('ftpForm','${path}/admin/ftp!check.action', '${pagination.rowSize}');">
        </c:if>
        </div>
    <jsp:include page="../../page/common/pager.jsp"></jsp:include>
    </div>
    </form>
    </body>
</html>