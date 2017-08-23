<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!doctype html>
<html lang="zh-CN">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=9"/>
        <title>财付通支付返回页</title>
    </head>
    <body class="list">
        <div align="center">
            <c:if test="${ten.tenpaySign}">
	            <h5>商户订单号：${ten.outTradeNo}</h5>    
	            <h5>支付金额:${(ten.totalFee/100)}元</h5>    
	            <h5>使用折扣券:${ten.discount}</h5>    
	            <h5>支付结果:${ten.tradeState==0?'支付成功':'支付失败'}</h5>    
	            <h5>交易模式:${ten.tradeMode==1?'即时到账':'中介担保'}</h5>    
            </c:if>
            <c:if test="${!ten.tenpaySign}">
                <h5>签名认证失败</h5>
            </c:if>
        </div>
    </body>
 </html>   
    