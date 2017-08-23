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
        <title>应聘者列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
    </head>
<body class="list">
<div class="main-top-hd clearfix">
    <h3 class="cur"><a href="javascript:void(0);">应聘者列表</a></h3>
</div>
<form id="areaForm" name="areaForm" method="post" action="${path}/admin/area!list.action">
<div class="main-action-bar">
    <c:if test="${add}">
    <a class="ui-btn-wrap" href="${path}/admin/candidate!edit.action">
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
                <th>应聘职位</th>
                <th>姓名</th>
                <th>联系电话</th>
                <th>QQ号码</th>
                <th>地址</th>
                <th class="w90">状态</th>
                <c:if test="${update or delete}">
                <th class="w150"><span>操作</span></th>
                </c:if>
            </tr>
            <c:forEach var="contacter" items="${pagination.list}" varStatus="e">
                <tr>
                    <td>
                        <input id="select${e.index}" type="checkbox" value="${contacter.id}" name=ids onclick="javascript:selected(this, '${pagination.rowSize}');">
                    </td>
                    <td>
                        <span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span>
                    </td>
                    <td>${contacter.applyPost}</td>
                    <td>${contacter.contacter}</td>
                    <td>${contacter.phone}</td>
                    <td>${contacter.qq}</td>
                    <td>${contacter.address}</td>
                    <td>
                        <a href="#" style="color:${contacter.disable ? 'green' : 'red'}" onclick="disableRow('${path}/admin/candidate!disable.action?id=${contacter.id}',${update},this);">${contacter.disable ? '已启用' : '未启用'}</a>
                    </td>
                     <c:if test="${delete or update}">
                            <td>
                               <c:if test="${update}">
                                <a href="${path}/admin/candidate!edit.action?id=${contacter.id}&edit=true" class="edit">修改</a>&nbsp;|&nbsp;
                               </c:if>
                               <c:if test="${delete}">   
                                <a href="#" class="delete" onclick="deleteRow('contacterForm', '${path}/admin/candidate!delete.action', '${pagination.rowSize}', this);">删除</a>
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
       <input  id="deleteButton" disabled="disabled" class=formButton value="删 除" type=button onclick="deleteRow('contacterForm','${path}/admin/candidate!delete.action', '${pagination.rowSize}');">
    </div> 
	</c:if><c:if test="${update}">
	<input  id="checkButton" disabled="disabled" class=formButton value="状态" type=button onclick="checkRow('contacterForm','${path}/admin/candidate!check.action', '${pagination.rowSize}');">
	</c:if>
	<jsp:include page="../../page/common/pager.jsp" flush="false"></jsp:include>
	</div>
</form>
</body>
<script type="text/javascript">  
	window.parent.document.getElementById("contacterCOrU").style.display="none";
</script>
</html>
