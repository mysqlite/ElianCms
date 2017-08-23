<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<title>角色列表</title>
		<link rel="icon" type="image/x-icon" href="favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" /> 
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/page.css"/>
        <script language="javascript" type="text/javascript" src="${path}/js/jquery.js"></script>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/page.js"></script>
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/jquery_message.css"/>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/jquery_message.js"></script>
        
        <script type="text/javascript">
			function cccc(){
			   $.message('操作成功!','success');
			}
		</script>

    </head>
	<body class="list">
		<div class="main-top-hd clearfix">
	        <h3 class="cur"><a href="javascript:void(0);">行业列表</a></h3>
	    </div>
		<form id="industryForm" method="post" action="${path}/admin/industry!list.action">
			<div class="main-action-bar">
				<c:if test="${add}">
			        <a class="ui-btn-wrap" href="${path}/admin/industry!edit.action">
			            <span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">添加</span>
			        </a>
			    </c:if>
		        <!-- 查询、分页 -->
		        <jsp:include page="../../page/include/search.jsp"></jsp:include>
		    </div>
		    <div class="body">
				<table id="table" class="listtable">
					<tr>
						<th class="check">
							<a class="sort">
								<input id="selectAll" type="checkbox" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')"/>
							</a>
						</th>
						<th class="th6">
							<a class="sort">序号</a>
						</th>
						<th width="20%">
							<a class="sort">行业名称</a>
						</th>
						<th width="30%">
							<a class="sort">行业描述</a>
						</th>
						<th class="th6">
							<a class="sort">是否可用</a>
						</th>
						
						<c:if test="${update || delete}">
							<th class="th14">
								<span>操作</span>
							</th>
						</c:if>
					</tr>
					<c:forEach var="industry" items="${pagination.list}" varStatus="e">
						<tr>
							<td>
								<input id="select${e.index}" type="checkbox" value="${industry.id}" onclick="selected(this, '${pagination.rowSize}');">
							</td>
							<td>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</td>
							<td>${industry.industryName}</td>
							<td>${industry.industryDesc}</td>
							<td>
									<a href="#" style="color:${industry.disable ? defaultItem.trueDescription : defaultItem.falseDescription}"
										onclick="disableRow('${path}/admin/industry!defaultData.action?id=${industry.id}', ${update}, this, '${defaultItem.trueStr}', 
											'${defaultItem.trueDescription}','${defaultItem.falseStr}', '${defaultItem.falseDescription}');">
										${industry.disable ? defaultItem.trueStr : defaultItem.falseStr}
									</a>
							</td>
							<c:if test="${update || delete}">
								<td>
									<c:if test="${update}">
										<a href="${path}/admin/industry!edit.action?id=${industry.id}&edit=true" class="edit">修改</a>
									</c:if>
									<c:if test="${delete}">
										&nbsp;|&nbsp;<a href="#" class="delete" onclick="deleteRow('industryForm', '${path}/admin/industry!delete.action', '${pagination.rowSize}', this);">删除</a>
									</c:if>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
				<div class="pagerBar">
					<c:if test="${delete}">
						<a id="deleteButton" class="formButton" disabled="disabled" onclick="deleteRow('industryForm','${path}/admin/industry!delete.action', '${pagination.rowSize}');">删&nbsp;&nbsp;除</a>
					</c:if>
				</div>
				<jsp:include page="../../page/common/pager.jsp"></jsp:include>
			</div>
		</form>
	</body>
</html>