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
		<title>模板配置列表</title>
		<link rel="icon" type="image/x-icon" href="favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/page.css"/>
        <script language="javascript" type="text/javascript" src="${path}/js/jquery.js"></script>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/page.js"></script>
    </head>
	<body class="list">
		<div class="main-top-hd clearfix">
	        <h3 class="cur"><a href="javascript:void(0);">模板配置列表</a></h3>
	    </div>
		<form id="templateSetForm" method="post" action="${path}/admin/templateSet!list.action?tempId=${tempId}">
			<div class="main-action-bar">
				<c:if test="${add}">
					<a class="ui-btn-wrap" href="${path}/admin/templateSet!edit.action?tempId=${tempId}&edit=false">
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
						<th width="30px">
							<a class="sort">区域编号</a>
						</th>				
						<th width="30px">
							<a class="sort">是否父区域</a>
						</th>						
						<th width="30px">
							<a class="sort">栏目类型</a>
						</th>
						<th width="30px">
							<a class="sort">内容模型</a>
						</th>
						<th width="30px">
							<a class="sort">内容类型</a>
						</th>	
						<th width="30px">
							<a class="sort">图文类型</a>
						</th>	
						<c:if test="${show || update || delete}">					
							<th width="80px">
								<span>操作</span>
							</th>		
						</c:if>				
					</tr>
					<c:forEach var="tempSet" items="${pagination.list}" varStatus="e">
						 <tr style="text-align: center;">
                            <td>
                                <input id="select${e.index}" type="checkbox" value="${tempSet.id}" name="ids" onclick="javascript:selected(this, '${pagination.rowSize}');"/>
                            </td>
                            <td>
                                <span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span>
                            </td>
                            <td>
                            	<span>${tempSet.areaId}</span>
                            </td>
                            <td>
                            	<span>
                            		<c:if test="${tempSet.hasSubArea}">是</c:if>
                            		<c:if test="${!tempSet.hasSubArea}">否</c:if>
                            	</span>
                            </td>
                            <td>
                            	<span>
                            		<c:forEach var="item" items="${channelTypeList}">
			                     		<c:if test="${tempSet.channelType==item.key}">${item.value}</c:if>			                     		
			                     	</c:forEach>                            	
                            	</span>
                            </td>
                            <td>
                            	<span>${tempSet.model.modelName}</span>
                            </td>
                            <td>
                            	<span>
                            		<c:forEach var="item" items="${ContentTypeList}">
		                     			<c:if test="${item.key==tempSet.contentType}">${item.value}</c:if>			                     		
		                     		</c:forEach>
                            	</span>
                            </td>
                            <td>
                            	<span>
	                            	<c:forEach var="item" items="${SpecialContentTypeList}">	
										<c:if test="${item.key==tempSet.specialContentType}">${item.value}</c:if>
			                     	</c:forEach>	
			                    </span>
                            </td>                          
                            <c:if test="${show || update || delete}">		
	                            <td>
	                            <c:if test="${show}">
	                              <a href="${path}/admin/templateSet!show.action?templateSet.id=${tempSet.id}" class="show">查看</a>&nbsp;|
		                                &nbsp;
		                        </c:if>
		                         <c:if test="${update}">
	                           	  <a href="${path}/admin/templateSet!edit.action?tempId=${tempId}&templateSet.id=${tempSet.id}&edit=true" class="edit">修改</a>&nbsp;|&nbsp;
	                             </c:if>
	                              <c:if test="${delete}">
	                              <a href="#" class="delete"
		                                onclick="deleteRow('templateSetForm', '${path}/admin/templateSet!delete.action', '${pagination.rowSize}', this);">删除</a>
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
		               	 type="button" onclick="deleteRow('templateSetForm', '${path}/admin/templateSet!delete.action', '${pagination.rowSize}')">
		            </div> 
		            </c:if>
		        </div>						
				</div>
				<jsp:include page="../../page/common/pager.jsp"></jsp:include>
		    </div>
		</form>
	</body>
</html>