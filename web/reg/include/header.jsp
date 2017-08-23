<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="wrap_topbar">
    <div class="topbar">
        <div class="welcom">
           ${_user.realName}&nbsp;&nbsp;您好！欢迎访问医联网191580智能挂号平台
        </div>
        <div class="top_link">
            <a class="tel-ico" href="#">手机版</a> | <a href="http://software.elian.cc/contactus.asp?mid=8" target="_bank">咨询电话</a> |
            <a onclick="AddFavorite(window.location,document.title)" href="javascript:void(0);">加入收藏</a> | 
            <c:if test="${empty _user}">
               <a href="${path}/reg/login.jsp">登录系统</a> 
            </c:if>
            <c:if test="${!empty _user}">
               <a href="${path}/reg/regUserManager!myData.action">用户中心</a> 
            </c:if>
           |
            <a href="http://www.elian.cc" target="_blank">医联网</a>
        </div>
    </div>
</div>
<div class="header">
    <h1><a href="${path}/reg/regIndex.action">
        <img src="http://images.elian.cc/design/main/reg/img/logo.gif" width="125" height="60" alt="91580就医我帮您智能挂号平台"/>
        91580 就医我帮您智能挂号平台</a>
    </h1>
    <%--<div class="city-switch">
        <a class="ui_link_btn" href="#"><em class="name">佛山</em>切换城市</a>
        <span class="txt">医联网已开通城市500+</span>
    </div>
    --%><div class="search_bar">
        <div class="tab1_hd clearfix" id="tab1_hd">
            <h3 class="cur" onmouseover="overTab(1,1,'h3','div')" onmouseout="outTab()">找医生</h3>
            <h3 onmouseover="overTab(1,2,'h3','div')" onmouseout="outTab()">找医院</h3>
            <%--<h3 onmouseover="overTab(1,3,'h3','div')" onmouseout="outTab()">查公交</h3>
        --%></div>
        <div class="tab1_bd" id="tab1_bd">
            <div class="tab_con" style="display: block;">
            	<form id="doctorSearchForm" action="${path}/reg/regDoctor!search.action" method="post">
	                <span class="ui_search_bar">
	                    <input name="doctorName" class="ipt" type="text" value=""/>
	                    <input class="ips" type="submit" value="搜索"/>
	                </span>
	            </form>
            </div>
            <div class="tab_con">
	   			<form id="hospitalSearchForm" action="${path}/reg/regHospitalSearch!search.action" method="post">
	                <span class="ui_search_bar">
	                    <input name="hospitalName" class="ipt" type="text" value=""/>
	                    <input class="ips" type="submit" value="搜索"/>
	                </span>
		        </form>
            </div><%--
            <div class="tab_con">
                <span class="ui_search_bar">
                    <input class="ipt" type="text" value=""/>
                    <input class="ips" type="submit" value="搜索"/>
                </span>
            </div>
        --%></div>
    </div>
</div>