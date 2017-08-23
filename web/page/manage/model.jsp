<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        <title>模型列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
    </head>
    <body class="list">
    <div class="main-top-hd clearfix">
        <h3 class="cur"><a href="javascript:void(0);">模型列表</a></h3>
    </div>
    <form id="modelForm" name="modelForm" method="post" action="${path}/admin/model!list.action">
    <div class="main-action-bar">
        <c:if test="${add}">
	        <a class="ui-btn-wrap" href="${path}/admin/model!edit.action">
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
                    <th>模型名称</th>
                    <th>组织类型</th>
                    <th>栏目模板前缀</th>
                    <th>内容模板前缀</th>
                    <th class="w90">状态</th>
                    <th class="w70">排序</th>
                    <c:if test="${update or delete or show}">
                        <th class="w150"><span>操作</span></th>
                    </c:if>
                </tr>
                <c:forEach var="model" items="${pagination.list}" varStatus="e">
                    <tr>
                        <td>
                            <input id="select${e.index}" type="checkbox" value="${model.id}" name=ids onclick="javascript:selected(this, '${pagination.rowSize}');">
                        </td>
                        <td><span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span></td>
                        <td>${model.modelName}</td>
                        <td>
                        	<c:forEach var="item" items="${compTypeList}">
								<c:if test="${model.compType == item.key}">${item.value}</c:if>
					    	</c:forEach>
                        </td>
                        <td>${model.channelTempPrefix}</td>
                        <td>${model.contentTempPrefix}</td>
                        <td>
                            <a href="#" style="color:${model.disable ? disableItem.trueDescription : disableItem.falseDescription}"
								onclick="disableRow('${path}/admin/model!disable.action?id=${model.id}', ${update}, this, '${disableItem.trueStr}', 
									'${disableItem.trueDescription}','${disableItem.falseStr}', '${disableItem.falseDescription}');">
								${model.disable ? disableItem.trueStr : disableItem.falseStr}
							</a>
                        </td>
                        <td onclick="sortRow('modelForm', '${path}/admin/model!sort.action?id=${model.id}',${update}, this);">${model.modelSort}</td>
                        <td>
                            <c:if test="${update}">
                            <a href="${path}/admin/model!edit.action?id=${model.id}&edit=true" class="edit">修改</a>&nbsp;|&nbsp;
                            </c:if>
                            <c:if test="${delete}">
                            <a href="#" class="delete" onclick="deleteRow('modelForm', '${path}/admin/model!delete.action', '${pagination.rowSize}', this);">删除</a>
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
                   <input  id="deleteButton" disabled="disabled" class=formButton value="删 除" type=button onclick="deleteRow('modelForm','${path}/admin/grade!delete.action', '${pagination.rowSize}');">
                </div> 
            </c:if>
        </div>
    <jsp:include page="../../page/common/pager.jsp"></jsp:include>
    </div>
    </form>
    </body>
</html>