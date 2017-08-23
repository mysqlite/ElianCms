<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	response.setHeader("P3P","CP=CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR");
%>
<%--<c:if test="${null !=siteId}">
	<c:import url="http://tcomp.elian.cc/${siteId}/include/head.shtml" charEncoding="utf-8"/>
	<c:import url="http://tcomp.elian.cc/${siteId}/include/nav.shtml" charEncoding="utf-8"/>
</c:if>
--%>
<c:import url="/page/front/searchMedicineHeader.jsp" charEncoding="utf-8"/>
<link media="screen" type="text/css" href="http://style.elian.cc/comp/v2/blue/shopping_cart.css" rel="stylesheet">
<div class="gutter"></div>
<div class="section">
	<div class="shopping_cart_breadcrumb">
		<h2 class="ico ico_cart">我的购物车</h2>
		<div class="step step1">
			<span class="txt1">挑选商品加入购物车</span>
			<span class="txt2">填写信息,提交订单</span>
			<span class="txt3">收货确认,完成订单</span>
		</div>
	</div>
	<div class="shopping_cart_bd td_cart_confrim td_cart_confrim_with_del">
		<div class="ui_hd">
			<span class="prd_pic_w">商品图片</span>
			<span class="prd_name_w">商品名称</span>
			<span class="prd_normal_85">商品规格</span>
			<span class="prd_normal_100">销售价格</span>
			<span class="prd_normal_100">数量</span>
			<span class="prd_normal_100">小计</span>
			<span class="prd_normal_70 last">操作</span>
		</div>
		<div class="bd" id="bd">
			<table cellspacing="0" cellpadding="0" class="tbl" id="cart_tbl">
				<tbody>
				<c:if test="${empty cartList}">还没有商品 赶紧去选购吧！</c:if>
				<c:set var="totleCount" value="0"></c:set>
				<c:set var="totleMoney" value="0"></c:set>
				<c:forEach var="item" items="${cartList}">
					<c:set var="totleCount" value="${totleCount+item.quantity}"></c:set>
					<c:set var="totleMoney" value="${totleMoney+item.price*item.quantity}"></c:set>
					<input type="hidden" value="${item.shoppingCartId}"/>
					<tr class="odd" id="${item.id}_${item.commodityType}" data-price="${item.price/100}">
						<%--<td>
							<input name="shoppingCartChek" type="checkbox" checked="checked" value="${item.shoppingCartId}"/>
						</td>
						--%><td class="prd_pic_w">
                            <span>
                                <a href="${item.path}"><img src="${item.image}"/></a>
                            </span>
						</td>
						<td class="prd_name_w">
							<span> <a href="${item.path}">${item.name}</a></span>
						</td>
						<td class="prd_normal_85">
							<span>${item.specifications}</span>
						</td>
						<td class="prd_normal_100">
							<b>￥</b><span class="prd_price">${item.price/100}</span>
						</td>
						<td class="prd_normal_100">
							<div class="num">
							    <a href="#" class="ico minus_num" id="minus_num">-</a>
							    <input class="prd_nums" id="prd_nums" data-cur-nums="${item.quantity}" value="${item.quantity}" type="text" class="ipt" maxlength="3" autocomplete="off"/>
							    <a href="#" class="ico add_num" id="add_num">+</a>
						    </div>
						</td>
						<td class="prd_normal_100"><b>￥</b><span class="total_price">${item.price*item.quantity/100 }</span>元 </td>
						<td class="prd_del_w"><span><b class="del_btn" onclick="delShoppingCart('${item.id}','${item.commodityType}',delRow)">删除</b></span></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="shopping_cart_bd_total">
				商品共计<em><span id="totleCount">${totleCount}</span></em><%--件&nbsp;&nbsp;
				积分：<em>6760</em>&nbsp;&nbsp;--%>
				商品总金额(不含运费)：<em>￥<span id="totleMoney">${totleMoney/100}</span>元</em>
			</div>
		</div>
		<div class="shopping_cart_ft"></div>
	</div>
	<div class="shopping_cart_bottom">
		<a class="link_btn ico_home" href="#">继续购物</a>
		<a class="link_btn ico_clear" href="#" onclick="empty()">清空购物车</a>
		<a href="#" class="account_btn" onclick="goBuy()">去结算</a>
	</div>
</div>
<%--<div class="wrap_footer">
	<c:if test="${null !=siteId}">
		<c:import url="http://tcomp.elian.cc/${siteId}/include/foot.shtml" charEncoding="utf-8"/>
	</c:if>
</div>
--%>
<c:import url="/page/front/searchFooter.jsp" charEncoding="utf-8"/>
<script src="http://script.elian.cc/comp/v2/base.js"></script>
<script src="http://script.elian.cc/comp/v2/cart-setNums.js"></script>
<script type="text/javascript">
	function closeDialog(){
		var sId=$("#siteId").val();
		$.ajax({
			url: res_cms+'/mall/shoppingCart!ajaxLogin.action',
			type: "GET",
			async:false,
			dataType: 'jsonp',
			data: {},
			success: function(data) {
				if(data!=null || data!=undefined){
					if(data.status=='success'){
						var url=res_cms+'/mall/mallOrder!list.action?ids='+data.ids+'&siteId='+sId;
						window.location.href=url;
					}
				}
			}
		});
	}
</script>
</body>
</html>