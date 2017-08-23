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
		<title>申请该职位</title>
        <jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
    <div class="main-top-hd clearfix">
        <h3 class="cur">
        <c:if test="${edit}">编辑</c:if>
        <c:if test="${!edit}">申请该职位</c:if>
        </h3>
    </div>
<div class=body>
    <form id="contacterCOrU" name="contacterCOrU" method="post" action="${path}/admin/candidate!save.action">
        <input type="hidden" name="contacter.version" value="${contacter.version}">
        <input type="hidden" name="contacter.id" value="${contacter.id}">
        <input type="hidden" name="edit" value="${edit}">
        <input type="hidden" name="token" value="${token}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
	                <div class="listItem">
	                    <label class="lbl-ipt-tit"><span class="star">*</span>联系名称：</label>
	                     <input type="text" class="ipt" name="contacter.contactName" value="${contacter.contactName}" maxlength="9"/>
	                     <span class="errortip">${errors['contacter.contactName'][0]}</span>
                   </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="contacter.disable" value="true"<c:if test="${contacter.disable}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="contacter.disable" value="false"<c:if test="${!contacter.disable}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>联系人：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.contacter" value="${contacter.contacter}" />
                      <span class="errortip">${errors['contacter.contacter'][0]}</span>
                    </div>
                 </div>
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>联系电话：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.phone" value="${contacter.phone}" />
                      <span class="errortip">${errors['contacter.phone'][0]}</span>
                    </div>
                 </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">QQ号码：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.qq" value="${contacter.qq}" />
                      <span class="errortip">${errors['contacter.qq'][0]}</span>
                    </div>
                 </div>
                 <div class="listItem">
                    <label class="lbl-ipt-tit">MSN号码：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.msn" value="${contacter.msn}" />
                      <span class="errortip">${errors['contacter.msn'][0]}</span>
                    </div>
                 </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">传真号码：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.fax" value="${contacter.fax}" />
                      <span class="errortip">${errors['contacter.fax'][0]}</span>
                    </div>
                 </div>
                 <div class="listItem">
                    <label class="lbl-ipt-tit">邮编：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.postcode" value="${contacter.postcode}" />
                      <span class="errortip">${errors['contacter.postcode'][0]}</span>
                    </div>
                 </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">部门：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.department" value="${contacter.department}" />
                      <span class="errortip">${errors['contacter.department'][0]}</span>
                    </div>
                 </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem txtarea">
                        <label class="lbl-ipt-tit">地址：</label>
                        <textarea name="contacter.address" class="formTextarea valid">${contacter.address}</textarea>
                             <span class="errortip">${errors['contacter.address'][0]}</span>
                    </div>
                </li>
            </ul>
           </div>
        </div>
         <div class="buttonArea">
            <input class="formButton" value="确  定" type="button" onclick="submits(this.form);">&nbsp;&nbsp;
            <input class="formButton" onclick="window.location='${path}/admin/contacter!list.action'" value="返  回" type="button">
        </div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
        displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>