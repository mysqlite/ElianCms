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
		<title>编辑分站</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">分站添加</c:if>
				<c:if test="${edit}">分站修改</c:if>
	        </h3>
	    </div>
		<div class="body">
			<form name="editSubstationForm" action="${path}/admin/substation!save.action" method="post">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="substation.id" value="${substation.id}">
				<input type="hidden" name="substation.version" value="${substation.version}">
				<input type="hidden" name="substation.createTime" value="${substation.createTime}"/>
				<input type="hidden" name="edit" value="${edit}">
				
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">
		            		<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否启用：
		                     		</label>
				                    <c:forEach items="${substationStatusList}" var="item" varStatus="e">
				                     <label class="lbl-ipt-tit radioWrap">
				                         <input type="radio" name="substation.status" value="${item.key}" <c:if test="${substation.status==item.key or empty hospital.status}">checked </c:if> />
				                            ${item.value}
				                     </label>
				                    </c:forEach>
			                   	</div>
			                   	<div class="listItem">
		                     		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>区域：
		                     		</label>
			                        <div>
									    <input id="keyId" class="ipt" type="text" readonly="true" name="areaName" value="${areaName}" 
									    	onfocus="showMenuArea('treeContent','keyId','valueId','${path}');" onkeydown="checkBackSpace()"/>
									    <input id="valueId" type="hidden" name="substation.areaId" value="${substation.areaId}"/>
									    <div id="treeDiv"><ul id="treeContent" class="ztree" style="width:200px;"/></div>
									</div>
									<span class="errortip">
										<s:fielderror >
										    <s:param>substation.areaId</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>
		            		<li class="inputList-li">
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>分站名称：
		                     		</label>
			                        <input type="text" class="ipt" name="substation.subName" value="${substation.subName}" />
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>substation.subName</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>分站简称：
		                     		</label>
		                     		<input type="text" class="ipt" name="substation.shortName" value="${substation.shortName}" />
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>substation.shortName</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>简介：
		                     		</label>
		                     		<textarea name="substation.shortDesc" class="formTextarea valid">${substation.shortDesc}</textarea>
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>substation.shortDesc</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>联系电话：
		                     		</label>
		                     		<input type="text" class="ipt" name="substation.phone" value="${substation.phone}" />
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>substation.phone</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>地址：
		                     		</label>
		                     		<input type="text" class="ipt" name="substation.address" value="${substation.address}" />
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>substation.address</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>邮政编码：
		                     		</label>
		                     		<input type="text" class="ipt" name="substation.postcode" value="${substation.postcode}" />
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>substation.postcode</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			传真号码：
		                     		</label>
		                     		<input type="text" class="ipt" name="substation.fax" value="${substation.fax}" />
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>substation.fax</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			电子邮箱：
		                     		</label>
			                        <input type="text" class="ipt" name="substation.email" value="${substation.email}" />
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>substation.email</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                    <div class="listItem txtarea">
			                    	<label class="lbl-ipt-tit">分站描述：</label>
			                        <textarea name="substation.subDesc" class="formTextarea valid">${substation.subDesc}</textarea>
			                    </div>
			                </li>
			            </ul>
			        </div>
			    </div>
				<div class="buttonArea">
					<input class="formButton" value="确  定" type="button" onclick="document.editSubstationForm.submit();">&nbsp;&nbsp;
            		<input class="formButton" onclick="window.location='${path}/admin/substation!list.action'" value="返  回" type="button">
				</div>
			</form>
		</div>
		<script type="text/javascript">  
			displayDiv('typeBtn','typeDiv','${errors}');
		</script>
	</body>
</html>