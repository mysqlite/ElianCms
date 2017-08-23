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
		<title>编辑模板信息</title>
		<!--主体样式 -->
<link rel="stylesheet" href="${path}/css/manage/base.css" type="text/css"/>
<link rel="stylesheet" href="${path}/css/manage/admin.css" type="text/css"/>	
<!--控制下拉列表树样式-->
<link rel="stylesheet" href="${path}/css/manage/selectMenu.css" type="text/css">
 <!--jquery -->
<script type="text/javascript" src="${path}/js/jquery.js"></script>
<!--编辑页面js方法-->
<script type='text/javascript' src='${path}/js/manage/editCommon.js'></script>
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">模板信息添加</c:if>
				<c:if test="${edit}">模板信息修改</c:if>
	        </h3>
	    </div>
		<div class="body">
			<form  action="${path}/admin/templateSet!save.action" method="post">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="edit" value="${edit}"/>
				<input type="hidden" name="tempId" value="${tempId}"/>				
				<input type="hidden" name="templateSet.id" value="${templateSet.id}"/>				
				<input type="hidden" name="templateSet.version" value="${templateSet.version}"/>
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">		            				               
			                <li class="inputList-li">			                	
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>区域编号：
		                     		</label>
		                     		<select name="templateSet.areaId" id="subIdSelect" class="listbar-sel"
		                     			<c:if test="${edit}"> style="display: none" disabled="disabled" </c:if>>
		                     			<c:forEach var="item" items="${areaIdList}">
		                     					<option type="radio" value="${item}">${item}</option>
		                     			</c:forEach>
		                     		</select>	
		                     		<c:if test="${edit}"> <input type="hidden" name="templateSet.areaId" value="${templateSet.areaId}"/> </c:if>	                     		
			                        <input type="text"  disabled="disabled" class="ipt" name="templateSet.areaId" value="${templateSet.areaId}" 
			                        	<c:if test="${!edit}"> style="display: none" </c:if>/>
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>templateSet.areaId</s:param>
									    </s:fielderror>
									</span>
			                   	</div>	
			                   	<div class="listItem">
			                   		<label class="lbl-ipt-tit">
		                     			包含子区域：
		                     		</label>
		                     		<c:if test="${!edit}">
		                     			<label class="lbl-ipt-tit radioWrap">
		                     				<input type="radio" <c:if test="${templateSet.hasSubArea}"> checked="checked"</c:if> name="templateSet.hasSubArea" 
		                     					value="true" onclick="changeContentType()">是</input>
		                     			</label>
		                     			<label class="lbl-ipt-tit radioWrap">
		                     				<input type="radio" <c:if test="${!templateSet.hasSubArea}"> checked="checked"</c:if> name="templateSet.hasSubArea"
	                     						 value="false" onclick="changeContentType()">否</input>
		                     			</label>											
			                   		</c:if>
			                   		<c:if test="${edit}">			                   		
			                   			<input type="hidden" name="templateSet.hasSubArea" value="${templateSet.hasSubArea}"/>
			                   			<label class="lbl-ipt-tit radioWrap">
		                     				<input type="radio" <c:if test="${templateSet.hasSubArea}"> checked="checked"</c:if> name="templateSet.hasSubArea" value="true" disabled="disabled">是</input>
		                     			</label>
		                     			<label class="lbl-ipt-tit radioWrap">
		                     				<input type="radio" <c:if test="${!templateSet.hasSubArea}"> checked="checked"</c:if> name="templateSet.hasSubArea" value="false" disabled="disabled">否</input>
		                     			</label>	
			                   		</c:if>
			                   	</div>		                  
			                </li>			               
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>栏目类型：
		                     		</label>
		                     		<c:if test="${edit}"><input type="hidden" name="templateSet.channelType" value="${templateSet.channelType}"/></c:if>
		                     		<c:forEach var="item" items="${channelTypeList}">
		                     			<label class="lbl-ipt-tit radioWrap">
			                     			<input type="radio" onclick="changeContentType()"  <c:if test="${edit}"> disabled="disabled" </c:if> name="templateSet.channelType" value="${item.key}"
												<c:if test="${item.key==templateSet.channelType}"> checked="checked" </c:if> />
												${item.value}
										</label>
		                     		</c:forEach>
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>templateSet.channelType</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>内容模型：
		                     		</label>
		                     		<c:if test="${edit}"><input type="hidden" name="templateSet.model.id" value="${templateSet.model.id}"/></c:if>
		                     		<select id="model" f="sss" onchange="channgeModel(this)" <c:if test="${edit}"> disabled="disabled" </c:if> class="listbar-sel" name="templateSet.model.id">		                     			
		                     			<option type="radio" value="0">--请选择--</option>	                     		
		                     			<c:forEach var="item" items="${modelList}">		
			                     			<option type="radio" value="${item.id}" <c:if test="${item.contentModel.actionName=='information_c'}"> f="aaa" </c:if>
												<c:if test="${item.id==templateSet.model.id}"> selected="selected" </c:if>
												>
												${item.modelName}
											</option>																						
		                     			</c:forEach>	
		                     		</select>		                     				                       
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>templateSet.model.id</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>	 
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>内容类型：
		                     		</label>		                    
									<c:if test="${edit}"><input type="hidden" name="templateSet.contentType" value="${templateSet.contentType}"/></c:if>		
		                     		<c:forEach var="item" items="${ContentTypeList}">
		                     			<label class="lbl-ipt-tit radioWrap">
			                     			<input type="radio"  <c:if test="${edit}"> disabled="disabled" </c:if> name="templateSet.contentType" value="${item.key}"
												<c:if test="${item.key==templateSet.contentType}"> checked="checked" </c:if>  onclick="changeContentType()"/>
												${item.value}
										</label>
		                     		</c:forEach>
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>templateSet.contentType</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                   	<div class="listItem" id="templateSet.maxChannelSize" >
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>最大栏目数：
		                     		</label>
		                     		<c:if test="${edit && templateSet.hasSubArea}">
				                        <input type="text" class="ipt" disabled="disabled" name="templateSet.maxChannelSize" value="${templateSet.maxChannelSize}" />
				                        <input type="hidden" name="templateSet.maxChannelSize" value="${templateSet.maxChannelSize}" />
		                     		</c:if>		                     		
		                     		<c:if test="${!edit || !templateSet.hasSubArea}">
				                        <input type="text" class="ipt" name="templateSet.maxChannelSize" value="${templateSet.maxChannelSize}" />
		                     		</c:if>
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>templateSet.maxChannelSize</s:param>
									    </s:fielderror>
									</span>
			                    </div>
			                </li>
			                <li class="inputList-li" id="size">
			                	<div class="listItem" id="templateSet.listSize">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>总列表大小：
		                     		</label>
			                        <input type="text" class="ipt" name="templateSet.listSize" value="${templateSet.listSize}" />
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>templateSet.listSize</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                	<div class="listItem" id="templateSet.imgSize" <c:if test="${templateSet.specialContentType==0}"> style="display: none;"</c:if>>
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>特殊列表大小：
		                     		</label>
			                        <input type="text" class="ipt" name="templateSet.imgSize" value="${templateSet.imgSize}" />
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>templateSet.imgSize</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">			                	
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>页面表现：
		                     		</label>
		                     		<select id="templateSet.specialContentType" class="listbar-sel" name="templateSet.specialContentType" onchange="changeContentType()">		                     			
		                     			<c:forEach var="item" items="${SpecialContentTypeList}" varStatus="var">			                     		
			                     			<option type="radio" value="${item.key}" 
												<c:if test="${item.key==templateSet.specialContentType}"> selected="selected" </c:if>>
												${item.value}
											</option>																						
		                     			</c:forEach>	
		                     		</select>		                     				                       
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>templateSet.specialContentType</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>
			            </ul>
			        </div>
			    </div>				
			</form>
				<div class="buttonArea">
					<input class="formButton" value="确  定" type="button" onclick="document.forms[0].submit()">&nbsp;&nbsp;
	           		<input class="formButton" onclick="window.location='${path}/admin/templateSet!list.action?tempId=${tempId}'" value="返  回" type="button">
				</div>
		</div>
		<SCRIPT type="text/javascript">	
			function showListSize(){
				var obj;
				var elements=document.getElementsByName("templateSet.contentType");
				for(var i=0;i<elements.length;i++){
					if(elements[i].checked==true){	
						obj=elements[i];
						break;
					}
				}
				if(obj.value=="list"){
					document.getElementById("templateSet.imgSize").style.display='block';
					document.getElementById("size").style.display='block';
					var specialContentType= document.getElementById("templateSet.specialContentType").value;
					if(specialContentType==0)
						document.getElementById("templateSet.imgSize").style.display='none';
					
				}else{
					document.getElementById("templateSet.imgSize").style.display='none';
					document.getElementById("size").style.display='none';
				}
			}
			
			function showMaxChannelSize(){
				var hasSubAreas=false;
				var channelType="content";
				var elements=document.getElementsByName("templateSet.hasSubArea");
				for(var i=0;i<elements.length;i++){
					if(elements[i].checked==true){	
						if("true"==elements[i].value)
							hasSubAreas=true;
						break;
					}
				}
				elements=document.getElementsByName("templateSet.channelType");
				for(var i=0;i<elements.length;i++){
					if(elements[i].checked==true){						
						channelType=elements[i].value;
						break;
					}
				}
				if(!hasSubAreas&&channelType=="content")
					document.getElementById("templateSet.maxChannelSize").style.display='none';
				else
					document.getElementById("templateSet.maxChannelSize").style.display='block';
			}
			
			function showSpecialContentType(){				
				var radios= document.getElementsByName("templateSet.contentType");
				var contentType="list";
				for(var i=0;i<radios.length;i++){
					if(radios[i].checked)
						contentType=radios[i].value;
				}
				if(contentType=="list"){
					document.getElementById("templateSet.imgSize").style.display='none';
					var specialContentType= document.getElementById("templateSet.specialContentType").value;
					if(specialContentType == 1)
						document.getElementById("templateSet.imgSize").style.display='block';	
					if(specialContentType ==3)
						powerPoint();
				}else{				
					document.getElementById("templateSet.imgSize").style.display='none';	
				}
			}
			
			function changeContentType(){
				showListSize();
				showMaxChannelSize();
				showSpecialContentType();
			}	
			
			changeContentType();
		</SCRIPT>
	</body>
</html>