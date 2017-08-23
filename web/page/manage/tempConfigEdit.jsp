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
				$.post('${path}/admin/tempConfig!getTempAreaIds.action',
						{tempId:tempId,channelId:${channelId}},function(data){					
					   dwr.util.removeAllOptions('templateConfig.areaId');
					   if(data!=null){
						    dwr.util.addOptions('templateConfig.areaId',[{name:'--请选择--',id:''}],"id","name");
	                        for(var i=0,len=data.length;i<len;i++){
	                            dwr.util.addOptions('templateConfig.areaId',[{name:data[i],id:data[i]}],"id","name");
	                        }
                        }else{
                        	 dwr.util.addOptions('templateConfig.areaId',[{name:'--请选择--',id:''}],"id","name");
                        }
				},"json");
			}	
			function showChannelTree(obj){			
				var tempId=document.getElementById("templateConfig.template.id").value;
				var areaId=document.getElementById("templateConfig.areaId").value;
				if(tempId!=null && tempId!=undefined && areaId!=null && areaId!=undefined)
					showMenu('${path}/admin/tempConfig!channelTree.action?tempId='+tempId+'&templateConfig.areaId='+areaId,'','keyId','valueId');
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
			<form  action="${path}/admin/tempConfig!save.action" method="post">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="edit" value="${edit}"/>
				<input type="hidden" name="channelId" value="${channelId}">
				<input type="hidden" name="channelType" value="${channelType}">
				<input type="hidden" name="contentType" value="${contentType}">
				<input type="hidden" name="channelTempId" value="${channelTempId}">
				<input type="hidden" name="contentTempId" value="${contentTempId}">				
				<input type="hidden" name="tempId" value="${tempId}"/>				
				<input type="hidden" name="templateConfig.id" value="${templateConfig.id}"/>	
				<input type="hidden" name="templateConfig.version" value="${templateConfig.version}"/>	
				<input type="hidden" name="templateConfig.channel.id" value="${templateConfig.channel.id}"/>	
				<input type="hidden" name="templateConfig.creater" value="${templateConfig.creater}"/>				
				<input type="hidden" name="templateConfig.createTime" value="${templateConfig.createTime}"/>				
				
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">		            				               
			                <li class="inputList-li">			                	
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>模板文件：
		                     		</label>
		                     		<select id="templateConfig.template.id" name="templateConfig.template.id"
		                     			 onchange="changeTemp(this)" class="listbar-sel">		                     			
		                     			<option type="radio" value="" <c:if test="${!edit}"> selected="selected" </c:if>>--请选择--</option>
		                     			<c:forEach var="item" items="${templateList}">
		                     				<option type="radio" value="${item.id}" <c:if test="${edit && templateConfig.template.id==item.id}"> selected="selected" </c:if> >${item.tempName}</option>
		                     			</c:forEach>
		                     		</select>				                      
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>templateConfig.template.id</s:param>
									    </s:fielderror>
									</span>									
			                   	</div>			                   		                   
			                </li>	
			                <li class="inputList-li">
			                	<div class="listItem">
			                		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>区域编号：
		                     		</label>
		                     		<select name="templateConfig.areaId" id="templateConfig.areaId" 
		                     			class="listbar-sel" onchange="showChannelTree()"
					                     onkeydown="BackSpace('--请选择--','keyId','valueId');">
		                     				<option type="radio" value="" <c:if test="${!edit}"> selected="selected" </c:if>>--请选择--</option>
		                     				<c:if test="${edit}">
		                     					<option type="radio" value="${templateConfig.areaId}" <c:if test="${edit}"> selected="selected" </c:if>>${templateConfig.areaId}</option>
		                     				</c:if>
		                     		</select>				                      
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>templateConfig.areaId</s:param>
									    </s:fielderror>
									</span>		                     		
			                	</div>
			                	<div class="listItem">
			                		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>选择栏目：
		                     		</label>
		                     		<input id="keyId" class="ipt" type="text" readonly="true" onclick="showChannelTree()"
		                     			value="${!empty templateConfig.channelSet.id?templateConfig.channelSet.channelName:'--请选择--'}" />
				                    <input id="valueId" type="hidden" name="templateConfig.channelSet.id" value="${!empty templateConfig.channelSet.id?templateConfig.channelSet.id:''}"/>
				                    <div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:300px;height: 300px;"></ul></div>
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>templateConfig.channelSet.id</s:param>
									    </s:fielderror>
									</span>		                     		
			                	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建人：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" value="${templateConfig.creater}" />
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建时间：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" value="<s:date name="templateConfig.createTime" format="yyyy-MM-dd HH:mm:ss" />" />
			                   	</div>
			                </li>
			            </ul>
			        </div>
			    </div>				
			</form>
			<div class="buttonArea">
				<input class="formButton" value="确  定" type="button" onclick="document.forms[0].submit()">&nbsp;&nbsp;
           		<input class="formButton" onclick="window.location='${path}/admin/tempConfig!list.action?&channelId=${channelId}&channelType=${channelType}&contentType=${contentType}&channelTempId=${channelTempId}&contentTempId=${contentTempId}'" value="返  回" type="button">
			</div>
		</div>
	</body>
</html>