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
		<title>用户类型</title>
		<link href="${path}/css/manage/page.css" type="text/css" rel="stylesheet" />
		<link rel="stylesheet" href="${path}/css/manage/Menu.css" type="text/css">
        <link rel="stylesheet" href="${path}/css/zTreeStyle/zTreeStyle.css" type="text/css">
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />       
        <script type="text/javascript" src="${path}/js/jquery-1.4.4.min.js"></script>
        <script type="text/javascript" src="${path}/js/jquery.ztree.core-3.5.js"></script>
        <script type="text/javascript" src="${path}/js/jquery.ztree.excheck-3.5.js"></script>
        <script src="${path}/js/ajaxTree.js" type="text/javascript"></script>
        <script type='text/javascript' src='${path}/js/manage/editCommon.js'></script>
	</head>
	<body class="input">
    <div class="main-top-hd clearfix">
        <h3 class="cur">
        <c:if test="${edit}">用户类型编辑</c:if>
        <c:if test="${!edit}">用户类型添加</c:if>
        </h3>
    </div>
<div class=body>
    <form id="userTypeCOrU" name="userTypeCOrU" method="post" action="${path}/admin/userType!save.action">
        <c:if test="${!empty userType}">
           <input type="hidden" value="${userType.id}" name="userType.id"/>
           <input type="hidden" value="${edit}" name="edit"/>
           <input type="hidden" value="${userType.version}" name="userType.version"/>
        </c:if>
        <input type="hidden" name="token" value="${token}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="userType.disable" value="true"<c:if test="${userType.disable}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="userType.disable" value="false"<c:if test="${!userType.disable}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>类型名称：</label>
                    <input type="text" class="ipt" name="userType.typeName" value="${userType.typeName}" />
                    <span class="errortip">
                        <s:fielderror ><s:param>userType.typeName</s:param></s:fielderror>
                    </span>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>所属父类：</label>
                    <div class="select">
                      <input id="userTypeValue" class="ipt" name="typeName" type="text" readonly="true" value="${!empty typeName?typeName:'最高级别'}" 
                          onfocus="showMenu('${path}/page/common/tree.jsp','userTypeTree','userTypeValue','userTypeId'); return false;"
                          onkeydown="BackSpace('最高级别','userTypeValue','userTypeId');"/>
                      <input id="userTypeId" type="hidden" name="userType.parentId" value="${!empty userType.parentId?userType.parentId:0}"/>
                      <div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:250px;"/></div>
                      <span class="errortip">
                       <s:fielderror ><s:param>userType.parentId</s:param></s:fielderror>
                      </span>
                    </div>
                    </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                      <label class="lbl-ipt-tit"><span class="star">*</span>类型排序：</label>
                      <input  type="text" class="ipt" name="userType.typeSort" maxlength="4" 
                        onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
                        value="${userType.typeSort}" />
                        <span class="errortip">
                            <s:fielderror ><s:param>userType.typeSort</s:param></s:fielderror>
                        </span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem txtarea">
                       <label class="lbl-ipt-tit">类型描述：</label>
                       <textarea rows="10" cols="10" name="userType.typeDesc" class="formTextarea">${userType.typeDesc}</textarea>
                       <span class="errortip">
                           <s:fielderror ><s:param>userType.typeDesc</s:param></s:fielderror>
                       </span>
                    </div>
                </li>
            </ul>
           </div>
        </div>
         <div class="buttonArea">
            <input class="formButton" value="确  定" type="button" onclick="submits(this.form);">&nbsp;&nbsp;
            <input class="formButton" onclick="window.location='${path}/admin/userType!list.action'" value="返  回" type="button">
        </div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
        displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>