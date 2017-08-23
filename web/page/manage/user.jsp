<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        <title>用户列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
    </head>
    <body class="list">
    <div class="main-top-hd clearfix">
        <h3 class="cur"><a href="javascript:void(0);">用户列表</a></h3>
    </div>
    <form id="userForm" name="userForm" method="post" action="${path}/admin/user!list.action">
    <input type="hidden" name="thisSiteUser" value="${thisSiteUser}"/>
    <div class="main-action-bar">
        <c:if test="${add}">
        <a class="ui-btn-wrap" href="${path}/admin/user!edit.action">
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
                    <th class="check">
                    <input class="allCheck" type="checkbox"  id="selectAll" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')"/> 
                    </th>
                    <th class="w70">序号</th>
                    <th>用户名</th>
                    <th class="Ttitle">用户类型</th>
                    <th>最后登录</th>
                    <th class="w70">登录次数</th>
                    <th class="w90">状态</th>
                    <c:if test="${update or delete or initialization}">
                    <th class="w150"><span>操作</span></th>
                    </c:if>
                </tr>
                <c:forEach var="user" items="${pagination.list}" varStatus="e">
                    <tr>
                        <td>
                            <input id="select${e.index}" type="checkbox" value="${user.id}" name="ids" onclick="javascript:selected(this, '${pagination.rowSize}');"/>
                        </td>
                        <td>
                            <span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span>
                        </td>
                        <td>
                            <span style="float:left;">
                            ${user.account}&nbsp;
                            <c:if test="${!empty user.realName}">
                             [<span style="color:green;">${user.realName}</span>]
                            </c:if>
                            </span>
                        </td>
                        <td>${user.userType.typeName}</td>
                        <td>
                            <c:if test="${!empty user.lastLoginTime or !empty user.lastLoginIp}">
                            <span style="color: green;float: left;">时间[<fmt:formatDate value="${user.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]</span>
                            <span style="color:red;float:left;padding-left:5px;"> IP[<c:if test="${user.lastLoginIp=='0:0:0:0:0:0:0:1' or user.lastLoginIp=='127.0.0.1'}">服务端</c:if>
                            <c:if test="${!(user.lastLoginIp=='0:0:0:0:0:0:0:1' or user.lastLoginIp=='127.0.0.1')}">${user.lastLoginIp}</c:if>
                            ]
                            </span>
                            </c:if>
                        </td>
                        <td>${user.loginCount} 次</td>
                        <td>
                             <div class="statusDiv-wrap">
                                <a href="#" onclick="showStatusDiv('statusDiv', ${e.index}, this);">
                                   <c:forEach var="item" items="${userStatusList}">
                                       <c:if test="${user.status == item.key}"><span id="status${e.index}" style="color: ${item.description};">${item.value}</span></c:if>
                                   </c:forEach>
                                </a>
                                <c:if test="${update}">
                                    <c:if test="${user.status < 4}">
		                                <div id="statusDiv${e.index}" class="statusDiv">
		                                   <c:forEach var="item" items="${mainUserStatusList}">
		                                       <a onclick="statusRow('${path}/admin/user!status.action?id=${user.id}', 'status', 'statusDiv', ${e.index}, '${item.value}', '${item.key}', '${item.description}');">${item.value}</a><br/>
		                                   </c:forEach>
		                                </div>
	                                </c:if>
                                </c:if>
                            </div>
                        </td>
                        <c:if test="${delete or update or initialization}">
                            <td>
                                <c:if test="${initialization}">   
                                    <a href="#" class="show"
                                     onclick="deleteRow('userForm','${path}/admin/user!initialPwd.action', '${pagination.rowSize}',this,'确认将用户\n&nbsp;&nbsp;${user.account}[${user.realName}]\n的密码初始化？');">密码初始化</a>
                                </c:if>
                               <c:if test="${update}">
                                <a href="${path}/admin/user!edit.action?id=${user.id}&edit=true" class="edit">修改</a>&nbsp;|&nbsp;
                               </c:if><c:if test="${delete}">   
                                <a href="#" class="delete" onclick="deleteRow('userForm', '${path}/admin/user!delete.action', '${pagination.rowSize}', this);">删除</a>
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
               <input  id="deleteButton" disabled="disabled" class=formButton value="删 除" type="button" onclick="deleteRow('userForm','${path}/admin/user!delete.action', '${pagination.rowSize}');">
            </div> 
        </c:if><c:if test="${update}">
        <input  id="checkButton" disabled="disabled" class=formButton value="审核" type="button" onclick="checkRow('userForm','${path}/admin/user!check.action', '${pagination.rowSize}','确认审核？');">
        </c:if>
        </div>
    <jsp:include page="../../page/common/pager.jsp"></jsp:include>
    </div>
    </form>
    </body>
</html>