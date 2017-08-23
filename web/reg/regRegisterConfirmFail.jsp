<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML >
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<jsp:include page="include/sub_header.jsp"></jsp:include>
<jsp:include page="include/nav.jsp"></jsp:include>
<div class="section">
    <div class="guahao_confirm_bd error_page">
        <div class="mob_ui_box">
            <div class="ui_bd">
            	<h3>系统提示</h3>
            	<div class="wrap">
                	<span class="tips">
                    	抱歉，暂无该时间段挂号资源!
                    </span>
                </div>
            </div>
        </div>
        <div class="wrap_action_btn">
            <a href="#" class="btn back_btn">返回上一页</a>
        </div>
    </div>
</div>
<%@ include file="include/footer.jsp"%>
<script src="assets/script/jquery-1.4.2.min.js"></script>
<script src="assets/script/base.js"></script>
<script src="http://script.elian.cc/main/reg/script/base.js"></script>
</body>
</html>