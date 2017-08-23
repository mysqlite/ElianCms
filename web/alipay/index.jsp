<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd"> 
<%
   String path = request.getContextPath();
   String basePath = request.getScheme() + "://" + request.getLocalAddr() + ":"
                              + request.getServerPort() + path + "/";
%>
<html>
<head>
<title>模拟商城</title> 
<meta http-equiv="Content-Type" content="application/vnd.wap.xhtml+xml;charset=utf-8" /> 
<base href="<%=basePath%>">
</head>
<link href="<%=basePath%>css/ap-3g.css" rel=stylesheet />
<body>
	<div class="ap-top">
		<div class="ap-b  ap-hr ap-cb-gray"> 
		   模拟商城商品
		</div>
		<div class="ap-b ap-left ap-noline"> 
		<img width="100px" src="<%=basePath%>images/xionghua.jpg"></img><br /> 
		商品<br /> 
		<span class="ap-c-green"></span><br /> 
		<strong><span class="ap-c-red">￥0.01元[<a href="${path}/alipay/alipay!alipay.action?orderId=38&orderType=order">购买</a>]</span></strong><br /> 
		</div> 
		<div class="b-hr" />
		
		<div  class="ap-b ap-left ap-noline"> 
		<img width="100px" src="<%=basePath%>images/qinglvshuiyi.jpg"></img><br/>挂号<br/> 
		<span class="ap-c-green"></span><br />
		<strong><span class="ap-c-red">￥0.01元[<a href="http://alipay.elian.cc:8081/${path}/alipay/alipay!alipay.action?orderId=92&orderType=userregister">购买</a>]</span></strong><br /> 
		</div> 
	</div>
</body>
</html>