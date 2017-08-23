<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
        <title>正在返回上一次浏览的页面...</title>
        <SCRIPT LANGUAGE="JavaScript"> 
        <!-- 
        var time = 0; //时间,秒 
        var url = '${_forward_page}';
        // 若from不存在则调整到用户管理首页
        if(url == ""){
            time = 0;
            url='${path}'+"/reg/regIndex.action";
        }
        function Redirect(){ 
            window.location = url; 
        } 
        var i = 0; 
        function dis(){ 
            document.getElementById("sTime").innerHTML = (time - i) + "秒"; 
            i++; 
        } 
        timer=setTimeout('Redirect()',time * 1000); //跳转 
        //--> 
    </SCRIPT>
</head>
<body>
<c:if test="${!empty _forward_page}">
        <center>
            <font color="#4b4b4b" size="2">您可以前往以下地址：</font>
            <br>
            <font color="#4b4b4b" size="2">系统会在
            <font color="#ff0000"><span id="sTime">1秒</span></font>后，自动跳到下面的连接</font>
            <br>
            <font size="2"><a href="${_forward_page}"></a></font>
        </center>
<script type="text/javascript">
timer=setInterval('dis()', 1000);//显示时间 
</script>
</c:if>
</body>
</html>