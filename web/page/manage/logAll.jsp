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
        <title>日志列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
    </head>
    <script type="text/javascript">
        function sim(form){
            form.action='${path}'+"/admin/logAll!batchDelete.action";
            form.submit();
        }
    </script>
    <body class="list">
    <form id="logForm" name="logForm" method="post" action="${path}/admin/logAll!list.action">
     <div class="main-top-hd clearfix">
        <h3 class="cur"><a href="javascript:void(0);">所有日志</a></h3>
        <c:if test="${delete}">
        <div style="float:right;padding-right:10px;padding-top:10px;">
                   &nbsp;&nbsp;&nbsp;&nbsp;批量删除
                   <select name="days" id="days">
                      <option value="365">一年前日志</option>
                      <option value="90">一季前日志</option>
                      <option value="30">一月前日志</option>
                      <option value="7" selected>一周前日志</option>
                      <option value="3">3天前日志</option>
                      <option value="0">所有日志</option>
                   </select>
                   <span class="body"><input class="formButton" type="button" onclick="sim(this.form);" value="提交"/></span>
        </div>
        </c:if>
    </div>
    <div class="main-action-bar">
        <jsp:include page="../../page/include/search.jsp"></jsp:include>
    </div>
    <div class="body">
        <table id="listtable" class="listtable">
            <tbody>
                <tr>
                    <th class="check"><input class="allCheck" type="checkbox"  id="selectAll" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')"> </th>
                    <th class="w70">用户</th>
                    <th class="w70">标题</th>
                    <th width="125px;">操作时间</th>
                    <th width="120px;">操作IP</th>
                    <th class="w90">请求路径</th>
                    <th class="w70">描述</th>
                    <c:if test="${delete}">
                    <th class="w70"><span>操作</span></th>
                    </c:if>
                </tr>
                <c:forEach var="log" items="${pagination.list}" varStatus="e">
                    <tr>
                        <td>
                            <input id="select${e.index}" type="checkbox" value="${log.id}" name=ids onclick="javascript:selected(this, '${pagination.rowSize}');">
                        </td>
                        <td>${log.user.account}</td>
                        <td>${log.title}</td>
                        <td><fmt:formatDate value="${log.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                       <td>
                           <c:if test="${log.ip=='0:0:0:0:0:0:0:1' or log.ip=='127.0.0.1'}">服务端</c:if>
                           <c:if test="${!(log.ip=='0:0:0:0:0:0:0:1' or log.ip=='127.0.0.1')}">${log.ip}</c:if>
                       </td>
                       <td>${log.url}</td>
                       <td>${log.content}</td>
                       <c:if test="${delete}">
	                      <td>
	                          <a href="#" class="delete" onclick="deleteRow('loglist','${path}/admin/log!delete.action', '${pagination.rowSize}', this);">删除</a>
	                      </td>
                       </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
        <div class=pagerBar>
         <c:if test="${delete}">
            <a class="formButton" onclick="deleteRow('loglist','${path}/admin/log!delete.action', '${pagination.rowSize}');">删&nbsp;&nbsp;除</a>
         </c:if>
        </div>
    <jsp:include page="../../page/common/pager.jsp"></jsp:include>
    </div>
    </form>
    </body>
</html>
