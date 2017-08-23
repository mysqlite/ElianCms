﻿<%@ page language="java" pageEncoding="UTF-8"%>
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
				if( parent.disabled==undefined )
					return;
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
					G("channelTempId").disabled="disabled";
                    G("indexChannelTempId").disabled="disabled";
                    G("modelId").disabled="disabled";
                    G("channel_content_type_0").disabled="disabled";
                    G("channel_content_type_1").disabled="disabled";
				}
				else if(value=="parent"){
					parentPanel.style.display='';
					display(outPanel, contentPanel, specialPanel, indexPanel);
					disabledFalse(parentPanel);
					disabledTrue(outPanel, contentPanel, specialPanel, indexPanel);
					showPath(true, true);
					G("channelTempId").disabled="";
					G("indexChannelTempId").disabled="disabled";
					G("modelId").disabled="disabled";
					G("channel_content_type_0").disabled="disabled";
                    G("channel_content_type_1").disabled="disabled";
				}
				else if(value=="content"){
					contentPanel.style.display='';
					display(outPanel, parentPanel, specialPanel, indexPanel);
					disabledFalse(contentPanel);
					disabledTrue(outPanel, parentPanel, specialPanel, indexPanel);
					showPath(true, true);
					G("modelId").disabled="";
					G("channel_content_type_0").disabled="";
					G("channel_content_type_1").disabled="";
					G("channelTempId").disabled="disabled";
					G("indexChannelTempId").disabled="disabled";
				}
				else{
					indexPanel.style.display='';
					display(outPanel, parentPanel, contentPanel, specialPanel);
					disabledFalse(indexPanel);
					disabledTrue(outPanel, parentPanel, contentPanel, specialPanel);
					
					showPath(true, false);
					G("indexChannelTempId").disabled="";
					G("channelTempId").disabled="disabled";
					G("modelId").disabled="disabled";
					G("channel_content_type_0").disabled="disabled";
                    G("channel_content_type_1").disabled="disabled";
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
					G("listTempId").disabled="";
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
			<form name="editChannelForm" action="${path}/admin/channel!save.action" method="post">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="modelId" value="${modelId}"/>
				<input type="hidden" name="channel.id" value="${channel.id}">
				<input type="hidden" name="channel.static" value="${channel.static}">
				<input type="hidden" name="channel.version" value="${channel.version}">
				<input type="hidden" name="channel.creater" value="${channel.creater}">
				<input type="hidden" name="channel.createTime" value="${channel.createTime}"/>
				<input type="hidden" name="channel.frontSort" value="${channel.frontSort}"/>
				<input type="hidden" name="edit" value="${edit}">
				<input type="hidden" name="navigateId" value="${navigateId}"/>
				<c:if test="${edit}">
				    <c:if test="${!empty tempPath }">
					 <input type="hidden" class="ipt" name="tempPath" value="${tempPath}"/>
					</c:if>
					 <input type="hidden" class="ipt" name="channel.contentType" value="${channel.contentType}"/>
					<input type="hidden" class="ipt" name="channel.channelType" value="${channel.channelType}"/>
					<input type="hidden" class="ipt" name="channel.model.id" value="${channel.model.id}"/>
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
										<input type="radio" name="channel.disable" value="true"
											<c:if test="${channel.disable}"> checked="checked" </c:if>/>
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="channel.disable" value="false"<c:if test="${!channel.disable}"> checked="checked" </c:if> 
											/>
											否
									</label>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否导航：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="channel.navigetor" value="true" <c:if test="${channel.navigetor}"> checked="checked" </c:if> 
											/>
										是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="channel.navigetor" value="false" <c:if test="${!channel.navigetor}"> checked="checked" </c:if>
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
			                        <input type="text" class="ipt" name="channel.channelName" value="${channel.channelName}" />
				                    <span class="errortip">${errors['channel.channelName'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			父栏目：
		                     		</label>
			                        <div>
									    <input id="keyId" class="formText" type="text" readonly="readonly" value="${parentName}" 
									    	onfocus="showMenu('${path}/admin/channel!parentTree.action'); return false;" onkeydown="checkBackSpace()"/>
									    <input id="valueId" type="hidden" name="channel.parentId" value="${channel.parentId}"/>
									    <div id="treeDiv"><ul id="treeContent" class="ztree" style="width:200px;"></ul></div>
									</div>
				                    <span class="errortip">${errors['channel.parentId'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>排序：
		                     		</label>
									<input type="text" class="ipt" name="channel.sort" value="${channel.sort}" onkeyup="intFormat(this,1,99);"/>
									<span class="errortip">${errors['channel.sort'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			SEO标题：
		                     		</label>
			                        <input type="text" class="ipt" name="channel.title" value="${channel.title}" />
				                    <span class="errortip">${errors['channel.title'][0]}</span>
			                   	</div>
			                  	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			SEO关键字：
		                     		</label>
			                        <input type="text" class="ipt" name="channel.keywords" value="${channel.keywords}" />
				                    <span class="errortip">${errors['channel.keywords'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			SEO描述：
		                     		</label>
		                     		<textarea name="channel.description" class="formTextarea valid">${channel.description}</textarea>
				                    <span class="errortip">${errors['channel.description'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                   	<div class="listItem">
			                        <label class="lbl-ipt-tit">
		                     			<span class="star">*</span>栏目类型：
		                     		</label>
									<c:forEach var="item" items="${channelTypeList}">
										<label class="lbl-ipt-tit radioWrap">
											<input type="radio" name="channel.channelType" value="${item.key}" onclick="changeChannelType(this.value);" <c:if test="${channel.channelType == item.key}"> checked="checked" </c:if> 
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
				                    	<span class="errortip">${errors['channel.path'][0]}</span>
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
					                        <select id="channelTempId" name="channel.channelTemp.id" class="listbar-sel" 
					                        	<c:if test="${channelType != 'parent'}"> disabled="disabled" </c:if>>
												<option value="">--请选择--</option>
												<c:forEach var="f" items="${channelTempList}">
													<option value="${f.id}" <c:if test="${f.id == channel.channelTemp.id}">selected="selected"</c:if>>${f.tempName}</option>
												</c:forEach>
											</select>
					                    </div>
					                    <span class="errortip">${errors['channel.channelTempUrl'][0]}</span>
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
					                        <select id="modelId" class="listbar-sel" name="channel.model.id" 
					                        	onchange="changeSelect('${path}/admin/channel!changeSelect.action','modelId', 'contentTempId', 'listTempId')"
					                        	<c:if test="${edit || channelType != 'content'}"> disabled="disabled" </c:if>>
					                        	<option value="">--请选择--</option>
												<c:forEach var="model" items="${modelList}">
													<option value="${model.id}" <c:if test="${channel.model.id == model.id}">selected="selected"</c:if>>${model.modelName}</option>
												</c:forEach>
											</select>
										</div>
					                    <span class="errortip">${errors['channel.model.id'][0]}</span>
				                   	</div>
				                </li>
				                <li class="inputList-li">
				                	<div class="listItem">
				                        <label class="lbl-ipt-tit">
			                     			<span class="star">*</span>内容类型：
			                     		</label>
										<c:forEach var="item" items="${contentTypeList}" varStatus="e">
											<label class="lbl-ipt-tit radioWrap">
												<input id="channel_content_type_${e.index}" type="radio" name="channel.contentType" value="${item.key}" 
													onclick="changeContentType(this.value);"
													<c:if test="${channel.contentType == item.key}"> checked="checked" </c:if> 
													<c:if test="${(edit || channelType != 'content') or (edit and !empty channel.path)}"> disabled="disabled" </c:if>/>
													${item.value}
											</label>
										</c:forEach>
				                   	</div>
				                   	<div id="listPanel" style="display: <c:if test="${channelType != 'content' || channel.contentType != 'list'}"> none </c:if>">
					                   	<div class="listItem">
				                       		<label class="lbl-ipt-tit">
				                     			列表模板：
				                     		</label>
				                     		<div class="select">
						                        <select id="listTempId" name="channel.channelTemp.id" class="listbar-sel"
						                        	<c:if test="${channelType != 'content' || channel.contentType != 'list'}"> disabled="disabled" </c:if>>
													<option value="">--请选择--</option>
													<c:forEach var="f" items="${listTempList}">
														<option value="${f.value}" <c:if test="${f.value == channel.channelTemp.id}">selected="selected"</c:if>>${f.key}</option>
													</c:forEach>
												</select>
						                     </div>
						                    <span class="errortip">${errors['channel.listTempUrl'][0]}</span>
					                   	</div>
						            </div>
				                </li>
				                <li class="inputList-li">
				                   	<div class="listItem">
			                       		<label class="lbl-ipt-tit">
			                     			内容模板：
			                     		</label>
				                        <div class="select">
					                        <select id="contentTempId" name="channel.contentTemp.id" class="listbar-sel"
					                        	<c:if test="${channelType != 'content'}"> disabled="disabled" </c:if>>
												<option value="">--请选择--</option>
												<c:forEach var="f" items="${contentTempList}">
													<option value="${f.value}" <c:if test="${f.value == channel.contentTemp.id}">selected="selected"</c:if>>${f.key}</option>
												</c:forEach>
											</select>
					                     </div>
					                    <span class="errortip">${errors['channel.contentTempUrl'][0]}</span>
				                   	</div>
				                </li>
				            </div>
			                <div id="outPanel" style="display: <c:if test="${channelType != 'out'}"> none </c:if>">
				                <li class="inputList-li">
				                   	<div class="listItem">
				                        <label class="lbl-ipt-tit">
			                     			外部链接：
			                     		</label>
										<input type="text" class="ipt" name="channel.outLinkUrl" value="${channel.outLinkUrl}" 
											<c:if test="${channelType != 'out'}"> disabled="disabled" </c:if>/>
										<span class="errortip">${errors['channel.outLinkUrl'][0]}</span>
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
					                        <select name="channel.channelTemp.id" class="listbar-sel" 
					                        	<c:if test="${channelType != 'special'}"> disabled="disabled" </c:if>>
												<option value="">--请选择--</option>
												<c:forEach var="f" items="${otherTempList}">
													<option value="${f.id}" <c:if test="${f.id == channel.channelTemp.id}">selected="selected"</c:if>>${f.tempName}</option>
												</c:forEach>
											</select>
					                    </div>
										<span class="errortip">${errors['channel.specialTempUrl'][0]}</span>
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
					                        <select id="indexChannelTempId" name="channel.channelTemp.id" class="listbar-sel" 
					                        	<c:if test="${channelType != 'index'}"> disabled="disabled" </c:if>>
												<option value="">--请选择--</option>
												<c:forEach var="f" items="${indexTempList}">
													<option value="${f.id}" <c:if test="${f.id == channel.channelTemp.id}">selected="selected"</c:if>>${f.tempName}</option>
												</c:forEach>
											</select>
					                    </div>
										<span class="errortip">${errors['channel.indexTempUrl'][0]}</span>
				                   	</div>
				                </li>
			                </div>
			            </ul>
			        </div>
			    </div>
				<div class="buttonArea">
					<input class="formButton" value="确  定" type="button" onclick="document.editChannelForm.submit();">&nbsp;&nbsp;
            		<input class="formButton" onclick="window.location='${path}/admin/channel!list.action?navigateId=${navigateId}'" value="返  回" type="button">
				</div>
			</form>
		</div>
		<script type="text/javascript">  
			displayDiv('typeBtn','typeDiv','${errors}');
		</script>
	</body>
</html>
