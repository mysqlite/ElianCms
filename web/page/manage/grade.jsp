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
        <title>等级列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
    </head>
    <body class="list">
    <div class="main-top-hd clearfix">
        <h3 class="cur"><a href="javascript:void(0);">等级列表</a></h3>
    </div>
    <form id="gradeForm" method="post" action="${path}/admin/grade!list.action">
    <div class="main-action-bar">
        <c:if test="${add}">
        <a class="ui-btn-wrap" href="${path}/admin/grade!edit.action">
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
                    <th>等级类型</th>
                      <c:if test="${mainStation}">
                    <th>是否默认</th>
                    </c:if>
                    <th class="w90">状态</th>
                    <th class="w70">排序</th>
                    <c:if test="${update or delete}">
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
                        <td>
                            <c:forEach var="comp" items="${comTypeList}"  varStatus="e">
                                <c:if test="${grade.comType eq comp.key}">${comp.value}</c:if>
                            </c:forEach>
                        </td>
                         <c:if test="${mainStation}">
                                <td>
                                    <a href="#" style="color:${grade.default?defaultItem.trueDescription:defaultItem.falseDescription}"
                                        onclick="disableRowSub('gradeForm','${path}/admin/grade!defaultData.action?id=${grade.id}', ${update}, this, '${defaultItem.trueStr}', 
                                            '${defaultItem.trueDescription}','${defaultItem.falseStr}', '${defaultItem.falseDescription}');">
                                        ${grade.default?defaultItem.trueStr:defaultItem.falseStr}
                                    </a>
                                </td>
                            </c:if>
                        <td>
                        	<a href="#" style="color:${grade.disable ? disableItem.trueDescription : disableItem.falseDescription}"
								onclick="disableRow('${path}/admin/grade!disable.action?id=${grade.id}', ${update}, this, '${disableItem.trueStr}', 
									'${disableItem.trueDescription}','${disableItem.falseStr}', '${disableItem.falseDescription}');">
								${grade.disable ? disableItem.trueStr : disableItem.falseStr}
							</a>
                        </td>
                        <td onclick="sortRow('gradeForm', '${path}/admin/grade!sort.action?id=${grade.id}',${update}, this);">${grade.gradeSort}</td>
                        <c:if test="${delete or update}">
                            <td>
                                 <c:if test="${update}">
                                <a href="${path}/admin/grade!edit.action?id=${grade.id}&edit=true" class="edit">修改</a>&nbsp;|&nbsp;
                                </c:if>
                                <c:if test="${delete}">  
                                <a href="#" class="delete"
                                    onclick="deleteRow('gradeForm', '${path}/admin/grade!delete.action', '${pagination.rowSize}', this);">删除</a>
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
	               <input  id="deleteButton" disabled="disabled" class=formButton value="删 除" type=button onclick="deleteRow('gradeForm','${path}/admin/grade!delete.action', '${pagination.rowSize}');">
	            </div> 
	        </c:if>
        </div>
    <jsp:include page="../../page/common/pager.jsp"></jsp:include>
    </div>
    </form>
    </body>
</html>
