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
	    <c:if test="${edit}">编辑中标信息</c:if>
	    <c:if test="${!edit}">添加中标信息</c:if>
	    </h3>
	</div>
<div class=body>
    <form id="jobCOrU" name="jobCOrU" method="post" action="${path}/admin/${action}!save.action">
        <input type="hidden" name="bidding.id" value="${bidding.id}">
        <input type="hidden" name="bidding.createTime" value="${bidding.createTime}">
        <input type="hidden" name="bidding.version" value="${bidding.version}">
        <input type="hidden" name="token" value="${token}"/>
        <input type="hidden" name="leaf" value="${leaf}"/>
        <input type="hidden" name="channelId" value="${channelId}"/>
        <input type="hidden" name="action" value="${action}"/>
        <input type="hidden" name="edit" value="${edit}"/>
        <input type="hidden" name="bidding.creater.id" value="${bidding.creater.id}"/>  
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>项目名称：</label>
                    <input type="text" class="ipt" name="bidding.projectName" value="${bidding.projectName}"/>
                    <span class="errortip">${errors['bidding.projectName'][0]}</span>
                   </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="bidding.disable" value="true"<c:if test="${bidding.disable}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="bidding.disable" value="false"<c:if test="${!bidding.disable}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">关键字：</label>
                    <input type="text" class="ipt" name="bidding.keywords" value="${bidding.keywords}" />
                    <span class="errortip">${errors['bidding.keywords'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源名称：</label>
                      <input type="text" class="ipt" name="bidding.sourceName" value="${bidding.sourceName}" />
                      <span class="errortip">${errors['bidding.sourceName'][0]}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源路径：</label>
                      <input type="text" class="ipt" name="bidding.sourceUrl" value="${bidding.sourceUrl}" />
                      <span class="errortip">${errors['bidding.sourceUrl'][0]}</span>
                  </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"> 中标单位：</label>
                      <input type="text" class="ipt" name="bidding.bidCompany" value="${bidding.bidCompany}" />
                      <span class="errortip">${errors['bidding.bidCompany'][0]}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">中标金额：</label>
                      <input type="text" class="ipt" name="bidding.bidSum" value="${bidding.bidSum}" />
                      <span class="errortip">${errors['bidding.bidSum'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>标题：</label>                    
                     <input type="text" class="ipt" name="bidding.title" value="${bidding.title}" />
                     <span class="errortip">${errors['bidding.title'][0]}</span>
                  </div> 
                   <div class="listItem txtarea">
                     <label class="lbl-ipt-tit"><span class="star">*</span>区域：</label>
                     <input id="keyId" class="ipt" name="areaName" type="text" readonly="readonly" value="${!empty areaName?areaName:'全国'}" 
                      onfocus="showMenuArea('treeContent','keyId','valueId','${path}');"
                      onkeydown="BackSpace('全国','keyId','valueId');"/>
                     <input id="valueId" type="hidden" name="bidding.areaId" value="${!empty bidding.areaId?bidding.areaId:0}"/>
                     <div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:300px;height: 300px;"></ul></div>
                     <span class="errortip" >${errors['bidding.areaId'][0]}</span>
                  </div>             
                </li>                
                <li class="inputList-li">
                	<div class="listItem txtarea">
                       <label class="lbl-ipt-tit">摘要：</label>
                       <textarea name="bidding.description"  class="formTextarea valid">${bidding.description}</textarea>
                         <span class="errortip">${errors['bidding.biddingDesc'][0]}</span>
                    </div>
                </li>
                <li class="inputList-li">                  
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>创建时间：</label>
                      <input type="text" class="ipt" name="bidding.createTime" disabled="disabled" value="<fmt:formatDate value="${bidding.createTime}" pattern="yyyy-MM-dd HH:mm"/>" />
                      <span class="errortip">${errors['bidding.createTime'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">中标内容：</label>
                   </div>
                 </li>
                <li class="inputList-li">
                  <div class="listItem txtarea">
                     <textarea id="bidDesc" name="bidding.bidDesc" class="formTextediter">${bidding.bidDesc}</textarea>
                     <script>
                      var editor1;KindEditor.ready(function(K) {editor1 = K.create('textarea[id="bidDesc"]');});    
                     </script>
                     <span class="errortip">${errors['bidding.bidDesc'][0]}</span>
                     <div style="display:none">
			          		<textarea name="editorPrevImg">${bidding.bidDesc}</textarea>
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