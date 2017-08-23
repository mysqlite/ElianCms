<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML >
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=9" />
		<title>模板配置</title>
		<link rel="icon" type="image/x-icon" href="favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/page.css"/>
        <script language="javascript" type="text/javascript" src="${path}/js/jquery.js"></script>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/page.js"></script>
    </head>
	<body class="list">
		<div class="main-top-hd clearfix">
	        <h3 class="cur"><a href="javascript:void(0);">栏目—模板配置列表</a></h3>
	    </div>
		<form id="templateSetForm" method="post" action="${path}/admin/tempConfig!list.action?
			channelId=${channelId }&channelType=${channelType }&contentType=${contentType }&channelTempId=${channelTempId }&contentTempId=${contentTempId}">
			<div class="main-action-bar">
				<c:if test="${add}">
					<a class="ui-btn-wrap" href="${path}/admin/tempConfig!edit.action?
						channelId=${channelId }&channelType=${channelType }&contentType=${contentType }
							&channelTempId=${channelTempId }&contentTempId=${contentTempId}&edit=false">
			            <span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">添加</span>
			        </a>	
			    </c:if>			
		        <!-- 查询、分页 -->
		        <jsp:include page="../../page/include/search.jsp"></jsp:include>
		    </div>		   
		    <div class="body">
		    	<table id="table" class="listtable">
					<tr>
						<th class="check" width="10px">
							<input id="selectAll" type="checkbox" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')"/>
						</th>
						<th width="30px">
							<a class="sort">序号</a>
						</th>						
						<th width="60px">
							<a class="sort">模板文件</a>
						</th>						
						<th width="30px">
							<a class="sort">区域编号</a>
						</th>	
						<th width="60px">
							<a class="sort">栏目</a>
						</th>							
						<th width="60px">
							<a class="sort">创建时间</a>
						</th>
						<th width="30px">
							<a class="sort">创建人</a>
						</th>		
						<c:if test="${show || update || delete}">
							<th width="80px">
								<span>操作</span>
							</th>						
						</c:if>								
					</tr>
					<c:forEach var="item" items="${pagination.list}" varStatus="e">
						 <tr style="text-align: center;">
							<td>
                                <input id="select${e.index}" type="checkbox" value="${item.id}" name="ids" onclick="javascript:selected(this, '${pagination.rowSize}');"/>
                            </td>	
                            <td>
                            	<span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span>
                            </td>
                            <td>
                            	<span>${item.template.tempName}</span>
                            </td>
                            <td>
                            	<span>${item.areaId}</span>
                            </td>
                            <td>
                            	<span>${item.channelSet.channelName}</span>
                            </td>
                            <td>                            	
                            	<span><f:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                            </td>
                            <td>
                            	<span>${item.creater}</span>
                            </td>
                            <c:if test="${show || update || delete}">		
                            <td>
	                           	<c:if test="${show}">
		                              <a href="${path}/admin/tempConfig!show.action?templateConfig.id=${item.id}
		                              	&channelId=${channelId}&channelType=${channelType}&contentType=${contentType}
		                              	&channelTempId=${channelTempId}&contentTempId=${contentTempId}" class="show">查看</a>&nbsp;|
			                                &nbsp;
			                     </c:if>
			                     <c:if test="${update}">			                     
	                           	  <a href="${path}/admin/tempConfig!edit.action?templateConfig.id=${item.id}&edit=true&channelId=${channelId}&channelType=${channelType}&contentType=${contentType}
	                              	&channelTempId=${channelTempId}&contentTempId=${contentTempId}" class="edit"">修改</a>&nbsp;|&nbsp;
	                             </c:if>
	                             <c:if test="${delete}">
	                              <a href="" class="delete"
		                                onclick="deleteRow('templateSetForm', '${path}/admin/tempConfig!delete.action', '${pagination.rowSize}', this);">删除</a>
                           		 </c:if>
                            </td>
                            </c:if>
						</tr>
					</c:forEach>				
				</table>
				<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
				<div class="pagerBar">	
					<c:if test="${delete}">	
			            <div class=delete>
			               <input  id="deleteButton" disabled="disabled" class="formButton" value="删 除"
			               	 type="button" onclick="deleteRow('templateSetForm', '${path}/admin/tempConfig!delete.action', '${pagination.rowSize}')">
			            </div> 
			        </c:if>
			        <c:if test="${add and !empty pagination.list}">
			        &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:void(0);" onclick="ajaxSubmit('templateSetForm', '','${path}/admin/tempConfig!saveMoreConfig.action?channelId=${channelId}')">
                    <span class="txt" style="color:green;">为相同栏目模板的同级栏目添加本栏目相同配置</span>
                    </a>
                     &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:void(0);" onclick="ajaxSubmit('templateSetForm', '','${path}/admin/tempConfig!saveSameTempConfig.action?channelId=${channelId}')">
                    <span class="txt" style="color:green;">为本站相同栏目模板的栏目添加本栏目相同模板数据配置</span>
                    </a>
                    </c:if>
		        </div>						
				</div>
				<jsp:include page="../../page/common/pager.jsp"></jsp:include>
		    </div>
		</form>
	</body>
</html>