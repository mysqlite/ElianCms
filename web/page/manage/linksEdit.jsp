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
	    <c:if test="${edit}">友情链接编辑</c:if>
	    <c:if test="${!edit}">友情链接添加</c:if>
	    </h3>
	</div>
<div class=body>
    <jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
    <form id="linksCOrU" name="linksCOrU" method="post" action="${path}/admin/${action}!save.action">
        <input type="hidden" name="links.id" value="${links.id}">
        <input type="hidden" name="links.createTime" value="${links.createTime}">
        <input type="hidden" name="links.creater.id" value="${links.creater.id}"/>  
        <input type="hidden" name="links.version" value="${links.version}">
        <input type="hidden" name="token" value="${token}"/>
        <input type="hidden" name="leaf" value="${leaf}"/>
        <input type="hidden" name="channelId" value="${channelId}"/>
        <input type="hidden" name="action" value="${action}"/>
        <input type="hidden" name="edit" value="${edit}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>链接名称：</label>
                    <input type="text" class="ipt" name="links.title" value="${links.title}"/>
                    <span class="errortip">${errors['links.title'][0]}</span>
                   </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="links.disable" value="true"<c:if test="${links.disable}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="links.disable" value="false"<c:if test="${!links.disable}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
	                    <label class="lbl-ipt-tit"><span class="star">*</span>链接地址：</label>
	                    <input type="text" class="ipt" name="links.linkUrl" value="${links.linkUrl}"/>
	                    <span class="errortip">${errors['links.linkUrl'][0]}</span>
                   </div>
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">邮箱地址：</label>
	                    <input type="text" class="ipt" name="links.email" value="${links.email}"/>
	                    <span class="errortip">${errors['links.email'][0]}</span>
                   </div>
                </li>
                
                <li class="inputList-li">
                   <div class="listItem">
                        <label class="lbl-ipt-tit">创建人：</label>
                        <span class="txt">${links.creater.realName}</span>
                   </div>
                   <div class="listItem">
                        <label class="lbl-ipt-tit">创建时间：</label>
                        <span class="txt"><fmt:formatDate value="${links.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                   </div>
                </li>
                
                <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                        <label class="lbl-ipt-tit">logo图片：</label>
                        <div id="logoImg"></div>
                        <span class="errortip">${errors['links.logoImg'][0]}</span>
                   </div>
                  <script type="text/javascript">
                   loadingImageButton("logoImg",'${links.logoImg}',"links.logoImg",false,1);
                  </script>
                </li>
                <li class="inputList-li">
                  <div class="listItem listItem-full-width">
                       <label class="lbl-ipt-tit">描述：</label>
                       <textarea id="intro" name="links.description" class="formTextarea valid">${links.description}</textarea>
                       <script type="text/javascript">
	                    	var editor1;KindEditor.ready(function(K) {editor1 = K.create('textarea[id="intro"]');});
	                    </script>
                       <span class="errortip">${errors['links.description'][0]}</span>
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