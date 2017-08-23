﻿<%@ page language="java" pageEncoding="UTF-8"%>
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
	    <h3 class="cur">人才招聘查看  </h3>
	</div>
<div class=body>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">招聘标题：</label><span class="txt">${job.title}</span>
                   </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit">是否启用：</label>
                    <span class="txt"><c:if test="${job.disable}">是 </c:if> 
                     <c:if test="${!job.disable}">否</c:if></span>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">关键字：</label>
                    <span class="txt">${job.keywords}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源名称：</label>
                      <span class="txt">${job.sourceName}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源路径：</label>
                     <span class="txt">${job.sourceUrl}</span>
                  </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"> 用人部门：</label>
                      <span class="txt">${job.servantDepa}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">职位名称：</label>
                      <span class="txt">${job.jobName}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">工作性质：</label>
                    <span class="txt">
                      <c:forEach var="item" items="${jobNatureList}" varStatus="e">
	                         <c:if test="${job.jobNature eq item.key}">${item.value}</c:if>
                      </c:forEach>
                    </span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">招聘人数：</label>
                     <span class="txt">${job.hireNum}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">提供月薪：</label>
                     <span class="txt">${job.salary}</span>
                  </div>
                  <div class="listItem">
                     <label class="lbl-ipt-tit">工作地点：</label>
                     <span class="txt">${!empty areaName?areaName:'全国'}</span>
                  </div>
                </li>
                <!-- -->
                 <li class="inputList-li">
                    <div class="listItem">
                        <label class="lbl-ipt-tit">摘要：</label>
                       <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${job.description}</p>
                    </div>
                    <div class="listItem">
                        <label class="lbl-ipt-tit">专业要求：</label>
                       <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${job.majorRequ}</p>
                    </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">住宿情况：</label>
                     <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${job.housing}</p> 
                  </div>
                  <div class="listItem">
                     <label class="lbl-ipt-tit">语言要求：</label>
                     <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${job.language}</p> 
                  </div>
                </li>
                <!--  -->
                <li class="inputList-li">
                  <div class="listItem">
                     <label class="lbl-ipt-tit">学历要求：</label>
                         <span class="txt">
                          <c:forEach var="item" items="${educationList}" varStatus="e">
                             <c:if test="${item.key eq job.education}">${item.value}</c:if> >
                          </c:forEach>
                          </span>
                  </div>
                  <div class="listItem">
                     <label class="lbl-ipt-tit">年龄要求：</label>
                     <span class="txt">${!empty job.ageRange?job.ageRange:'不限'}</span>
                  </div>
                </li>
                <li class="inputList-li">
                 <div class="listItem">
                     <label class="lbl-ipt-tit">联系人：</label>
	                     <span class="txt">
	                            <c:forEach var="item" items="${contacterList}" varStatus="e">
	                             <c:if test="${item.id eq job.contact.id}">${item.contactName}|${item.contacter}</c:if>
	                            </c:forEach>
	                     </span>
                 </div>
                 <div class="listItem">
                  <label class="lbl-ipt-tit">有效期至：</label>
                  <span class="txt"><fmt:formatDate value="${job.expireTime}" pattern="yyyy-MM-dd"/></span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">创建人：</label>
                     <span class="txt"> ${!empty job.createrId.id?job.createrId.realName:_user.realName}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">创建时间：</label>
                      <span class="txt"><fmt:formatDate value="${job.createTime}" pattern="yyyy-MM-dd HH:mm"/></span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                     <label class="lbl-ipt-tit">岗位要求：</label>
                     <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${job.jobRequ}</p> 
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