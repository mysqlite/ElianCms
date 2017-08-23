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
		<title>编辑权限</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">权限添加</c:if>
				<c:if test="${edit}">权限修改</c:if>
	        </h3>
	    </div>
		<div class="body">
			<form name="editActionForm" action="${path}/admin/action!save.action" method="post">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="action.id" value="${action.id}">
				<input type="hidden" name="action.version" value="${action.version}">
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
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="action.disable" value="true"
											<c:if test="${action.disable}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="action.disable" value="false"
											<c:if test="${!action.disable}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>权限名称：
		                     		</label>
			                        <input type="text" class="ipt" name="action.actionName" value="${action.actionName}" />
				                    <span class="errortip">${errors['action.actionName'][0]}</span>
			                   	</div>
			                </li>
			                <c:if test="${!edit}">
				                <li class="inputList-li">
				                	<div class="listItem">
			                       		<label class="lbl-ipt-tit">
			                     			子权限：
			                     		</label>
			                     		<div class="select">
				                     		<input type="checkbox" name="showActionSelected" value="true"/>
											查看权限<br/>
											<input type="checkbox" name="addActionSelected" value="true"/>
											添加权限<br/>
											<input type="checkbox" name="updateActionSelected" value="true"/>
											修改权限<br/>
											<input type="checkbox" name="checkActionSelected" value="true"/>
											审核权限<br/>
											<input type="checkbox" name="deleteActionSelected" value="true"/>
											删除权限
										</div>
				                   	</div>
				                </li>
				             </c:if>
				             <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>权限URL：
		                     		</label>
		                     		<input type="text" class="ipt" name="action.actionUrl" value="${action.actionUrl}" />
									<span class="errortip">${errors['action.actionUrl'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			所属父级：
		                     		</label>
		                     		<div>
									    <input id="keyId" class="formText" type="text" readonly="readonly" value="${parentName}" 
									    	onfocus="showMenu('${path}/page/common/tree.jsp','action'); return false;" onkeydown="checkBackSpace()"/>
									    <input id="valueId" type="hidden" name="action.parentId" value="${action.parentId}"/>
									    <input id="depthId" type="hidden" name="action.depth" value="${action.depth}"/>
									    <div id="treeDiv"><ul id="treeContent" class="ztree" style="width:200px;"></ul></div>
									</div>
			                   	</div>
			                </li>
							<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>排序：
		                     		</label>
			                        <input type="text" class="ipt" name="action.actionSort" value="${action.actionSort}" onkeyup="intFormat(this,1,99);"/>
				                    <span class="errortip">${errors['action.actionSort'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                    <div class="listItem txtarea">
			                    	<label class="lbl-ipt-tit">权限描述：</label>
			                        <textarea name="action.actionDesc" class="formTextarea valid">${action.actionDesc}</textarea>
			                        <span class="errortip">${errors['action.actionDesc'][0]}</span>
			                    </div>
			                </li>
			             </ul>
			        </div>
			    </div>
				<div class="buttonArea">
					<input class="formButton" value="确  定" type="button" onclick="document.editActionForm.submit();">&nbsp;&nbsp;
            		<input class="formButton" onclick="window.location='${path}/admin/action!list.action'" value="返  回" type="button">
				</div>
			</form>
		</div>
	</body>
</html>