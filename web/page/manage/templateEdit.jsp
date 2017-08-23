<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>编辑模板</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
		<script type="text/javascript">
		//<![CDATA[
			function disableSubNode(parent, bool){
				parent.disabled = bool;
				var childs = parent.childNodes;
				for(var i = 0; i < childs.length; i++){
		    		disableSubNode(childs[i], bool);
		        }
			}
			
			function disabledTrue(){
				for(var i = 0;i<arguments.length; i++) {
					disableSubNode(arguments[i], true);
				}
			}
			
			function disabledFalse(){
				for(var i = 0;i<arguments.length; i++) {
					disableSubNode(arguments[i], false);
				}
			}
			
			function changeFileType(value){
				var fileContentDiv = document.getElementById("fileContentDiv");
				if(value=='true'){
					fileContentDiv.style.display='';
					disabledFalse(fileContentDiv);
				}
				else{
					fileContentDiv.style.display='none';
					disabledTrue(fileContentDiv);
				}
			}
		//]]>
		</script>
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
				模板修改
	        </h3>
	    </div>
		<div class="body">
			<form name="editTemplateForm" action="${path}/admin/template!save.action" method="post">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="edit" value="${edit}">
				<input type="hidden" name="template.id" value="${template.id}"/>
				<input type="hidden" name="template.version" value="${template.version}">
				<input type="hidden" name="template.parentId" value="${template.parentId}"/>
				<input type="hidden" name="tempId" value="${tempId}"/>
				<input type="hidden" name="url" value="${url}"/>
				<input type="hidden" name="template.fileName" value="${template.fileName}"/>
				<input type="hidden" name="template.creater" value="${template.creater}">
				<input type="hidden" name="template.createTime" value="${template.createTime}"/>
				<c:if test="${edit}">
					<input type="hidden" name="template.file" value="${template.file}"/>
				</c:if>
				
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">
		            		<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否启用：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="template.disable" value="true"
											<c:if test="${template.disable}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="template.disable" value="false"<c:if test="${!template.disable}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>模板名称：
		                     		</label>
			                        <input type="text" class="ipt" name="template.tempName" value="${template.tempName}" />
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>template.tempName</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>文件名称：
		                     		</label>
			                        <input type="text" class="ipt" name="template.newFileName" value="${template.newFileName}" />
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>template.fileName</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>文件类型：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="template.file" value="true" onchange="changeFileType(this.value);"
											<c:if test="${template.file}"> checked="checked" </c:if>
											<c:if test="${edit}"> disabled="disabled" </c:if> />
											文件
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="template.file" value="false" onchange="changeFileType(this.value);"
											<c:if test="${!template.file}"> checked="checked" </c:if>
											<c:if test="${edit}"> disabled="disabled" </c:if>/>
											文件夹
									</label>
			                   	</div>
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>排序：
		                     		</label>
									<input type="text" class="ipt" name="template.tempSort" value="${template.tempSort}" onkeyup="intFormat(this,1,99);"/>
									<span class="errortip">
										<s:fielderror >
										    <s:param>template.tempSort</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>
			                <div id="fileContentDiv" style="display: <c:if test="${!template.file}"> none </c:if>">
				                <li class="inputList-li">
					                <div class="listItem">
					                	<label class="lbl-ipt-tit">
			                     			<span class="star">*</span>文件内容：
			                     		</label>
					                    <div class="listItem txtarea">
					                        <textarea name="template.fileContent" class="formTextediter">${template.fileContent}</textarea>
					                    </div>
					                    <span class="errortip">
											<s:fielderror >
											    <s:param>template.fileContent</s:param>
										    </s:fielderror>
										</span>
					                </div>
				                </li>
				            </div>
			            </ul>
			        </div>
			    </div>
				<div class="buttonArea">
					<input class="formButton" value="确  定" type="button" onclick="document.editTemplateForm.submit();">&nbsp;&nbsp;
            		<input class="formButton" onclick="window.location='${path}/admin/template!list.action?tempId=${tempId}&url=${url}'" value="返  回" type="button">
				</div>
			</form>
		</div>
		<script type="text/javascript">  
			displayDiv('typeBtn','typeDiv','${errors}');
		</script>
	</body>
</html>