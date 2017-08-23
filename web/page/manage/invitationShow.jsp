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
	    <h3 class="cur">查看招标信息</h3>
	</div>
<div class=body>       
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>            
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">企业名称：</label><span class="txt">${invitation.publisher}</span>                   
                   </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit">是否启用：</label>
                     <c:if test="${invitation.disable}">
	                     <span class="txt">是 </span>                     
                     </c:if>
                     <c:if test="${!invitation.disable}">
	                     <span class="txt">否</span>                     
                     </c:if>   
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">关键字：</label><span class="txt">${invitation.keywords}</span>      
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源名称：</label><span class="txt">${invitation.sourceName}</span>   
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源路径：</label><span class="txt">${invitation.sourceUrl}</span>                      
                  </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"> 项目名称 ：</label><span class="txt">${invitation.projectName}</span>       
                  </div>  
                  <div class="listItem">
                    <label class="lbl-ipt-tit">标题 ：</label><span class="txt">${invitation.title}</span> 
                  </div>               
                </li>                
           	 <li class="inputList-li">   
           	    <div class="listItem">
                    <label class="lbl-ipt-tit">采购方式 ：</label><span class="txt">${invitation.invitType}</span>
                </div>             
                 <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">采购 区域 ：</label><span class="txt">${!empty areaName?areaName:'全国'}</span>
                  </div>                            
                </li>
                <li class="inputList-li">
                	<div class="listItem txtarea">
                     <label class="lbl-ipt-tit">摘要：</label>
                     <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${invitation.description}</p>                     
                  </div>    
                </li>
                <li class="inputList-li">                         
                 <div class="listItem">
                    <label class="lbl-ipt-tit">联系人：</label>
                    <span class="txt">
                          ${invitation.contacter}                       
	                 </span>                   	 
                  </div>   
                 <div class="listItem">
                    <label class="lbl-ipt-tit">联系人电话：</label>
                    <span class="txt">
                          ${invitation.contacterPhone}                       
	                 </span>                   	 
                  </div>   
                 </li>   
                <li class="inputList-li">
                 <div class="listItem">
                    <label class="lbl-ipt-tit">创建时间：</label>
                    <span class="txt"><fmt:formatDate value="${invitation.createTime}" pattern="yyyy-MM-dd HH:mm"/></span>                    
                  </div>  
                  <div class="listItem">
                  	<label class="lbl-ipt-tit">发布时间：</label>
                    <span class="txt"><fmt:formatDate value="${invitation.publishTime}" pattern="yyyy-MM-dd HH:mm"/></span> 
                  </div> 
                </li>  
                <li class="inputList-li">
                   <div class="listItem">
                  <label class="lbl-ipt-tit">有效期至：</label>
                   <span class="txt"><fmt:formatDate value="${invitation.expireTime}" pattern="yyyy-MM-dd"/></span> 
                  </div>
                </li>             
                 <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">说明：</label>
                     <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${invitation.invitDesc}</p> 
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