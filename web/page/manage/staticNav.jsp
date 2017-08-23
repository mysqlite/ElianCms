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
		<title>导航发布</title>
		<link rel="icon" type="image/x-icon" href="favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/page.css"/>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/jquery.js"></script>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/page.js"></script>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/ajaxMessage.js"></script>
    </head>
	<body class="list">
		<div class="main-top-hd clearfix">
	        <h3 class="cur"><a href="javascript:void(0);">
		  		     导航发布  
	        </a></h3>
	    </div>
			<form id="channelForm" method="post" action="${path}/admin/channel!frontList.action">			
			<div class="main-action-bar">
				<a class="ui-btn-wrap" href="javascript:void(0)" onclick="ajax('POST', '${path}/admin/static!staticNav.action?cache=true', false, 'message');">
		            <span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">发布</span>
		        </a>
				<a class="ui-btn-wrap" href="javascript:void(0)" onclick="ajax('POST', '${path}/admin/static!staticNav.action?cache=false', false, 'message');">
		            <span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">重发</span>
		        </a>
		        <!-- 查询、分页 -->
		        <jsp:include page="../../page/include/search.jsp"></jsp:include>
		    </div>
		    <div class="body">
				<table id="table" class="listtable">
					<tr>
						<th class="th6">
							<a class="sort">序号</a>
						</th>
						<th width="30%">
							<a class="sort">栏目名称</a>
						</th>
						<th width="10%">
							<a class="sort">静态化</a>
						</th>
						<th class="th6">
							<a class="sort">导航</a>
						</th>
						<th class="th6">
							<a class="sort">状态</a>
						</th>
						<th class="th6">
							<a class="sort">前端排序</a>
						</th>
					</tr>
					<c:forEach var="channel" items="${pagination.list}" varStatus="e">
						<tr>
							<td>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</td>
							<td>${channel.channelName}</td>
							<td>
								${channel.static ? '是' : '否'}
							</td>
							<td>
								<a href="#" style="color:${channel.navigetor ? naviItem.trueDescription : naviItem.falseDescription}"
									onclick="disableRow('${path}/admin/channel!navigetor.action?id=${channel.id}', ${update}, this, '${naviItem.trueStr}', 
										'${naviItem.trueDescription}','${naviItem.falseStr}', '${naviItem.falseDescription}');">
									${channel.navigetor ? naviItem.trueStr : naviItem.falseStr}
								</a>
							</td>
							<td>
								${channel.disable ? disableItem.trueStr : disableItem.falseStr}
							</td>
							<td onclick="sortRow('channelForm', '${path}/admin/channel!frontSort.action?id=${channel.id}', ${update}, this);">${channel.frontSort}</td>
						</tr>
					</c:forEach>
				</table>
				<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
				<jsp:include page="../../page/common/pager.jsp"></jsp:include>
			</div>
		</form>
	</body>
</html>