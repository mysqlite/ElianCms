<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>编辑内容模型</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
		<script language="javascript" type="text/javascript" src="${path}/js/jquery-1.4.4.min.js"></script>
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">添加行业</c:if>
				<c:if test="${edit}">编辑行业</c:if>
	        </h3>
	    </div>
		<div class="body">
			<form name="editIndustryForm" action="${path}/admin/contentModel!saveContent.action" method="post">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="contentModel.id" value="${contentModel.id}">
				<input type="hidden" name="contentModel.version" value="${contentModel.version}">
				<input type="hidden" name="edit" value="${edit}">
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">
		            		<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>对象名称：
		                     		</label>
			                        <input type="text" class="ipt" id="ipt" name="contentModel.objectName" value="${contentModel.objectName}"/>
				                    <span class="errortip">${errors['contentModel.objectName'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                    <div class="listItem txtarea">
			                    	<label class="lbl-ipt-tit">Action名称：</label>
			                            <input type="text" class="ipt" id="ipt" name="contentModel.actionName" value="${contentModel.actionName}"/>
				                    <span class="errortip">${errors['contentModel.actionName'][0]}</span>
			                    </div>
			                </li>
			            </ul>
			        </div>
			    </div>
				<div class="buttonArea">
            		<input class="formButton" value="确  定" type="button" onclick="document.editIndustryForm.submit();">&nbsp;&nbsp;
            		<input class="formButton" onclick="window.location='${path}/admin/contentModel!list.action'" value="返  回" type="button">
				</div>
			</form>
		</div>
		<script type="text/javascript">  
			displayDiv('typeBtn','typeDiv','${errors}');
		</script>
	</body>
</html>