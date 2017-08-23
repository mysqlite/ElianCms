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
<link rel="stylesheet" href="${path}/css/manage/selectMenu.css" type="text/css">
<link rel="stylesheet" href="${path}/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<div class="gutter"></div>
		<div class="section">
			<div class="shopping_cart_breadcrumb">
				<h2 class="ico ico_cart_submit">
					提交订单信息
				</h2>
				<div class="step step2">
					<span class="txt1">挑选商品加入购物车</span>
					<span class="txt2">填写信息,提交订单</span>
					<span class="txt3">收货确认,完成订单</span>
				</div>
			</div>
			<div class="order_form">
				<form id="mallOrderConfirmForm" name="mallOrderConfirmForm" action="${path}/mall/mallOrderConfirm!save.action" method="post">
					<input type="hidden" name="ids" value="${ids}">
					<input type="hidden" name="siteId" value="${siteId}">
					<table cellspacing="0" cellpadding="0" class="tbl">
						<tbody>
							<tr>
								<td class="td1">
									收货信息
								</td>
								<td>
									<div class="i">
										<ul class="address_list">
											<c:forEach var="addr" items="${addressList}" varStatus="e">
												<li>
													<label class="lbl" for="add${e.index}">
														<input type="radio" class="ipr" id="add${e.index}" name="addressId" value="${addressId}" <c:if test="${addr.id == addressId}"> checked="checked" </c:if>/>
														姓名：${addr.consignee}
														<span>地址：${addr.streetAddress}</span>手机号码：${addr.consigneeMobile}
													</label>
												</li>
											</c:forEach>
										</ul>
										<div class="add_address">
											<div class="hd">
												<label class="lbl" for="add-id">
													<input type="radio" class="ipr" id="add-id" name="addressId" value="" <c:if test="${addressId == null}"> checked="checked" </c:if>/>
													填写收货地址
												</label>
											</div>
											<div class="bd">
												<ul class="add_address_list">
													<li>
														<span class="tit">收货人姓名</span>
														<input id="consignee" type="text" class="ipt" name="address.consignee" value="" />
														<b class="must">*</b>
													</li>
													<li>
														<span class="tit">地区</span>
														<div>
							                                <input id="areaId" class="ipt" name="areaName" type="text" readonly="true" value="中国" 
										                        onfocus="showMenuArea('treeContent','areaId','areaValueId','${path}');"
										                        onkeydown="BackSpace('中国','areaId','areaValueId');" />
						                          			<input id="areaValueId" type="hidden" name="address.area.areaCode" value=""/>
															<b class="must">*</b>
														</div>
													</li>
													<li>
														<span class="tit">街道地址</span>
														<input id="streetAddress" type="text" class="ipt" name="address.streetAddress" />
														<b class="must">*</b>
													</li>
													<li>
														<span class="tit">电话</span>
														<input id="consigneePhone" type="text" class="ipt" name="address.consigneePhone"/>
													</li>
													<li>
														<span class="tit">手机</span>
														<input id="consigneeMobile" type="text" class="ipt" name="address.consigneeMobile"/>
														<b class="must">*</b>
														<span>asdfasdf</span>
													</li>
													<li>
														<span class="tit">设置</span>
														<label class="lbl" for="">
															<input type="checkbox" class="ipc" <c:if test="${save}"> checked="checked" </c:if>/>保存收货地址
														</label>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="td1">
									配送方式
								</td>
								<td class="mod_td">
									<div class="i ">
										<div class="wrap_bg">
											<span class="tit">
												<label class="lbl" for="ipr_kuaidi">
													<input type="radio" name="cart_form" class="ipr" id="ipr_kuaidi" checked="checked"/>
													普通快递
												</label>
											</span>
											<span class="txt price"> +￥10.00元 </span>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="td1">
									支付方式
								</td>
								<td class="mod_td">
									<div class="i">
										<c:forEach var="item" items="${deliveryTypeList}" varStatus="e">
											<div class="wrap_bg">
												<span class="tit">
													<label class="lbl" for="xianxia">
														<input type="radio" name="deliveryType" class="ipr" value="${item.key}" <c:if test="${item.key == deliveryType}"> checked="checked" </c:if>/>
														${item.value}
													</label>
												</span>
												<span class="txt"></span>
											</div>
										</c:forEach>
									</div>
								</td>
							</tr>
							<tr>
								<td class="td1">
									附言
								</td>
								<td>
									<div class="i ">
										<input type="text" class="ipt order_comment" name="description" value="${description}"/>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<div id="treeDiv"><ul id="treeContent" class="ztree" style="width:200px;"></ul></div>
				</form>
			</div>
			<div class="shopping_cart_bd td_cart_confrim">
				<div class="ui_hd">
					<span class="prd_pic_w">商品图片</span>
					<span class="prd_name_w">商品名称</span>
					<span class="prd_guige_w">商品规格</span>
					<span class="prd_price_w">销售价格</span>
					<span class="prd_num_w">购买数量</span>
					<span class="prd_num_w last">小计</span>
				</div>
				<div class="bd" id="bd">
					<table cellspacing="0" cellpadding="0" class="tbl">
						<tbody id="divOrderItem">
							<tr class="odd">
							</tr>
						</tbody>
					</table>
					<div class="shopping_cart_bd_total">
						商品共计<em id="totalQuantity"></em>件&nbsp;&nbsp;商品总金额(不含运费)：￥<em id="totalPrice"></em>元
					</div>
				</div>
				<div class="shopping_cart_ft"></div>
			</div>
			<div class="shopping_cart_bottom">
				<a class="link_btn ico_cart" href="#">返回购物车</a>
				<a id="submitButton" class="account_btn">去结算</a>
			</div>
		</div>
		<script type="text/javascript" src="${path}/js/jquery.js"></script>
		<script src="${path}/js/mall/public_cart.js"></script>
		<script src="${path}/js/mall/base.js"></script>
		<!--ztree实现多选单选-->
		<script type="text/javascript" src="${path}/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="${path}/js/areaTree.js" ></script>
		<script language="javascript" type="text/jscript">
			$(document).ready(function() {
				getOrderItem();
				
				verifyFormSubmit();
			});
			
			function isMobile(value){
				if (/^13\d{9}$/g.test(value) || (/^15[0-35-9]\d{8}$/g.test(value))
						|| (/^18[05-9]\d{8}$/g.test(value))){
					return true;
				}
				else{
					return false;
				}
			}
			
			function verifyFormSubmit(){
				$('#submitButton').bind('click', function(){
					var $newAddressChecked = $("#add-id").attr("checked");
					var isVerifyOk = true;
					if($newAddressChecked=="checked"){
						var consignee = $("#consignee").attr("value");
						var area = $("#areaValueId").attr("value");
						var streetAddress = $("#streetAddress").attr("value");
						var consigneePhone = $("#consigneePhone").attr("value");
						var consigneeMobile = $("#consigneeMobile").attr("value");
						if(consignee==null || consignee.trim() ==""){
							isVerifyOk=false;
							alert("请填写收货人姓名!");
						}
						else if(area==null || area.trim() ==""){
							isVerifyOk=false;
							alert("请选择区域!");
						}
						else if(streetAddress==null || streetAddress.trim() ==""){
							isVerifyOk=false;
							alert("请填写街道地址!");
						}
						else if(consigneeMobile==null || consigneeMobile.trim() ==""){
							isVerifyOk=false;
							alert("请填写收货人手机号码!");
						}
						else if(!isMobile(consigneeMobile)){
							isVerifyOk=false;
							alert("请填写正确的手机号码!");
						}
						else if(consigneePhone !=null && !/^[0-9]*$/.test(consigneePhone)){
							isVerifyOk=false;
							alert("请填写正确的收货人电话号码!");
						}
					}
					if(isVerifyOk){
						$("#mallOrderConfirmForm").submit();
					}
				});
			}
			
			//获取医生排班
			function getOrderItem() {
				$.ajax( {
					url : "${path}/mall/mallOrder!listJson.action?ids=${ids}",
					type : "POST",
					dataType : 'json',
					data : null,
					success : function(data) {
						showOrderItem(data.list, data.totalQuantity, data.totalPrice);
					}
				});
			}
			
			function showOrderItem(list, totalQuantity, totalPrice) {
				$("#totalQuantity").html(totalQuantity);
				$("#totalPrice").html(totalPrice/100);
				var html = "";//拼接表格html
				$.each(list,function(index, item) {
					html+="<tr class=\"odd\">";
					html+="		<td class=\"prd_pic_w\">";
					html+="			<span>";
					html+="				<a href=\"#\"><img src=\""+item.image+"\" /></a> ";
					html+="			</span>";
					html+="		</td>";
					html+="		<td class=\"prd_name_w\">";
					html+="			<span>"+item.name+"</span>";
					html+="		</td>";
					html+="		<td class=\"prd_guige_w\">";
					html+="			<span>"+item.specification+"</span>";
					html+="		</td>";
					html+="		<td class=\"prd_price_w\">";
					html+="			<span>￥"+item.unitPrice/100+"</span>";
					html+="		</td>";
					html+="		<td class=\"prd_num_w\">";
					html+="			<span>"+item.quantity+"</span>";
					html+="		</td>";
					html+="		<td class=\"prd_num_w\">";
					html+="			<span>￥"+item.subTotalPrice/100+"元</span>";
					html+="		</td>";
					html+="</tr>";
				});
				$("#divOrderItem").html(html);
			}
		</script>
		<div class="wrap_footer">
			<c:if test="${null !=siteId}">
				<c:import url="http://comp.elian.cc/${siteId}/include/foot.shtml" charEncoding="utf-8"/>
			</c:if>
		</div>
	</body>
</html>