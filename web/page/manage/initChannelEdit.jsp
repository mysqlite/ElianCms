﻿﻿<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>编辑栏目</title>
		<!--主体样式 -->
		<link rel="stylesheet" href="${path}/css/manage/base.css" type="text/css"/>
		<link rel="stylesheet" href="${path}/css/manage/admin.css" type="text/css"/>
		<!--控制下拉列表树样式-->
		<link rel="stylesheet" href="${path}/css/manage/selectMenu.css" type="text/css">
		<link rel="stylesheet" href="${path}/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<!--控制普通下拉列表 -->
		<link rel="stylesheet" href="${path}/js/manage/ui-select/select2css.css" type="text/css"/>  
		<!--弹出层样式控制-->
		<link rel="stylesheet" href='${path}/css/manage/jquery-webox.css' type="text/css">
		<!--树形下拉列表-->
		<script type="text/javascript" src="${path}/js/ajaxTree.js" ></script>
		 <!--jquery -->
		<script type="text/javascript" src="${path}/js/jquery-1.4.4.min.js"></script>
		<!--ztree实现多选单选-->
		<script type="text/javascript" src="${path}/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="${path}/js/jquery.ztree.excheck-3.5.js"></script>
		<!--编辑页面js方法-->
		<script type='text/javascript' src='${path}/js/manage/editCommon.js'></script>
		<!--编辑器-->
		<script src="${path}/plugin/ckeditor/ckeditor.js" type="text/javascript"></script>
		<script src="${path}/plugin/ckeditor/config.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript" src="${path}/js/manage/editPage.js"></script>
		<script type="text/javascript">
			function display(){
				for(var i = 0;i<arguments.length; i++) {
					arguments[i].style.display='none';
				}
			}
			
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
			
			function changeChannelType(value){
				var parentPanel = document.getElementById("parentPanel");
				var outPanel = document.getElementById("outPanel");
				var contentPanel = document.getElementById("contentPanel");
				var specialPanel = document.getElementById("specialPanel");
				var indexPanel = document.getElementById("indexPanel");
				if(value=="out"){
					outPanel.style.display='';
					display(parentPanel, contentPanel, specialPanel, indexPanel);
					disabledFalse(outPanel);
					disabledTrue(parentPanel, contentPanel, specialPanel, indexPanel);
					
					showPath(true, false);
				}
				else if(value=="parent"){
					parentPanel.style.display='';
					display(outPanel, contentPanel, specialPanel, indexPanel);
					disabledFalse(parentPanel);
					disabledTrue(outPanel, contentPanel, specialPanel, indexPanel);
					
					showPath(true, true);
				}
				else if(value=="content"){
					contentPanel.style.display='';
					display(outPanel, parentPanel, specialPanel, indexPanel);
					disabledFalse(contentPanel);
					disabledTrue(outPanel, parentPanel, specialPanel, indexPanel);
					
					showPath(true, true);
				}
				else{
					indexPanel.style.display='';
					display(outPanel, parentPanel, contentPanel, specialPanel);
					disabledFalse(indexPanel);
					disabledTrue(outPanel, parentPanel, contentPanel, specialPanel);
					
					showPath(true, false);
				}
			}
			
			//只有是导航，栏目类型为父栏目或者内容栏目时，显示静态文件路径
			function changeNav(value){
				showPath(value == 'true', isContentOrParetChannelType());
			}
			
			//显示或者隐藏静态文件路径文本框
			function showPath(isNav, isContentOrParetChannelType){
				var pathPanel = document.getElementById("pathPanel");
				if(isNav && isContentOrParetChannelType){
					pathPanel.style.display='';
					disabledFalse(pathPanel);
				}
				else {
					pathPanel.style.display='none';
					disabledTrue(pathPanel);
				}
			}
			
			//判断栏目类型是否是父栏目或者内容栏目
			function isContentOrParetChannelType(){
				var channelTypes = document.getElementsByName("channel.channelType");
				for(var i=0,len=channelTypes.length;i<len;i++){
 					if(channelTypes[i].checked && (channelTypes[i].value=="special" || channelTypes[i].value=="parent" ||　channelTypes[i].value=="content")){
 						 return true;
 					}
				}
				return false;
			}
			
			//判断栏目类型是否是父栏目或者内容栏目
			function isNav(){
				var navs = document.getElementsByName("channel.navigetor");
				for(var i=0,len=navs.length;i<len;i++){
 					if(navs[i].checked && navs[i].value=="true"){
 						 return true;
 					}
				}
				return false;
			}
			
			function changeContentType(value){
				var listPanel = document.getElementById("listPanel");
				if(value=="list"){
					listPanel.style.display='';
					disabledFalse(listPanel);
				}
				else{
					listPanel.style.display='none';
					disabledTrue(listPanel);
				}
			}
		</script>
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">栏目添加</c:if>
				<c:if test="${edit}">栏目修改</c:if>
	        </h3>
	    </div>
		<div class="body">
			<form name="editinitChannelForm" action="${path}/admin/initChannel!save.action" method="post">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="tempId" value="${tempId}"/>
				<input type="hidden" name="parentId" value="${parentId}"/>
				<input type="hidden" name="modelId" value="${modelId}"/>
				<input type="hidden" name="initChannel.id" value="${initChannel.id}">
				<input type="hidden" name="initChannel.static" value="${initChannel.static}">
				<input type="hidden" name="initChannel.version" value="${initChannel.version}">
				<input type="hidden" name="initChannel.creater" value="${initChannel.creater}">
				<input type="hidden" name="initChannel.createTime" value="${initChannel.createTime}"/>
				<input type="hidden" name="initChannel.frontSort" value="${initChannel.frontSort}"/>
				<input type="hidden" name="edit" value="${edit}">
				<input type="hidden" name="navigateId" value="${navigateId}"/>
				<c:if test="${edit}">
				    <c:if test="${!empty tempPath }">
					 <%--<input type="hidden" class="ipt" name="tempPath" value="${tempPath}"/>
					 --%><input type="hidden" class="ipt" name="initChannel.contentType" value="${initChannel.contentType}"/>
					</c:if>
					<input type="hidden" class="ipt" name="initChannel.channelType" value="${initChannel.channelType}"/>
					<input type="hidden" class="ipt" name="initChannel.model.id" value="${initChannel.model.id}"/>
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
										<input type="radio" name="initChannel.disable" value="true"
											<c:if test="${initChannel.disable}"> checked="checked" </c:if>/>
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="initChannel.disable" value="false"<c:if test="${!initChannel.disable}"> checked="checked" </c:if> 
											/>
											否
									</label>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否导航：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="initChannel.navigetor" value="true" <c:if test="${initChannel.navigetor}"> checked="checked" </c:if> 
											/>
										是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="initChannel.navigetor" value="false" <c:if test="${!initChannel.navigetor}"> checked="checked" </c:if>
											/>
										否
									</label>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>栏目名称：
		                     		</label>
			                        <input type="text" class="ipt" name="initChannel.channelName" value="${initChannel.channelName}" />
				                    <span class="errortip">${errors['initChannel.channelName'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			父栏目：
		                     		</label>
			                        <div>
									    <input id="keyId" class="formText" type="text" readonly="readonly" value="${parentName}" 
									    	onfocus="showMenu('${path}/admin/initChannel!parentTreeAjax.action?tempId=${tempId}'); return false;" onkeydown="checkBackSpace()"/>
									    <input id="valueId" type="hidden" name="initChannel.parentId" value="${initChannel.parentId}"/>
									    <div id="treeDiv"><ul id="treeContent" class="ztree" style="width:200px;"></ul></div>
									</div>
				                    <span class="errortip">${errors['initChannel.parentId'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>排序：
		                     		</label>
									<input type="text" class="ipt" name="initChannel.sort" value="${initChannel.sort}" onkeyup="intFormat(this,1,99);"/>
									<span class="errortip">${errors['initChannel.sort'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			SEO标题：
		                     		</label>
			                        <input type="text" class="ipt" name="initChannel.title" value="${initChannel.title}" />
				                    <span class="errortip">${errors['initChannel.title'][0]}</span>
			                   	</div>
			                  	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			SEO关键字：
		                     		</label>
			                        <input type="text" class="ipt" name="initChannel.keywords" value="${initChannel.keywords}" />
				                    <span class="errortip">${errors['initChannel.keywords'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			SEO描述：
		                     		</label>
		                     		<textarea name="initChannel.description" class="formTextarea valid">${initChannel.description}</textarea>
				                    <span class="errortip">${errors['initChannel.description'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                   	<div class="listItem">
			                        <label class="lbl-ipt-tit">
		                     			<span class="star">*</span>栏目类型：
		                     		</label>
									<c:forEach var="item" items="${channelTypeList}">
										<label class="lbl-ipt-tit radioWrap">
											<input type="radio" name="initChannel.channelType" value="${item.key}" onchange="changeChannelType(this.value);" <c:if test="${initChannel.channelType == item.key}"> checked="checked" </c:if> 
												<c:if test="${edit}"> disabled="disabled" </c:if>/>
											${item.value}
										</label>
									</c:forEach>
			                   	</div>
			                   	<div id="pathPanel" style="display: <c:if test="${!(channelType == 'parent' || channelType == 'content')}"> none </c:if>">
				                   	<div class="listItem">
				                     	<label class="lbl-ipt-tit">
				                   			<span class="star">*</span>静态页路径：
				                   		</label>
				                        <input type="text" class="ipt" name="tempPath" value="${tempPath}"
				                        	<c:if test="${(edit || !(channelType == 'parent' || channelType == 'content')) and !empty channel.path }"> disabled="disabled" </c:if>/>
				                    	<span class="errortip">${errors['initChannel.path'][0]}</span>
				                  	</div>
				                </div>
			                </li>
			                <div id="parentPanel" style="display: <c:if test="${channelType != 'parent'}"> none </c:if>">
				                <li class="inputList-li">
				                	<div class="listItem">
			                       		<label class="lbl-ipt-tit">
			                     			栏目模板：
			                     		</label>
				                        <div class="select">
					                        <select name="initChannel.channelTemp.id" class="listbar-sel" 
					                        	<c:if test="${channelType != 'parent'}"> disabled="disabled" </c:if>>
												<option value="">--请选择--</option>
												<c:forEach var="f" items="${channelTempList}">
													<option value="${f.id}" <c:if test="${f.id == initChannel.channelTemp.id}">selected="selected"</c:if>>${f.tempName}</option>
												</c:forEach>
											</select>
					                    </div>
					                    <span class="errortip">${errors['initChannel.channelTempUrl'][0]}</span>
				                   	</div>
				                </li>
				            </div>
			                <div id="contentPanel" style="display: <c:if test="${channelType != 'content'}"> none </c:if>">
				                <li class="inputList-li">
				                   	<div class="listItem">
			                       		<label class="lbl-ipt-tit">
			                     			<span class="star">*</span>模型</span>：
			                     		</label>
				                        <div class="select">
					                        <select id="modelId" class="listbar-sel" name="initChannel.model.id" 
					                        	onchange="changeSelect('${path}/admin/initChannel!changeSelectAjax.action?tempId=${tempId}','modelId', 'contentTempId', 'listTempId')"
					                        	<c:if test="${edit || channelType != 'content'}"> disabled="disabled" </c:if>>
					                        	<option value="">--请选择--</option>
												<c:forEach var="model" items="${modelList}">
													<option value="${model.id}" <c:if test="${initChannel.model.id == model.id}">selected="selected"</c:if>>${model.modelName}</option>
												</c:forEach>
											</select>
										</div>
					                    <span class="errortip">${errors['initChannel.model.id'][0]}</span>
				                   	</div>
				                </li>
				                <li class="inputList-li">
				                	<div class="listItem">
				                        <label class="lbl-ipt-tit">
			                     			<span class="star">*</span>内容类型：
			                     		</label>
										<c:forEach var="item" items="${contentTypeList}">
											<label class="lbl-ipt-tit radioWrap">
												<input type="radio" name="initChannel.contentType" value="${item.key}" 
													onchange="changeContentType(this.value);"
													<c:if test="${initChannel.contentType == item.key}"> checked="checked" </c:if> 
													<c:if test="${(edit || channelType != 'content')and !empty initChannel.path}"> disabled="disabled" </c:if>/>
													${item.value}
											</label>
										</c:forEach>
				                   	</div>
				                   	<div id="listPanel" style="display: <c:if test="${channelType != 'content' || initChannel.contentType != 'list'}"> none </c:if>">
					                   	<div class="listItem">
				                       		<label class="lbl-ipt-tit">
				                     			列表模板：
				                     		</label>
				                     		<div class="select">
						                        <select id="listTempId" name="initChannel.channelTemp.id" class="listbar-sel"
						                        	<c:if test="${channelType != 'content' || initChannel.contentType != 'list'}"> disabled="disabled" </c:if>>
													<option value="">--请选择--</option>
													<c:forEach var="f" items="${listTempList}">
														<option value="${f.value}" <c:if test="${f.value == initChannel.channelTemp.id}">selected="selected"</c:if>>${f.key}</option>
													</c:forEach>
												</select>
						                     </div>
						                    <span class="errortip">${errors['initChannel.listTempUrl'][0]}</span>
					                   	</div>
						            </div>
				                </li>
				                <li class="inputList-li">
				                   	<div class="listItem">
			                       		<label class="lbl-ipt-tit">
			                     			内容模板：
			                     		</label>
				                        <div class="select">
					                        <select id="contentTempId" name="initChannel.contentTemp.id" class="listbar-sel"
					                        	<c:if test="${channelType != 'content'}"> disabled="disabled" </c:if>>
												<option value="">--请选择--</option>
												<c:forEach var="f" items="${contentTempList}">
													<option value="${f.value}" <c:if test="${f.value == initChannel.contentTemp.id}">selected="selected"</c:if>>${f.key}</option>
												</c:forEach>
											</select>
					                     </div>
					                    <span class="errortip">${errors['initChannel.contentTempUrl'][0]}</span>
				                   	</div>
				                </li>
				            </div>
			                <div id="outPanel" style="display: <c:if test="${channelType != 'out'}"> none </c:if>">
				                <li class="inputList-li">
				                   	<div class="listItem">
				                        <label class="lbl-ipt-tit">
			                     			外部链接：
			                     		</label>
										<input type="text" class="ipt" name="initChannel.outLinkUrl" value="${initChannel.outLinkUrl}" 
											<c:if test="${channelType != 'out'}"> disabled="disabled" </c:if>/>
										<span class="errortip">${errors['initChannel.outLinkUrl'][0]}</span>
				                   	</div>
				                </li>
				            </div>  
			                <div id="specialPanel" style="display: <c:if test="${channelType != 'special'}"> none </c:if>">
				                <li class="inputList-li">
				                   	<div class="listItem">
				                        <label class="lbl-ipt-tit">
			                     			特殊页模板：
			                     		</label>
										<div class="select">
					                        <select name="initChannel.channelTemp.id" class="listbar-sel" 
					                        	<c:if test="${channelType != 'special'}"> disabled="disabled" </c:if>>
												<option value="">--请选择--</option>
												<c:forEach var="f" items="${otherTempList}">
													<option value="${f.id}" <c:if test="${f.id == initChannel.channelTemp.id}">selected="selected"</c:if>>${f.tempName}</option>
												</c:forEach>
											</select>
					                    </div>
										<span class="errortip">${errors['initChannel.specialTempUrl'][0]}</span>
				                   	</div>
				                </li>
			                </div>
			                <div id="indexPanel" style="display: <c:if test="${channelType != 'index'}"> none </c:if>">
				                <li class="inputList-li">
				                   	<div class="listItem">
				                        <label class="lbl-ipt-tit">
			                     			首页模板：
			                     		</label>
										<div class="select">
					                        <select name="initChannel.channelTemp.id" class="listbar-sel" 
					                        	<c:if test="${channelType != 'index'}"> disabled="disabled" </c:if>>
												<option value="">--请选择--</option>
												<c:forEach var="f" items="${indexTempList}">
													<option value="${f.id}" <c:if test="${f.id == initChannel.channelTemp.id}">selected="selected"</c:if>>${f.tempName}</option>
												</c:forEach>
											</select>
					                    </div>
										<span class="errortip">${errors['initChannel.indexTempUrl'][0]}</span>
				                   	</div>
				                </li>
			                </div>
			            </ul>
			        </div>
			    </div>
				<div class="buttonArea">
					<input class="formButton" value="确  定" type="button" onclick="document.editinitChannelForm.submit();">&nbsp;&nbsp;
            		<input class="formButton" onclick="window.location='${path}/admin/initChannel!list.action?tempId=${tempId}&parentId=${parentId}&navigateId=${navigateId}'" value="返  回" type="button">
				</div>
			</form>
		</div>
		<script type="text/javascript">  
			displayDiv('typeBtn','typeDiv','${errors}');
		</script>
	</body>
</html>