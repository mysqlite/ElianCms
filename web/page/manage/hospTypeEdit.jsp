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
	    <c:if test="${edit}">医院类型编辑</c:if>
	    <c:if test="${!edit}">医院类型添加</c:if>
	    </h3>
	</div>
<div class=body>
    <form id="hospTypeCOrU" name="hospTypeCOrU" method="post" action="${path}/admin/hospType!save.action">
        <input type="hidden" name="hospType.id" value="${hospType.id}">
        <input type="hidden" name="hospType.version" value="${hospType.version}">
        <input type="hidden" name="token" value="${token}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="hospType.disable" value="true"<c:if test="${hospType.disable}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="hospType.disable" value="false"<c:if test="${!hospType.disable}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                      <label class="lbl-ipt-tit"><span class="star">*</span>类型类别：</label>
	                      <div class="select">
		                      <select name="hospType.typeClass">
			                      <c:forEach var="type" items="${hospTypeList}" varStatus="e">
			                          <option value="${type.key}" <c:if test="${hospType.typeClass eq type.key}">selected</c:if>>
			                          ${type.value}
			                          </option>
			                      </c:forEach>
		                      </select>
	                      </div>
                      <span class="errortip">${errors['hospType.parentId'][0]}</span>
                   </div>
                </li>
                 <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>类型名称：</label>
                    <input type="text" class="ipt" name="hospType.typeName" value="${hospType.typeName}"/>
                    <span class="errortip">${errors['hospType.typeName'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                      <label class="lbl-ipt-tit">
                          <span class="star">*</span>排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序：
                      </label>
                      <input type="text" class="ipt" name="hospType.typeSort" maxlength="4"
                      onkeyup="this.value=this.value.replace(/\D/g,'')"  
                       onafterpaste="this.value=this.value.replace(/\D/g,'')"
                       value="${!empty hospType.typeSort?hospType.typeSort:1}" />
                      <span class="errortip">${errors['hospType.typeSort'][0]}</span>
                    </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem txtarea">
                        <label class="lbl-ipt-tit">类型描述：</label>
                       <textarea name="hospType.typeDesc" class="formTextarea valid">${hospType.typeDesc}</textarea>
                         <span class="errortip">${errors['hospType.typeDesc'][0]}</span>
                    </div>
                </li>
            </ul>
           </div>
        </div>
         <div class="buttonArea">
            <input class="formButton" value="确  定" type="button" onclick="submits(this.form);">&nbsp;&nbsp;
            <input class="formButton" onclick="window.location='${path}/admin/hospType!list.action'" value="返  回" type="button">
        </div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
		displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>