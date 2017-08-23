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
		<title>栏目-模板查看</title>
		<!--主体样式 -->
		<link rel="stylesheet" href="${path}/css/manage/base.css" type="text/css"/>
		<link rel="stylesheet" href="${path}/css/manage/admin.css" type="text/css"/>	
		<link rel="stylesheet" href="${path}/css/zTreeStyle/zTreeStyle.css" type="text/css">		
		<!--控制下拉列表树样式-->
		<link rel="stylesheet" href="${path}/css/manage/selectMenu.css" type="text/css">		
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">栏目-模板查看</h3>
	    </div>
		<div class="body">
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">		            				               
			                <li class="inputList-li">			                	
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>模板文件：
		                     		</label>		                     										
	                     			<span class="txt">${initTemplateConfig.template.tempName}</span>
			                   	</div>			                   		                   
			                </li>	
			                <li class="inputList-li">
			                	<div class="listItem">
			                		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>区域编号：
		                     		</label>		                     		              		
	                     			<span class="txt">${initTemplateConfig.areaId}</span>		                     			
			                	</div>
			                	<div class="listItem">
			                		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>选择栏目：
		                     		</label>		                     		         		
	                     			<span class="txt">${initTemplateConfig.initChannelSet.channelName}</span>
			                	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建人：
		                     		</label>
		                     		<span class="txt">${initTemplateConfig.creater}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建时间：
		                     		</label>	
		                     		<span class="txt"><fmt:formatDate value="${initTemplateConfig.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>	                     		
			                   	</div>
			                </li>
			            </ul>
			        </div>
			    </div>	
			<div class="buttonArea">
           		<input class="formButton" onclick="window.location='${path}/admin/initTempConfig!list.action?initTempId=${initTempId}&initChannelId=${initChannelId}&channelType=${channelType}&contentType=${contentType}&channelTempId=${channelTempId}&contentTempId=${contentTempId}'" value="返  回" type="button">
			</div>
		</div>
	</body>
</html>