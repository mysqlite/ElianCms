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
			function createDirectory(formId, id){
				var dir = document.getElementById(id);
				var navigateUrl = document.getElementById('navigateUrl');
				if(navigateUrl==null || navigateUrl.value==null){
					alert("请选择目录！");
					return;
				}
				if(dir!=null && dir.value!=null && dir.value!=""){
					var form = document.getElementById(formId);
					form.action='${path}/admin/template!createDir.action'+"?dirName="+dir.value;
					form.submit();
				}
			}
			
			function submitUpload(formId, id){
				var file = document.getElementById(id);
				if(file.value.lastIndexOf(".html")>0 || file.value.lastIndexOf(".zip")>0 || file.value.lastIndexOf(".rar")>0)
					document.getElementById(formId).submit();
				else
					alert("只能上传html、zip文件");
			}
		//]]>
		</script>
	</head>
	<body class="list">
		<div class="bar">
			模板列表		
		</div>
		<div class="body">
			<form id="uploadForm" action="${path}/admin/upload!upload.action" method="post" enctype="multipart/form-data">
				<c:if test="${add}">
					<div class="listbar">
						<span>当前路径:${url}</span>
			            <input id="upload" type='file' name='upload'/>
			            <input type="hidden" name="uploadToUrl" value="${navigateUrl}">
			            <input type="hidden" name="url" value="${path}/admin/template!list.action"> 
			            <a class="formButton" onclick="submitUpload('uploadForm','upload');">上&nbsp;&nbsp;传</a>
			            <span>
							<input id="createDir" type="text" class="listbar-ipt" onkeypress="if(event.keyCode==13){$('#dirButton').click();return false;}"/>
							<a id="dirButton" class="formButton" onclick="createDirectory('templateForm', 'createDir')">新建目录</a>
						</span>
			        </div>
			    </c:if>
	        </form>
			<form id="templateForm" method="post" action="${path}/admin/template!list.action">
				<table id="table" class="listtable">
					<tr>
						<th class="check">
							<a class="sort">
								<input id="selectAll" type="checkbox" onclick="javascript:selectAllCheckBox(this, '${listSize}')">
							</a>
						</th>
						<th width="5%">
							<a class="sort"></a>
						</th>
						<th width="31%">
							<a class="sort">文件名</a>
						</th>
						<th width="12%">
							<a class="sort">大小</a>
						</th>
						<th class="30%">
							<a class="sort">最后修改时间</a>
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
								<input id="select${e.index}" type="checkbox" value="${template.url}">
							</td>
							<td>
								<img src="${path}/images/manage/${template.dir ? 'folder.gif' : 'html.gif'}">
							</td>
							<td>${template.name}</td>
							<td>${template.size}KB</td>
							<td>
								<f:formatDate value="${template.lastUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<c:if test="${update || delete}">
								<td>
									<c:if test="${update}">
										<a href="${path}/admin/template!edit.action?navigateUrl=${template.url}" class="edit">修改</a>
									</c:if>
									<c:if test="${delete}">
										&nbsp;|&nbsp;<a href="#" class="delete" onclick="deleteRow('templateForm', '${path}/admin/template!delete.action', '${listSize}', this);">删除</a>
									</c:if>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<input type="hidden" id="navigateUrl" name="navigateUrl" value="${navigateUrl}">
			</form>
			<h4 align="center" style="display:${empty templateList ? 'block' : 'none' }">没有符合条件的数据!</h4>
			<div class="listbar">
				<c:if test="${delete}">
					<a class="formButton" onclick="deleteRow('templateForm','${path}/admin/template!delete.action', '${listSize}');">删&nbsp;&nbsp;除</a>
				</c:if>
			</div>
		</div>
	</body>
</html>