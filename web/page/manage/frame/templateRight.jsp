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
		<title>模板列表</title>
		<link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" />
		<link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />
		<link rel="stylesheet" type="text/css" href="${path}/css/manage/page.css"/>
		<script language="javascript" type="text/javascript" src="${path}/js/jquery.js"></script>
		<script language="javascript" type="text/javascript" src="${path}/js/manage/page.js"></script>
		<script type="text/javascript">
		//<![CDATA[
			function submitUpload(formId, id){
				var file = document.getElementById(id);
				if(file.value.lastIndexOf(".html")>0 || file.value.lastIndexOf(".zip")>0)
					document.getElementById(formId).submit();
				else
					alert("只能上传html、zip文件");
			}
		//]]>
		</script>
	</head>
	<body class="list">
		<div class="main-top-hd clearfix">
	        <h3 class="cur"><a href="javascript:void(0);">模板列表</a></h3>
	    </div>
		<div class="body">
			<form id="uploadForm" action="${path}/admin/upload!upload.action" method="post" enctype="multipart/form-data">
				<c:if test="${add}">
					<div class="main-action-bar">
						<span>
							<a id="dirButton" class="formButton" href="${path}/admin/template!edit.action?tempId=${tempId}&url=${url}">添加</a>
						</span>
						<span>当前路径:${url}</span>
			            <input id="upload" type='file' name='upload'/>
			            <input type="hidden" name="uploadToUrl" value="${url}">
			            <input type="hidden" name="url" value="${path}/admin/template!list.action">
			            <input type="hidden" name="tempId" value="${tempId}"/>
			            <a class="formButton" onclick="submitUpload('uploadForm','upload');">上&nbsp;&nbsp;传</a>
			        </div>
			    </c:if>
	        </form>
			<form id="templateForm" method="post" action="${path}/admin/template!list.action">
				<input type="hidden" name="tempId" value="${tempId}"/>
				
				<table id="table" class="listtable">
					<tr>
						<th class="check">
							<a class="sort">
								<input id="selectAll" type="checkbox" onclick="javascript:selectAllCheckBox(this, '${listSize}')">
							</a>
						</th>
						<th width="5%">
							<a class="sort">类型</a>
						</th>
						<th width="10%">
							<a class="sort">模板名称</a>
						</th>
						<th width="10%">
							<a class="sort">文件名</a>
						</th>
						<th class="8%">
							<a class="sort">创建时间</a>
						</th>
						<th width="8%">
							<a class="sort">创建人</a>
						</th>
						<th class="4%">
							<a class="sort">状态</a>
						</th>
						<th width="7%">
							<a class="sort">排序</a>
						</th>
						<c:if test="${update || delete}">
							<th class="th14">
								<span>操作</span>
							</th>
						</c:if>
					</tr>
					<c:forEach var="template" items="${templateList}" varStatus="e">
						<tr>
							<td>
								<input id="select${e.index}" type="checkbox" value="${template.id}" onclick="selected(this, '${listSize}');">
							</td>
							<td>
								<img src="${path}/images/manage/${!template.file ? 'folder.gif' : 'html.gif'}">
							</td>
							<td>${template.tempName}</td>
							<td>${template.fileName}</td>
							<td>
								<f:formatDate value="${template.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>${template.creater}</td>
							<td>
								<a href="#" style="color:${template.disable ? disableItem.trueDescription : disableItem.falseDescription}"
									onclick="disableRow('${path}/admin/template!disable.action?id=${template.id}', ${update}, this, '${disableItem.trueStr}', 
										'${disableItem.trueDescription}','${disableItem.falseStr}', '${disableItem.falseDescription}');">
									${template.disable ? disableItem.trueStr : disableItem.falseStr}
								</a>
							</td>
							<td onclick="sortRow('templateForm', '${path}/admin/template!sort.action?id=${template.id}', ${update}, this);">${template.tempSort}</td>
							<c:if test="${update || delete}">
								<td>
									<c:if test="${update}">
										<a href="${path}/admin/template!edit.action?edit=true&id=${template.id}&url=${url}&tempId=${tempId}" class="edit">修改</a>
										&nbsp;|&nbsp;<a href="#" onclick="ajaxSubmit('templateForm', '', '${path}/admin/template!resolve.action?templateName=${url}${template.fileName}');" class="show">分解</a>
									</c:if>
									<c:if test="${delete}">
										&nbsp;|&nbsp;<a href="#" class="delete" onclick="deleteRow('templateForm', '${path}/admin/template!delete.action?url=${url}', '${listSize}', this);">删除</a>
									</c:if>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<input type="hidden" id="navigateUrl" name="navigateUrl" value="${navigateUrl}">
			</form>
			<h4 align="center" style="display:${empty templateList ? 'block' : 'none' }">暂无数据!</h4>
		</div>
	</body>
</html>