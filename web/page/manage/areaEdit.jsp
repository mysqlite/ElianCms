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
		<title>菜单编辑</title>
        <jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
    <div class="main-top-hd clearfix">
        <h3 class="cur">
        <c:if test="${edit}">区域编辑</c:if>
        <c:if test="${!edit}">区域添加</c:if>
        </h3>
    </div>
<div class=body>
    <form id="areaCOrU" name="areaCOrU" method="post" action="${path}/admin/area!save.action">
        <input type="hidden" name="area.version" value="${area.version}">
        <input type="hidden" name="edit" value="${edit}">
        <input type="hidden" name="token" value="${token}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="area.disable" value="true"<c:if test="${area.disable}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="area.disable" value="false"<c:if test="${!area.disable}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>区划编码：</label>
                     <input type="text" class="ipt" name="area.areaCode" value="${area.areaCode}" maxlength="9"/>
                     <span class="errortip">${errors['area.areaCode'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>区划名称：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="area.areaName" value="${area.areaName}" />
                      <span class="errortip">${errors['area.areaName'][0]}</span>
                    </div>
                 </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                      <label class="lbl-ipt-tit"><span class="star">*</span>区划上级：</label>
                      <input id="keyId" class="ipt" name="areaName" type="text" readonly="readonly" value="${!empty areaName?areaName:'中国'}" 
                       onfocus="showMenuArea('treeContent','keyId','valueId','${path}');"
                       />
                      <input id="valueId" type="hidden" name="area.parentCode" value="${!empty area.parentCode?area.parentCode:0}"/>
                      <div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:260px;height: 300px;"></ul></div>
                      <span class="errortip">${errors['area.parentCode'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                      <label class="lbl-ipt-tit">
                          <span class="star">*</span>排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序：
                      </label>
                      <input type="text" class="formText" name="area.areaSort" maxlength="4" 
                       onkeyup="intFormat(this,1,9999)" value="${!empty area.areaSort?area.areaSort:1}" />
                      <span class="errortip">${errors['area.areaSort'][0]}</span>
                    </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem txtarea">
                        <label class="lbl-ipt-tit">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</label>
                        <textarea name="area.note" class="formTextarea valid">${area.note}</textarea>
                        <span class="errortip">${errors['area.note'][0]}</span>
                    </div>
                </li>
            </ul>
           </div>
        </div>
         <div class="buttonArea">
            <input class="formButton" value="确  定" type="button" onclick="submits(this.form);">&nbsp;&nbsp;
            <input class="formButton" onclick="window.location='${path}/admin/area!list.action'" value="返  回" type="button">
        </div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
        displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>