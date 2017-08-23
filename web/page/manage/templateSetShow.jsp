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
		<title>查看模板信息</title>
		<!--主体样式 -->
	<link rel="stylesheet" href="${path}/css/manage/base.css" type="text/css"/>
	<link rel="stylesheet" href="${path}/css/manage/admin.css" type="text/css"/>	
	<!--控制下拉列表树样式-->
	<link rel="stylesheet" href="${path}/css/manage/selectMenu.css" type="text/css">
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
				查看模板信息
	        </h3>
	    </div>
		<div class="body">			
			<div class="divInputTable">
	            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
	            <div id="typeDiv">
	            	<ul class="inputList">		            				               
		                <li class="inputList-li">			                	
		                	<div class="listItem">
	                       		<label class="lbl-ipt-tit">
	                     			<span class="star">*</span>区域编号：	                     			
	                     		</label>
	                     		<span class="txt">${templateSet.areaId}</span>
		                   	</div>	
		                   	<div class="listItem">
		                   		<label class="lbl-ipt-tit">
	                     			包含子区域：	                     			
	                     		</label>	
	                     		<span class="txt">
                     				<c:if test="${templateSet.hasSubArea}">是</c:if>
                     				<c:if test="${!templateSet.hasSubArea}">否</c:if>
	                     		</span>                     		
		                   	</div>		                  
		                </li>			               
		                <li class="inputList-li">
		                	<div class="listItem">
	                       		<label class="lbl-ipt-tit">
	                     			<span class="star">*</span>栏目类型：
	                     		</label>
	                     		<span class="txt">
		                     		<c:forEach var="item" items="${channelTypeList}">
		                     			<c:if test="${item.key==templateSet.channelType}">${item.value}</c:if>			                     		
		                     		</c:forEach>
	                     		</span>			                   
		                   	</div>
		                   	<div class="listItem">
	                       		<label class="lbl-ipt-tit">
	                     			<span class="star">*</span>模型：
	                     		</label>
	                     		<span class="txt">
		                     		<c:forEach var="item" items="${modelList}">
			                     			<c:if test="${item.id==templateSet.model.id}">${item.modelName}</c:if>			                     		
			                     	</c:forEach>
			                    </span>    
		                   	</div>
		                </li>	 
		                <li class="inputList-li">
		                	<div class="listItem">
	                       		<label class="lbl-ipt-tit">
	                     			<span class="star">*</span>内容类型：
	                     		</label>		     
	                     		<span class="txt">    
	                     			<c:forEach var="item" items="${ContentTypeList}">
		                     			<c:if test="${item.key==templateSet.contentType}">${item.value}</c:if>			                     		
		                     		</c:forEach>
	                     		</span>  
		                   	</div>
		                   	<div class="listItem">
	                       		<label class="lbl-ipt-tit">
	                     			<span class="star">*</span>栏目限额：
	                     		</label>
	                     		<span class="txt">${templateSet.maxChannelSize}</span>		                        
		                    </div>
		                </li>
		                <li class="inputList-li">
		                	<div class="listItem">
	                       		<label class="lbl-ipt-tit">
	                     			<span class="star">*</span>总列表大小：
	                     		</label>
	                     		<span class="txt">${templateSet.listSize}</span>			                       
		                   	</div>
		                	<div class="listItem">
	                       		<label class="lbl-ipt-tit">
	                     			<span class="star">*</span>特殊列表大小：
	                     		</label>
	                     		<span class="txt">${templateSet.imgSize}</span>		                      
		                   	</div>
		                </li>
		                <li class="inputList-li">			                	
		                	<div class="listItem">
	                       		<label class="lbl-ipt-tit">
	                     			<span class="star">*</span>图文：
	                     		</label>	
	                     		<span class="txt">
		                     		<c:forEach var="item" items="${SpecialContentTypeList}">
		                     			<c:if test="${item.key==templateSet.specialContentType}">${item.value}</c:if>			                     		
		                     		</c:forEach>
	                     		</span>		                     		
		                   	</div>
		                </li>
		            </ul>
		        </div>
		    </div>				
			<div class="buttonArea">				
           		<input class="formButton" onclick="window.location='${path}/admin/templateSet!list.action?tempId=${templateSet.tempId}'" value="返  回" type="button">
			</div>
		</div>
	</body>
</html>