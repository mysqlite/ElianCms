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
	    <h3 class="cur"> 求购详情   </h3>
	</div>
<div class=body>
	<div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">产品名称：</label><span class="txt">${purchase.name}</span>
                    </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit">是否启用：</label>
                     <span class="txt"><c:if test="${purchase.disable}">是 </c:if> 
                     <c:if test="${!purchase.disable}">否</c:if></span>                   
                   </div>
                </li>                
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">关键字：</label> <span class="txt">${purchase.keywords}</span>                
                  </div>
                </li>                
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源名称：</label> <span class="txt">${purchase.sourceName}</span> 
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源路径：</label> <span class="txt">${purchase.sourceUrl}</span>                                        
                  </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"> 求购单价：</label> <span class="txt">${purchase.price}</span> 
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">求购数量：</label>  <span class="txt">${purchase.number}</span>                    
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">规格：</label>
                      <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${purchase.specification}</p> 
                  </div>
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">摘要：</label>
	                     <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${purchase.description}</p> 
	               </div>     
                </li>
                <li class="inputList-li">
                	<div class="listItem">
                    <label class="lbl-ipt-tit">产品详情：</label>  
                     <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${purchase.desc}</p>                                                          
                  	</div>
				</li>	
				
				
                <li class="inputList-li">                 
                  <div class="listItem">
                    <label class="lbl-ipt-tit">标题：</label> <span class="txt">${purchase.title}</span>                      
                  </div>
                  
                   <div class="listItem">
                    <label class="lbl-ipt-tit">联系人：</label> <span class="txt">${purchase.title}</span>   
                   	  <span class="txt">
                           <c:forEach var="item" items="${contacterList}" varStatus="e">
                            <c:if test="${item.id eq purchase.contacter.id}">${item.contactName}|${item.contacter}</c:if>
                           </c:forEach>
	                  </span> 
                  </div>
                  </li>
                  <li class="inputList-li">	      
                  	<div class="listItem txtarea"></div>                 
                   <div class="listItem txtarea">
                       <label class="lbl-ipt-tit">发布时间：</label>  <span class="txt">${purchase.publishTime}</span>                      
                   </div>
                </li>                 
                <li class="inputList-li">
                 <div class="listItem">
                     <label class="lbl-ipt-tit">求购区域：</label>
                       ${!empty areaName?areaName:'全国'}
                 </div>
                 <div class="listItem">
                  <label class="lbl-ipt-tit">有效期至：</label>  <span class="txt">${purchase.expireTime}</span>  
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">创建人：</label>
                      <span class="txt"> ${!empty purchase.createrUser.id?purchase.createrUser.realName:_user.realName}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">创建时间：</label> <span class="txt">${purchase.createTime}</span>  
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">求购须知：</label>
                       <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${purchase.notice}</p> 
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