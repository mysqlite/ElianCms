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
        <title>模板包含列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
        <script type="text/javascript">
        	function staticIncludeFile(includeFileId){
        		$.post('${path}/admin/static!staticIncludeFile.action',{siteIncludeId:includeFileId},function(data){
        			alert(data.MSG);
        		},"json")
        	}
        </script>
    </head>
    <body class="list">
    <div class="main-top-hd clearfix">
        <h3 class="cur"><a href="javascript:void(0);">模板包含列表</a></h3>
    </div>
    <form id="siteHeadAndFoot" name="siteHeadAndFoot" method="post" action="${path}/admin/siteHeadAndFoot!list.action">
    <div class="main-action-bar">
        <!-- 查询、分页 -->
        <jsp:include page="../../page/include/search.jsp"></jsp:include>
    </div>
    <div class="body">
        <table id="table" class="listtable">
                    <tr>
                        <th class="check"><input id="selectAll" type="checkbox" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')">
                        </th>
                        <th style="width:80px;">序号</th>
                        <th style="width:100px;">模板名称</th>
                        <th style="width:100px;">文件名称</th>
                            <th class="w150">操作</th>
                    </tr>
                    <c:forEach var="siteInclude" items="${pagination.list}" varStatus="e">
                        <tr style="text-align: center;">
                            <td>
                                <input id="select${e.index}" type="checkbox" value="${siteInclude.id}" name="ids" onclick="javascript:selected(this, '${pagination.rowSize}');"/>
                            </td>
                            <td>
                                <span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span>
                            </td>
                            <td>
                            	<c:forEach var="item" items="${templateName}">
                            		<c:if test="${item.key==siteInclude.fileName}">
                            			${item.value}
                            		</c:if>
                            	</c:forEach>
                            </td>                           
                            <td>${siteInclude.fileName}</td>
                            <td>
                                <a href="${path}/admin/siteHeadAndFoot!edit.action?siteInclude.id=${siteInclude.id}" class="show">编辑</a> &nbsp;|&nbsp;
                                <a href="#" onclick="staticIncludeFile(${siteInclude.id})">发布</a>
                                <!--  &nbsp;|&nbsp;<a href="${path}/admin/siteHeadAndFoot!tempFileInit.action?siteInclude.id=${siteInclude.id}" class="delete">初始化</a> -->
                            </td>
                        </tr>
                    </c:forEach>
                </table>
        <h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
       <jsp:include page="../../page/common/pager.jsp"></jsp:include>
    </div>
    </form>
    </body>
</html>