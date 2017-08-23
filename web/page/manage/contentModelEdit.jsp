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
	        <c:if test="${!edit}">内容模型添加</c:if>
	        <c:if test="${edit}">内容模型修改</c:if>
        </h3>
    </div>
<div class=body>
    <form id="editContentModelForm" name="editContentModelForm" method="post" action="${path}/admin/contentModel!save.action">
    	<input type="hidden" name="id" value="${id}">
        <input type="hidden" name="token" value="${token}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>模型名称：</label>
                     <input type="text" class="ipt" name="modelName" value="${modelName}" disabled="disabled"/>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem listItem-full-width">
                    <label class="lbl-ipt-tit"><span class="star">*</span>内容模型：</label>
                    <label class="lbl-ipt-tit radioWrap">
                    	<c:forEach var="c" items="${contentModelList}">
                    		<label class="lbl-ipt-tit radioWrap">
                        		<input type="radio" name="contentModelId" value="${c.value}" <c:if test="${c.value == contentModelId}"> checked="checked" </c:if>/>${c.key}
                        	</label>
                        </c:forEach>
                    </label>
                    <span class="errortip">${errors['contentModelId'][0]}</span>
                  </div>
                </li>
            </ul>
           </div>
        </div>
        <div class="buttonArea">
			<input class="formButton" value="确  定" type="button" onclick="document.editContentModelForm.submit();">&nbsp;&nbsp;
          	<input class="formButton" onclick="window.location='${path}/admin/model!list.action'" value="返  回" type="button">
		</div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
        displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>