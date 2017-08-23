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
		<title>栏目-模板配置</title>
		<!--主体样式 -->
		<link rel="stylesheet" href="${path}/css/manage/base.css" type="text/css"/>
		<link rel="stylesheet" href="${path}/css/manage/admin.css" type="text/css"/>	
		<link rel="stylesheet" href="${path}/css/zTreeStyle/zTreeStyle.css" type="text/css">		
		<!--控制下拉列表树样式-->
		<link rel="stylesheet" href="${path}/css/manage/selectMenu.css" type="text/css">
		 <!--jquery -->
		<script type="text/javascript" src="${path}/js/jquery.js"></script>
		<!--树形下拉列表-->
		<script type="text/javascript" src="${path}/js/ajaxTree.js" ></script>
		<!--ztree实现多选单选-->
		<script type="text/javascript" src="${path}/js/jquery.ztree.core-3.5.js"></script>
		<!--编辑页面js方法-->
		<script type='text/javascript' src='${path}/js/manage/editCommon.js'></script>	
		<script type='text/javascript' src='${path}/dwr/util.js'></script>	
		<SCRIPT type="text/javascript">
			function changeTemp(obj){
				var tempId=obj.value;
				if(tempId==null || tempId==undefined) return;
				$.post('${path}/admin/initTempConfig!getTempAreaIds.action',
						{tempId:tempId,initChannelId:'${initChannelId}'},function(data){					
					   dwr.util.removeAllOptions('initTemplateConfig.areaId');
					   if(data!=null){
						    dwr.util.addOptions('initTemplateConfig.areaId',[{name:'--请选择--',id:''}],"id","name");
	                        for(var i=0,len=data.length;i<len;i++){
	                            dwr.util.addOptions('initTemplateConfig.areaId',[{name:data[i],id:data[i]}],"id","name");
	                        }
                        }else{
                        	 dwr.util.addOptions('initTemplateConfig.areaId',[{name:'--请选择--',id:''}],"id","name");
                        }
				},"json");
			}	
			function showChannelTree(obj){			
				var tempId=document.getElementById("initTemplateConfig.template.id").value;
				var areaId=document.getElementById("initTemplateConfig.areaId").value;
				//var initTempId=document.getElementById("initTempId").value;
				//alert(tempId);alert(areaId);
				if(tempId!=null && tempId!=undefined && areaId!=null && areaId!=undefined)
					showMenu('${path}/admin/initTempConfig!channelTree.action?initTempId=${initTempId}&tempId='+tempId+'&initTemplateConfig.areaId='+areaId,'','keyId','valueId');
			}
		</SCRIPT>
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">栏目-模板配置添加</c:if>
				<c:if test="${edit}">栏目-模板配置修改</c:if>
	        </h3>
	    </div>
		<div class="body">
			<form name="initTempConfigForm" action="${path}/admin/initTempConfig!save.action" method="post">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="edit" value="${edit}"/>
				<input type="hidden" name="initTempId" value="${initTempId}" id="initTempId"/>
				<input type="hidden" name="initChannelId" value="${initChannelId}">
				<input type="hidden" name="channelType" value="${channelType}">
				<input type="hidden" name="contentType" value="${contentType}">
				<input type="hidden" name="channelTempId" value="${channelTempId}">
				<input type="hidden" name="contentTempId" value="${contentTempId}">				
				<input type="hidden" name="tempId" value="${tempId}"/>				
				<input type="hidden" name="initTemplateConfig.id" value="${initTemplateConfig.id}"/>	
				<input type="hidden" name="initTemplateConfig.version" value="${initTemplateConfig.version}"/>	
				<input type="hidden" name="initTemplateConfig.initChannel.id" value="${initTemplateConfig.initChannel.id}"/>	
				<input type="hidden" name="initTemplateConfig.creater" value="${initTemplateConfig.creater}"/>				
				<input type="hidden" name="initTemplateConfig.createTime" value="${initTemplateConfig.createTime}"/>				
				
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">		            				               
			                <li class="inputList-li">			                	
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>模板文件：
		                     		</label>
		                     		<select id="initTemplateConfig.template.id" name="initTemplateConfig.template.id"
		                     			 onchange="changeTemp(this)" class="listbar-sel">		                     			
		                     			<option type="radio" value="" <c:if test="${!edit}"> selected="selected" </c:if>>--请选择--</option>
		                     			<c:forEach var="item" items="${templateList}">
		                     				<option type="radio" value="${item.id}" <c:if test="${edit}"> selected="selected" </c:if> >${item.tempName}</option>
		                     			</c:forEach>
		                     		</select>				                      
				                    <span class="errortip">${errors['initTemplateConfig.template.id'][0]}</span>									
			                   	</div>			                   		                   
			                </li>	
			                <li class="inputList-li">
			                	<div class="listItem">
			                		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>区域编号：
		                     		</label>
		                     		<select name="initTemplateConfig.areaId" id="initTemplateConfig.areaId" 
		                     			class="listbar-sel" onchange="showChannelTree()"
					                     onkeydown="BackSpace('--请选择--','keyId','valueId');">
		                     				<option type="radio" value="" <c:if test="${!edit}"> selected="selected" </c:if>>--请选择--</option>
		                     				<c:if test="${edit}">
		                     					<option type="radio" value="${initTemplateConfig.areaId}" <c:if test="${edit}"> selected="selected" </c:if>>${initTemplateConfig.areaId}</option>
		                     				</c:if>
		                     		</select>				                      
				                    <span class="errortip">${errors['initTemplateConfig.areaId'][0]}</span>		                     		
			                	</div>
			                	<div class="listItem">
			                		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>选择栏目：
		                     		</label>
		                     		<input id="keyId" class="ipt" type="text" readonly="true" onclick="showChannelTree()"
		                     			value="${!empty initTemplateConfig.initChannelSet.id?initTemplateConfig.initChannelSet.channelName:'--请选择--'}" />
				                    <input id="valueId" type="hidden" name="initTemplateConfig.initChannelSet.id" value="${!empty initTemplateConfig.initChannelSet.id?initTemplateConfig.initChannelSet.id:''}"/>
				                    <div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:300px;height: 300px;"></ul></div>
				                    <span class="errortip">${errors['initTemplateConfig.initChannelSet.id'][0]}</span>		                     		
			                	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建人：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" value="${initTemplateConfig.creater}" />
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建时间：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" value="<s:date name="initTemplateConfig.createTime" format="yyyy-MM-dd HH:mm:ss" />" />
			                   	</div>
			                </li>
			            </ul>
			        </div>
			    </div>				
			</form>
			<div class="buttonArea">
				<input class="formButton" value="确  定" type="button" onclick="document.initTempConfigForm.submit()">&nbsp;&nbsp;
           		<input class="formButton" onclick="window.location='${path}/admin/initTempConfig!list.action?initTempId=${initTempId}&initChannelId=${initChannelId}&channelType=${channelType}&contentType=${contentType}&channelTempId=${channelTempId}&contentTempId=${contentTempId}'" value="返  回" type="button">
			</div>
		</div>
	</body>
</html>