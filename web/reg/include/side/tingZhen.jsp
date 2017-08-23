<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="notice">
    <div class="mod_hd01">
        <h3 class="ui_hd"><b class="ui_bg"></b>本周停诊通知</h3>
        <a href="${path}/reg/tingZhen!show.action" class="more">&gt;&gt;更多</a>
    </div>
    <div class="mod_bd01"> 
    <!--无缝滚动开始-->
    <ul class="marquee" id="marquee">
        <li>
        	<c:forEach var="item" items="${doctorWorkList}">
             <div class="li_con">
             	<f:formatDate value="${item.startTime}" pattern="yyyy.MM.dd (EEEE"/>
				<f:formatDate value="${item.startTime}" type="time" pattern="HH:mm"/>
				至
				<f:formatDate value="${item.endTime}" type="time"  pattern="HH:mm"/>
				)
				<%--
             	${item.doctor.dept.hospital.hospName }
             	<a href="${path}/reg/regDoctor!detial.action?docId=${item.doctor.id}">${item.doctor.doctName}</a>(${item.doctor.dept.deptName}) 停诊。
             	 --%>
             </div>
        	</c:forEach>
        </li>
    </ul>
   <!--无缝滚动结束-->
    </div>
</div>