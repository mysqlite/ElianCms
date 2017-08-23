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
        <jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
    </head>
	<body class="input">
	<div class="main-top-hd clearfix">
	    <h3 class="cur">
	    <c:if test="${edit}">类型编辑</c:if>
	    <c:if test="${!edit}">类型添加</c:if>
	    </h3>
	</div>
<div class=body>
    <form id="typeCOrU" name="typeCOrU" method="post" action="${path}/admin/type!save.action">
        <input type="hidden" name="type.id" value="${type.id}">
        <input type="hidden" name="type.version" value="${type.version}">
        <input type="hidden" name="token" value="${token}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="type.disable" value="true"<c:if test="${type.disable}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="type.disable" value="false"<c:if test="${!type.disable}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                      <label class="lbl-ipt-tit"><span class="star">*</span>类型类别：</label>
	                      <div class="select">
		                      <select name="type.typeClass">
			                      <c:forEach var="item" items="${typeList}" varStatus="e">
			                          <option value="${item.key}" <c:if test="${type.typeClass eq item.key}">selected</c:if>>
			                          ${item.value}
			                          </option>
			                      </c:forEach>
		                      </select>
	                      </div>
                      <span class="errortip">
                          <s:fielderror ><s:param>type.parentId</s:param></s:fielderror>
                      </span>
                   </div>
                </li>
                 <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>类型名称：</label>
                    <input type="text" class="ipt" name="type.typeName" value="${type.typeName}"/>
                    <span class="errortip">
                       <s:fielderror ><s:param>type.typeName</s:param></s:fielderror>
                    </span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                      <label class="lbl-ipt-tit">
                          <span class="star">*</span>排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序：
                      </label>
                      <input type="text" class="ipt" name="type.typeSort" maxlength="4"
                      onkeyup="this.value=this.value.replace(/\D/g,'')"  
                       onafterpaste="this.value=this.value.replace(/\D/g,'')"
                       value="${!empty type.typeSort?type.typeSort:99}" />
                      <span class="errortip">
                          <s:fielderror><s:param>type.typeSort</s:param></s:fielderror>
                      </span>
                    </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem txtarea">
                        <label class="lbl-ipt-tit">类型描述：</label>
                       <textarea name="type.typeDesc" class="formTextarea valid">${type.typeDesc}</textarea>
                         <span class="errortip">
                             <s:fielderror ><s:param>type.typeDesc</s:param></s:fielderror>
                         </span>
                    </div>
                </li>
            </ul>
           </div>
        </div>
         <div class="buttonArea">
            <input class="formButton" value="确  定" type="button" onclick="submits(this.form);">&nbsp;&nbsp;
            <input class="formButton" onclick="window.location='${path}/admin/type!list.action'" value="返  回" type="button">
        </div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
		displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>