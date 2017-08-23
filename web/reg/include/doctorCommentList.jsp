<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%--医生评论列表--%>
<div class="reply_comment" id="reply_comment">
 <form action="${path}/reg/regDoctor!detial.action#reply_comment" method="post" id="replyComment" name="replyComment" >
  <input type="hidden" name="paginationComment.conditionContent" value="${paginationComment.conditionContent}"/>
  <input type="hidden" name="docId" value="${docId}"/>
  <div class="ui_hd">
      <h3 class="tit">
      <c:if test="${paginationComment.rowCount>0}">
      所有评价：<span>(共<em>${paginationComment.rowCount}</em>条)</span>
      </c:if>
      <c:if test="${paginationComment.rowCount==0}">
      暂无评论
      </c:if>
      </h3>
  </div>
  <div class="bd">
      <ul class="pt03">
       <c:forEach var="doctorCommon" items="${paginationComment.list}" varStatus="e">
         <li><a class="pic" href="#"><img src="<c:if test="${empty doctorCommon.user.userImg or doctorCommon.user.userImg==''}">${_img_ftp.ftpUrl}/design/main/reg/img/offline_user2.jpg</c:if> 
         <c:if test="${!empty doctorCommon.user.userImg or !doctorCommon.user.userImg==''}">
         <c:if test="${!fn:startsWith(doctorCommon.user.userImg,'http://')}">${_img_ftp.ftpUrl}${doctorCommon.user.userImg}</c:if> 
         <c:if test="${fn:startsWith(doctorCommon.user.userImg,'http://')}">${doctorCommon.user.userImg}</c:if> 
         </c:if>
         " alt=""/></a>
              <h4 class="tit">就诊疾病：${doctorCommon.illness}<span class="date"><fmt:formatDate value="${doctorCommon.levaWordTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span></h4>
              <p class="txt">${doctorCommon.leaveWords}</p>
              <div class="usr_rate_details">
                  <span class="txt">对${doctor.doctName}医生的评价</span>
                  <span class="txt">诊疗效果:
                  <c:forEach var="score_" items="${scoreList}" varStatus="f">
                  <c:if test="${score_.key==doctorCommon.cureScore}">${score_.value}</c:if>
                  </c:forEach>
                  </span>
                  <span class="txt">服务态度:
                   <c:forEach var="score_" items="${scoreList}" varStatus="f">
                      <c:if test="${score_.key==doctorCommon.serveScore}">${score_.value}</c:if>
                  </c:forEach>
                  </span>
                  <span class="txt">医德:
                   <c:forEach var="score_" items="${scoreList}" varStatus="f">
                      <c:if test="${score_.key==doctorCommon.ethiceScore}">${score_.value}</c:if>
                  </c:forEach>
                  </span>
                  
                  <span class="txt">综合评分:
                   <c:forEach var="score_" items="${scoreList}" varStatus="f">
                      <c:if test="${score_.key==doctorCommon.aveScore}">${score_.value}</c:if>
                  </c:forEach>
                  </span>
                  
                  <div class="wrap_up_down" id="wrap_up_down${doctorCommon.id}">
                      <c:if test="${!empty _user}">
                          <input id="good_value_${doctorCommon.id}" value="${!empty doctorCommon.good?doctorCommon.good:0}" type="hidden"/>
                          <input id="poor_value_${doctorCommon.id}" value="${!empty doctorCommon.poor?doctorCommon.poor:0}" type="hidden"/>
                          <a href="javascript:void(0);" class="ui_btn up" id="good_${doctorCommon.id}"
                           onclick="toGood(${doctorCommon.id})">${!empty doctorCommon.good?doctorCommon.good:0}</a>
                          <a href="javascript:void(0);" class="ui_btn down" id="poor_${doctorCommon.id}"
                          onclick="toPoor(${doctorCommon.id})">${!empty doctorCommon.poor?doctorCommon.poor:0}</a>
                      </c:if>
                  </div>
              </div>
          </li>
       </c:forEach>
      </ul>
      <c:if test="${paginationComment.rowCount>0}">
         <jsp:include page="doctorCommentPager.jsp"></jsp:include>
      </c:if>
      <script type="text/javascript">
      function toPage(page){
          if (page != null && page != ''){
              document.getElementById("currentPage").value= page;
              document.getElementById("replyComment").submit();
          }
      }
      </script>
  </div>
  </form>
  <script type="text/javascript">
  function toGood(id){
	  getJson('${path}/reg/regDoctor!saveGood.action',function(data){
		  if(data.ok==0){
              G("good_"+id).innerHTML=(parseInt(GV("good_value_"+id))+1);
              G("good_value_"+id).value=(parseInt(GV("good_value_"+id))+1);
          }else if(data.ok==1){
              alert("您已经对这条评论顶/踩过了");
          }if(data.ok==2){
              
          }
	   },{"id":id});
   }
   function toPoor(id){
	   getJson('${path}/reg/regDoctor!savePoor.action',function(data){
           if(data.ok==0){
              G("poor_"+id).innerHTML=(parseInt(GV("poor_value_"+id))+1);
              G("poor_value_"+id).value=(parseInt(GV("poor_value_"+id))+1);
          }else if(data.ok==1){
              alert("您已经对这条评论顶/踩过了");
          }else if(data.ok==2){
          }
       },{"id":id});
   }
  </script>
</div>