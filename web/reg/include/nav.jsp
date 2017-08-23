<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="nav">
    <span class="tel400">全国电话：40001-91580</span>
    <ul class="list" id="list">
        <li><a class="cur" href="${path}/reg/regIndex.action">首页</a></li>
        <li><a href="http://www.915800.com/diagnose/default.htm?menuid=1" target="_bank">智能挂号</a></li>
        <li><a href="${path}/reg/regNotes!list.action">诊疗信息查询</a></li>
        <li><a href="${path}/reg/regDoctor!search.action">专家介绍</a></li>
        <li><a href="${path}/reg/regHospitalSearch!search.action">床位查询</a></li>
        <li><a href="javaScript:alert('数据未接入');">检查排号</a></li>
        <li><a href="http://61.49.18.120/nursesearch.aspx" target="_bank">查询中心</a></li>
    </ul>
    <div class="app-banner"><a href="http://style.elian.cc/gh.apk">手机版软件下载</a></div>
</div>
<!--  -->
<script type="text/javascript">
$(document).ready(function() {
    var $menuItem = $("#list li a");
    $menuItem.live("click",function(){
        var $this=$(this);
        $menuItem = $("#list li a");
        $menuItem.removeClass("cur");
        $this.addClass("cur");
    });
});
</script>