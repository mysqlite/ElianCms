<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="mod_hd03">
    <h3 class="tit">医院放号排行</h3>
</div>
<div class="mod_bd03">
    <ul class="hos-resource-list">
     <c:forEach var="noScore" items="${noScoreList}" varStatus="nse" >
         <c:if test="${nse.index+1<9}">
         <li class="num-ico num-ico-0${nse.index+1}">
             <div class="txt"><a href="${path}/reg/regDoctor!searchByDept.action?hospId=${noScore.hospId}" title="${noScore.storeName}">${noScore.hospName}</a></div>
             <span class="num">号数：<b>${noScore.noScoreCount}</b></span>
         </li>
         </c:if>
     </c:forEach>
      </ul>
</div>
