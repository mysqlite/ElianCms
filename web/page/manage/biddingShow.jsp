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
	    <h3 class="cur">查看中标信息 </h3>
	</div>
<div class=body>        
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">项目名称：</label>
                    <span class="txt">${bidding.projectName}</span>                    
                   </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit">是否启用：</label>
                     <span class="txt"><c:if test="${bidding.disable}">是 </c:if> 
                     <c:if test="${!bidding.disable}">否</c:if></span>  
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">关键字：</label>
                    <span class="txt">${bidding.keywords}</span> 
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源名称：</label>
                    <span class="txt">${bidding.sourceName}</span> 
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源路径：</label>
                    <span class="txt">${bidding.sourceUrl}</span> 
                  </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"> 中标单位：</label>
                    <span class="txt">${bidding.bidCompany}</span> 
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">中标金额：</label>
                    <span class="txt">${bidding.bidSum}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">标题：</label>    
                    <span class="txt">${bidding.title}</span>
                  </div> 
                   <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">区域：</label>
                     <span class="txt"> ${!empty areaName?areaName:'全国'}</span>
                  </div>             
                </li>                
                <li class="inputList-li">
                	<div class="listItem txtarea">
                       <label class="lbl-ipt-tit">摘要：</label>
                       <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${bidding.description}</p>                     
                    </div>
                </li>                
                <li class="inputList-li">                  
                  <div class="listItem">
                    <label class="lbl-ipt-tit">创建时间：</label>
                    <span class="txt">${bidding.createTime}</span>  
                  </div>
                  <div class="listItem">
                  	<label class="lbl-ipt-tit">发布时间：</label>
                    <span class="txt"><fmt:formatDate value="${bidding.publishTime}" pattern="yyyy-MM-dd HH:mm"/></span> 
                  </div> 
                </li>
                <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">中标内容：</label>
                     <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${bidding.bidDesc}</p> 
                   </div>
                 </li>               
            </ul>
           </div>
        </div>
        <div class="buttonArea">
          	<input class="formButton" onclick="window.location='${path}/admin/content!list.action?leaf=${leaf}&channelId=${channelId}&action=${action}&status=${status}'" value="返  回" type="button">
		</div>
      </div>
   </body>
<script type="text/javascript">  
		displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>