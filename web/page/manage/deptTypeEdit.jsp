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
		<title>编辑科室类型</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">科室类型添加</c:if>
				<c:if test="${edit}">科室类型修改</c:if>
	        </h3>
	    </div>
		<div class="body">
			<form name="editDeptTypeForm" action="${path}/admin/deptType!save.action" method="post">
				<input type="hidden" name="edit" value="${edit}">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="deptType.id" value="${deptType.id}">
				<input type="hidden" name="deptType.version" value="${deptType.version}">
				<input type="hidden" name="deptType.type" value="${deptType.type}">
				
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
										<input type="radio" name="deptType.disable" value="true"
											<c:if test="${deptType.disable}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="deptType.disable" value="false"<c:if test="${!deptType.disable}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			所属父级：
		                     		</label>
		                     		<div>
									    <input id="keyId" class="formText" type="text" readonly="readonly" value="${parentName}" 
									    	onfocus="showMenu('${path}/admin/deptType!tree.action'); return false;" onkeydown="checkBackSpace()"/>
									    <input id="valueId" type="hidden" name="deptType.parentId" value="${deptType.parentId}"/>
									    <div id="treeDiv"><ul id="treeContent" class="ztree" style="width:200px;"></ul></div>
									</div>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>类型名称：
		                     		</label>
			                        <input type="text" class="ipt" name="deptType.typeName" value="${deptType.typeName}" />
				                    <span class="errortip">${errors['deptType.typeName'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>排序：
		                     		</label>
									<input type="text" class="ipt" name="deptType.typeSort" value="${deptType.typeSort}" onkeyup="intFormat(this,1,99);"/>
									<span class="errortip">${errors['deptType.typeSort'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                    <div class="listItem txtarea">
			                    	<label class="lbl-ipt-tit">描述：</label>
			                        <textarea name="deptType.typeDesc" class="formTextarea valid">${deptType.typeDesc}</textarea>
			                        <span class="errortip">${errors['deptType.typeDesc'][0]}</span>
			                    </div>
			                </li>
			            </ul>
			        </div>
			    </div>
				<div class="buttonArea">
					<input class="formButton" value="确  定" type="button" onclick="document.editDeptTypeForm.submit();">&nbsp;&nbsp;
            		<input class="formButton" onclick="window.location='${path}/admin/deptType!list.action'" value="返  回" type="button">
				</div>
			</form>
		</div>
		<script type="text/javascript">  
			displayDiv('typeBtn','typeDiv','${errors}');
		</script>
	</body>
</html>