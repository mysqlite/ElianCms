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
        <title>医生列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
    </head>
    <body class="list">
    <div class="main-top-hd clearfix">
        <h3 class="cur"><a href="javascript:void(0);">医生列表</a></h3>
    </div>
    <form id="doctorForm" name="doctorForm" method="post" action="${path}/admin/doctor!list.action">
      <input type="hidden" name="ztree" value="${ztree}"/>
      <input type="hidden" name="hospId" value="${hospId}"/>
      <input type="hidden" name="departId" value="${departId}"/>
    <div class="main-action-bar">
        <c:if test="${add}">
        <a class="ui-btn-wrap" href="${path}/admin/doctor!edit.action?ztree=${ztree}&hospId=${hospId}&departId=${departId}">
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
                        <th style="width:100px;">医生姓名</th>
                        <th style="width:100px;">所属科室</th>
                        <th style="width:120px;">职称</th>
                        <th style="width:100px;">毕业院校</th>
                        <%--
                        <th style="width:130px;">从业时间</th>
                        --%>
                        <th style="width:130px;">创建时间</th>
                        <th class="w90">状态</th>
                        <th class="w70">排序</th>
                        <c:if test="${update or delete or show}">
                            <th  style="width:210px;">操作</th>
                        </c:if>
                    </tr>
                    <c:forEach var="doctor" items="${pagination.list}" varStatus="e">
                        <tr style="text-align: center;">
                            <td>
                                <input id="select${e.index}" type="checkbox" value="${doctor.id}" name="ids" onclick="javascript:selected(this, '${pagination.rowSize}');"/>
                            </td>
                            <td>
                                <span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span>
                            </td>
                            <td>
                               ${doctor.doctName}
                            </td>
                            <td>
                               ${doctor.dept.deptName}
                            </td>
                            <td>${doctor.jobTitle}</td>
                            <td>${doctor.graduateSchool}</td>
                            <%--
                            <td><fmt:formatDate value="${doctor.jobBeginTime}" pattern="yyyy-MM-dd"/></td>
                            --%>
                            <td><fmt:formatDate value="${doctor.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <a href="#" style="color:${doctor.disable ? 'green' : 'red'}"
                                    onclick="disableRow('${path}/admin/doctor!disable.action?id=${doctor.id}',${update},this);">${doctor.disable ? '已启用' : '未启用'}</a>
                            </td>
                            <td onclick="sortRow('doctorForm', '${path}/admin/doctor!sort.action?id=${doctor.id}',${update},this);">${doctor.doctSort}</td>
                            <c:if test="${update or delete or show}">
	                            <td>
	                               <c:if test="${show}">
	                                <c:if test="${ztree}">
	                                  <a href="${path}/admin/doctor!show.action?ztree=${ztree}&departId=${departId}&hospId=${hospId}&id=${doctor.id}" class="show">查看</a>
	                                </c:if>
	                                <c:if test="${!ztree}">
	                                  <a href="${path}/admin/doctor!show.action?id=${doctor.id}" class="show">查看</a>
	                                </c:if>
	                                &nbsp;| &nbsp;
	                                </c:if>
	                                <c:if test="${update}">
	                                <a href="${path}/admin/doctor!edit.action?id=${doctor.id}&edit=true&ztree=${ztree}&hospId=${hospId}&departId=${departId}" class="edit"
	                                    onclick="select('${e.index}', '${pagination.rowSize}');">修改</a>&nbsp;|&nbsp;
	                                </c:if>
	                                <c:if test="${delete}">
	                                <a href="#" class="delete"
	                                    onclick="deleteRow('doctorForm', '${path}/admin/doctor!deleteDoctor.action', '${pagination.rowSize}', this);">删除</a>
	                                </c:if>
	                                <c:if test="${doctor.reg}">
		                                &nbsp;|&nbsp;<a href="${path}/admin/doctorRegisterSet!list.action?doctorId=${doctor.id}" class="show">排班设置</a>
		                                &nbsp;|&nbsp;<a href="${path}/admin/doctorWork!list.action?doctorId=${doctor.id}" class="show">排班</a>
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
               <input  id="deleteButton" disabled="disabled" class="formButton" value="删 除" type="button" onclick="deleteRow('doctorForm','${path}/admin/doctor!deleteDoctor.action', '${pagination.rowSize}');">
            </div> 
        </c:if><c:if test="${update}">
        <input  id="checkButton" disabled="disabled" class="formButton" value="审核" type="button" onclick="checkRow('doctorForm','${path}/admin/doctor!check.action', '${pagination.rowSize}');">
        </c:if>
        </div>
    <jsp:include page="../../page/common/pager.jsp"></jsp:include>
    </div>
    </form>
    </body>
</html>