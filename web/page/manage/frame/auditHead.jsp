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
        <jsp:include page="../../../page/include/headList.jsp"></jsp:include>
    </head>
    <body class="list">
    <div class="main-top-hd clearfix" id="menu">
        <h3 <c:if test="${status eq 'notAudit' or empty status}">class="cur"</c:if> id="notAuditList"><a href="javascript:void(0);">未审核列表</a></h3>
        <h3 <c:if test="${status eq 'audit'}"> class="cur" </c:if> id="auditList"><a href="javascript:void(0);">已审核列表</a></h3>
        <h3 <c:if test="${status eq 'exitAudit'}"> class="cur" </c:if> id="exitAuditList"><a href="javascript:void(0);">已退回列表</a></h3>
        <%--
        <h3 <c:if test="${status eq 'disable'}">class="cur"</c:if> id="disableList"><a href="javascript:void(0);">已禁用列表</a></h3>
        <h3 <c:if test="${status eq 'delete'}">class="cur"</c:if> id="deleteList"><a href="javascript:void(0);">已删除列表</a></h3>
         --%>
    </div>
    </body>
    <script type="text/javascript">
    G=function(id){return document.getElementById(id);};
    G("notAuditList").onclick=function(){parent.auditMainFrame.location='${path}/admin/audit!list.action?status=notAudit';};
    G("auditList").onclick=function(){parent.auditMainFrame.location='${path}/admin/audit!list.action?status=audit';};
    G("exitAuditList").onclick=function(){parent.auditMainFrame.location='${path}/admin/audit!list.action?status=exitAudit';};
    //G("disableList").onclick=function(){parent.auditMainFrame.location='${path}/audit!list.action?status=disable';};
    //G("deleteList").onclick=function(){parent.auditMainFrame.location='${path}/audit!list.action?status=delete';};
     $(document).ready(function(){
                var $menuItem = $("#menu h3");
                $menuItem.live("click",function(){
                    var $this=$(this);
                    $menuItem = $("#menu h3");
                    $menuItem.removeClass("cur")
                    $this.addClass("cur")
                })
            });
    </script>
</html>