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
        <jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
    </head>
	<body class="input">
	<div class="main-top-hd clearfix">
	    <h3 class="cur">
	    <c:if test="${edit}">菜单编辑</c:if>
	    <c:if test="${!edit}">菜单添加</c:if>
	    </h3>
	</div>
<div class=body>
    <form id="menuCOrU" name="menuCOrU" method="post" action="${path}/admin/menu!save.action">
        <input type="hidden" name="menu.id" value="${menu.id}">
        <input type="hidden" name="menu.createTime" value="${menu.createTime}">
        <input type="hidden" name="menu.depth" value="${menu.depth}">
        <input type="hidden" name="menu.version" value="${menu.version}">
        <input type="hidden" name="token" value="${token}"/>
        <input type="hidden" name="edit" value="${edit}"/>
        <input type="hidden" name="actionId" value="${actionId}">
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="menu.disable" value="true"<c:if test="${menu.disable}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="menu.disable" value="false"<c:if test="${!menu.disable}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <c:if test="${!edit}">
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>权限映射：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="checkbox" name="addAction" checked="checked" value="true"/>添加到权限
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="checkBox" name="actionChild" checked="checked" value="true"/>添加增删查改审核权限
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
	                <div class="listItem">
		                <label class="lbl-ipt-tit"><span class="star">*</span>组织类型：</label>
		                <div class="select">
		                    <select class="listbar-sel" name="compType">
		                        <c:forEach var="item" items="${compTypeList}">
		                            <option value="${item.key}">${item.value}</option>
		                        </c:forEach>
		                    </select>
		                </div>
	                </div>
                </li>
                </c:if>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>菜单名称：</label>
                    <input type="text" class="ipt" name="menu.menuName" value="${menu.menuName}"/>
                    <span class="errortip">${errors['menu.menuName'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem listItem-full-width">
                    <label class="lbl-ipt-tit">链接URL：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="menu.menuUrl" value="${menu.menuUrl}" />
                      <span class="errortip">${errors['menu.menuUrl'][0]}</span>
                    </div>
                  </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                      <label class="lbl-ipt-tit"><span class="star">*</span>菜单级别：</label>
                      <input id="parentName" class="ipt" name="parentName" type="text" readonly="true"
                      value="${!empty parentName?parentName:'最高级别'}"
                       onfocus="showMenu('${path}/page/common/tree.jsp','menuTree','parentName','parentId'); return false;"
                       onkeydown="BackSpace('最高级别','parentName','parentId');"/>
                      <input id="parentId" type="hidden" name="menu.parentId" value="${!empty menu.parentId?menu.parentId:0}"/>
                      <div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:235px;"/></div>
                      <span class="errortip">${errors['menu.parentId'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                      <label class="lbl-ipt-tit">
                          <span class="star">*</span>排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序：
                      </label>
                      <input type="text" class="ipt" name="menu.menuSort" maxlength="4"
                      onkeyup="intFormat(this,1,9999)" value="${!empty menu.menuSort?menu.menuSort:1}" />
                      <span class="errortip">${errors['menu.menuSort'][0]}</span>
                    </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem txtarea">
                        <label class="lbl-ipt-tit">菜单描述：</label>
                       <textarea name="menu.menuDesc" class="formTextarea valid">${menu.menuDesc}</textarea>
                         <span class="errortip">${errors['menu.menuDesc'][0]}</span>
                    </div>
                </li>
            </ul>
           </div>
        </div>
         <div class="buttonArea">
            <input class="formButton" value="确  定" type="button" onclick="submits(this.form);">&nbsp;&nbsp;
            <input class="formButton" onclick="window.location='${path}/admin/menu!list.action'" value="返  回" type="button">
        </div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
		displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>