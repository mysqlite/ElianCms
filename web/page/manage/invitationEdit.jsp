<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	    <c:if test="${edit}">招标信息编辑</c:if>
	    <c:if test="${!edit}">招标信息添加</c:if>
	    </h3>
	</div>
<div class=body>
    <form id="inviCOrU" name="inviCOrU" method="post" action="${path}/admin/${action}!save.action">
        <input type="hidden" name="invitation.id" value="${invitation.id}">
        <input type="hidden" name="invitation.createTime" value="${invitation.createTime}">
        <input type="hidden" name="invitation.version" value="${invitation.version}">
        <input type="hidden" name="token" value="${token}"/>
        <input type="hidden" name="leaf" value="${leaf}"/>
        <input type="hidden" name="channelId" value="${channelId}"/>
        <input type="hidden" name="action" value="${action}"/>
        <input type="hidden" name="edit" value="${edit}"/>  
        <input type="hidden" name="invitation.creater.id" value="${invitation.creater.id}"/>  
      
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>企业名称：</label>
                    <input type="text" class="ipt" name="invitation.publisher" value="${invitation.publisher}"/>
                    <span class="errortip">${errors['invitation.publisher'][0]}</span>
                   </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="invitation.disable" value="true"<c:if test="${invitation.disable}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="invitation.disable" value="false"<c:if test="${!invitation.disable}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">关键字：</label>
                    <input type="text" class="ipt" name="invitation.keywords" value="${invitation.keywords}" />
                    <span class="errortip">${errors['invitation.keywords'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源名称：</label>
                      <input type="text" class="ipt" name="invitation.sourceName" value="${invitation.sourceName}" />
                      <span class="errortip">${errors['invitation.sourceName'][0]}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源路径：</label>
                      <input type="text" class="ipt" name="invitation.sourceUrl" value="${invitation.sourceUrl}" />
                      <span class="errortip">${errors['invitation.sourceUrl'][0]}</span>
                  </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span> 项目名称 ：</label>
                      <input type="text" class="ipt" name="invitation.projectName" value="${invitation.projectName}" />
                      <span class="errortip">${errors['invitation.projectName'][0]}</span>
                  </div>  
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>标题 ：</label>
                      <input type="text" class="ipt" name="invitation.title" value="${invitation.title}" />
                      <span class="errortip">${errors['invitation.title'][0]}</span>
                  </div>               
                </li>                
           	 <li class="inputList-li">   
           	    <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>采购方式 ：</label>   
                      <div class="select">
                          <select name="invitation.invitType">
                            <c:forEach var="item" items="${typeList}" varStatus="e">
                             <option  value="${item.typeName}" <c:if test="${item.typeName eq invitation.invitType}"> selected="selected" </c:if>>${item.typeName}</option>
                            </c:forEach>
                          </select>
                     </div>
                     <span class="errortip">${errors['invitation.contacter.id'][0]}</span>
                  </div>             
                 <div class="listItem txtarea">
                     <label class="lbl-ipt-tit"><span class="star">*</span>采购 区域 ：</label>
                     <input id="keyId" class="ipt" name="areaName" type="text" readonly="true" value="${!empty invitation.areaId?'':'全国'}" 
                      onfocus="showMenuArea('treeContent','keyId','valueId','${path}');"
                      onkeydown="BackSpace('全国','keyId','valueId');"/>
                     <input id="valueId" type="hidden" name="invitation.areaId" value="${!empty invitation.areaId?invitation.areaId:0}"/>
                     <div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:300px;height: 300px;"></ul></div>
                     <span class="errortip">${errors['invitation.areaId'][0]}</span>
                  </div>                            
                </li>
                <li class="inputList-li">
                	<div class="listItem txtarea">
                     <label class="lbl-ipt-tit">摘要：</label>
                     <textarea name="invitation.description"  class="formTextarea valid">${invitation.description}</textarea>
                       <span class="errortip">${errors['invitation.description'][0]}</span>
                  </div>    
                </li>
                <li class="inputList-li">                        
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>联系人：</label>
                    <input type="text" class="ipt" name="invitation.contacter" value="${invitation.contacter}" />
                    <span class="errortip">${errors['invitation.contacter'][0]}</span>
                  </div>   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>联系人电话：</label>
                    <input type="text" class="ipt" name="invitation.contacterPhone" value="${invitation.contacterPhone}" />
                    <span class="errortip">${errors['invitation.contacterPhone'][0]}</span>
                  </div>   
                 </li>   
                <li class="inputList-li">
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>创建时间：</label>
                      <input type="text" class="ipt" name="invitation.createTime" disabled="disabled" value="<fmt:formatDate value="${invitation.createTime}" pattern="yyyy-MM-dd HH:mm"/>" />
                      <span class="errortip">${errors['invitation.createTime'][0]}</span>
                  </div>                     
                 <div class="listItem">
                  <label class="lbl-ipt-tit"><span class="star">*</span>有效期至：</label>
                  <input name="invitation.expireTime" value='<fmt:formatDate value="${invitation.expireTime}" pattern="yyyy-MM-dd"/>' class="ipt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
                  <span class="errortip">${errors['invitation.expireTime'][0]}</span>
                  </div>
                </li>               
                 <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">说明：</label>
                   </div>
                 </li>
                <li class="inputList-li">
                  <div class="listItem txtarea">
                     <textarea id="invitDesc" name="invitation.invitDesc" class="formTextediter">${invitation.invitDesc}</textarea>
                     <script>
                      var editor1;KindEditor.ready(function(K) {editor1 = K.create('textarea[id="invitDesc"]');});    
                     </script>
                     <span class="errortip">${errors['invitation.invitDesc'][0]}</span>
                     <div style="display:none">
			          		<textarea name="editorPrevImg">${invitation.invitDesc}</textarea>
			         </div>
                   </div>
                </li>
            </ul>
           </div>
        </div>
        <jsp:include page="contentButton.jsp"></jsp:include>
        </form>
      </div>
   </body>
<script type="text/javascript">  
		displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>