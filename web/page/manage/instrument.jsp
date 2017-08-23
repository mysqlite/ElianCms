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
        <title>器械列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
    </head>
    <body class="list">
    <div class="main-top-hd clearfix">
        <h3 class="cur"><a href="javascript:void(0);">器械列表</a></h3>
    </div>
    <form id="instrumentForm" name="instrumentForm" method="post" action="${path}/admin/instrument!list.action">
      <input type="hidden" name="ztree" value="${ztree}"/>
    <div class="main-action-bar">
        <c:if test="${add}">
        <a class="ui-btn-wrap" href="${path}/admin/instrument!edit.action?ztree=${ztree}&compId=${id}">
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
                        <th style="width:100px;">中文名</th>
                        <th style="width:100px;">通用名</th>
                        <th style="width:120px;">器械类型</th>
                        <th style="width:100px;">单价</th>
                        <th style="width:100px;">折扣价</th>
                        <th style="width:100px;">折扣价启用</th>
                        <th class="w90">状态</th>
                        <th class="w70">排序</th>
                        <c:if test="${update or delete or show}">
                            <th  style="width:210px;">操作</th>
                        </c:if>
                    </tr>
                    <c:forEach var="instrument" items="${pagination.list}" varStatus="e">
                        <tr style="text-align: center;">
                            <td>
                                <input id="select${e.index}" type="checkbox" value="${instrument.id}" name="ids" onclick="javascript:selected(this, '${pagination.rowSize}');"/>
                            </td>
                            <td>
                                <span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span>
                            </td>
                            <td>
                               ${instrument.cnName}
                            </td>
                            <td>
                               <div class="Ttitle">${instrument.alias}</div>
                            </td>
                            <td>${instrument.type.typeName}</td>
                            <td><fmt:formatNumber value='${instrument.pricePany}' pattern='########.##' type='number'/>元</td>
                            <td><fmt:formatNumber value='${instrument.discountedPricePany}' pattern='########.##' type='number'/>元</td>
                            <td>
                                ${instrument.discountedPrice?'是':'否'}
                            </td>
                            <td>
                                <a href="#" style="color:${instrument.disable ? 'green' : 'red'}"
                                    onclick="disableRow('${path}/admin/instrument!disable.action?id=${instrument.id}',${update},this);">${instrument.disable ? '已启用' : '未启用'}</a>
                            </td>
                            <td onclick="sortRow('instrumentForm', '${path}/admin/instrument!sort.action?id=${instrument.id}',${update},this);">${instrument.sort}</td>
                            <c:if test="${update or delete or show}">
	                            <td>
	                               <c:if test="${show}">
	                                <c:if test="${ztree}">
	                                  <a href="${path}/admin/instrument!show.action?ztree=${ztree}&compId=${compId}&id=${instrument.id}" class="show">查看</a>
	                                </c:if>
	                                <c:if test="${!ztree}">
	                                  <a href="${path}/admin/instrument!show.action?id=${instrument.id}" class="show">查看</a>
	                                </c:if>
	                                &nbsp;| &nbsp;
	                                </c:if>
	                                <c:if test="${update}">
	                                <a href="${path}/admin/instrument!edit.action?id=${instrument.id}&edit=true&ztree=${ztree}&compId=${compId}" class="edit"
	                                    >修改</a>&nbsp;|&nbsp;
	                                </c:if>
	                                <c:if test="${delete}">
	                                <a href="#" class="delete"
	                                    onclick="deleteRow('instrumentForm', '${path}/admin/instrument!deleteInstrument.action', '${pagination.rowSize}', this);">删除</a>
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
               <input  id="deleteButton" disabled="disabled" class="formButton" value="删 除" type="button" onclick="deleteRow('instrumentForm','${path}/admin/instrument!deleteInstrument.action', '${pagination.rowSize}');">
            </div> 
        </c:if><c:if test="${update}">
        <input  id="checkButton" disabled="disabled" class="formButton" value="审核" type="button" onclick="checkRow('instrumentForm','${path}/admin/instrument!check.action', '${pagination.rowSize}');">
        </c:if>
        </div>
    <jsp:include page="../../page/common/pager.jsp"></jsp:include>
    </div>
    </form>
    </body>
</html>