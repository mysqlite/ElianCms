<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML >
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<c:if test="${null !=siteId}">
	<c:import url="http://comp.elian.cc/${siteId}/include/head.shtml" charEncoding="utf-8"/>
	<c:import url="http://comp.elian.cc/${siteId}/include/nav.shtml" charEncoding="utf-8"/>
</c:if>
		<div class="gutter"></div>
		<div class="section">
			<div class="shopping_cart_breadcrumb">
				<h2 class="ico ico_order_success">
					订单提交成功
				</h2>
				<div class="step step3">
					<span class="txt1">挑选商品加入购物车</span>
					<span class="txt2">填写信息,提交订单</span>
					<span class="txt3">完成订单</span>
				</div>
			</div>
			<div class="order_success_tips">
				<div class="bg">
					<span class="ico">您的订单已经成功提交</span>
				</div>
			</div>
			<div class="order_success_form">
				<div class="i ">
					<h3>
						订单信息
					</h3>
					<div class="wrap_bg">
						<span class="tit">订单编号</span>
						<span class="txt">${order.orderCode}&nbsp;<a href="#">[查看订单详情]</a> </span>
					</div>
					<div class="wrap_bg">
						<span class="tit">订单总金额</span>
						<span class="txt price"> ￥${order.payAmount/100}元 </span>
					</div>
				</div>
				<div class="i ">
					<h3>
						配送信息
					</h3>
					<div class="wrap_bg">
						<span class="tit">普通快递</span>
						<span class="txt"> 普通快递 </span>
					</div>
				</div>
				<div class="i ">
					<h3>
						支付信息
					</h3>
					<div class="wrap_bg">
						<span class="tit">支付方式</span>
						<span class="txt">
							<c:forEach var="item" items="${deliveryTypeList}" varStatus="e">
								<c:if test="${item.key == order.deliveryType}">${item.value}</c:if>
							</c:forEach>
						</span>
					</div>
				</div>
			</div>
			<a class="pay_now_btn" id="tenpayButton" target="_blank">立即支付</a>
		</div>
		<div class="wrap_footer">
			<c:if test="${null !=siteId}">
				<c:import url="http://comp.elian.cc/${siteId}/include/foot.shtml" charEncoding="utf-8"/>
			</c:if>
		</div>
		<script src="${path}/js/mall/jquery-1.4.2.min.js"></script>
		<script src="${path}/js/mall/public_cart.js"></script>
		<script src="${path}/js/mall/base.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			$.ajax({
				url : '${path}/tenpay/tenpay!tenPayJson.action?orderType=order&orderId=${order.id}',
				type : "POST",
				dataType : 'json',
				data : null,
				success : function(data) {
					$("#tenpayButton").attr("href", data.tenpayUrl);
				}
			});
		});
		</script>
	</body>
</html>