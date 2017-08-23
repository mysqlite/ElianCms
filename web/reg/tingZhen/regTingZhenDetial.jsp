<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../include/index_header.jsp"></jsp:include>
<link rel="stylesheet" href="http://style.elian.cc/main/reg/style/sub.css" type="text/css" media="screen" />
<jsp:include page="../include/nav.jsp"></jsp:include>
<div class="breadcrumbs">
    <div class="bd">
        <a href="#">首页</a> &gt; <a href="#">广东</a> &gt; 
        <a href="#">佛山</a> &gt; <span class="cur">广东省人民医院</span>
    </div>
</div>
<div class="gutter"></div>
<div class="section">
    <div class="w730 article">
        <div class="article_hd">
            <h1>停诊通知</h1>
            <div class="wrap_tagInfo">
                <div class="tagInfo">
                    <span class="date">
                        2013年03月11日 15：29
                    </span>
                    <span>来源：本站</span>
                </div>
            </div>
        </div>
        <div class="article_bd">
        	<c:forEach var="item" items="${tingZhenList}" varStatus="e">
        		<p style="text-indent: 2em;">
        			${e.index+1}.
	             	<f:formatDate value="${item.startTime}" pattern="yyyy.MM.dd (EEEE"/>
					<f:formatDate value="${item.startTime}" type="time" pattern="HH:mm"/>
					至
					<f:formatDate value="${item.endTime}" type="time"  pattern="HH:mm"/>
					)
	             	${item.doctor.dept.hospital.hospName }
	             	<a href="${path}/reg/regDoctor!detial.action?docId=${item.doctor.id}">${item.doctor.doctName}</a>(${item.doctor.dept.deptName}) 停诊。
        		</p>
        	</c:forEach>
        </div>
    </div>
    <div class="w240">
        <div class="aside">
            <jsp:include page="../include/side/tingZhen.jsp"></jsp:include>
        </div>
        <div class="gutter"></div>
        <div class="aside">
         	<!-- 床位查询 -->
         	<%--<jsp:include page="../include/bedTopSearch.jsp"></jsp:include> --%>
        </div>
    </div>
</div>
<script src="http://script.elian.cc/main/reg/script/base.js"></script>
<script src="http://script.elian.cc/main/reg/script/gd-top.js"></script>
<jsp:include page="../include/footer.jsp"></jsp:include>