<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<div class="loginArea">
        <div class="ui_hd">
            <h3 class="tit">用户登录</h3>
        </div>
        <c:if test="${!empty _user}">
        <div class="bd logined_bd clearfix">
            <div class="i">
                <span class="tit">您好：</span><span class="txt">${_user.account}[${_user.realName}]</span>
            </div>
            <div class="i">
                <span class="tit">您的等级：</span><span class="txt">普通会员</span>
            </div>
            <div class="i">
                <a href="${path}/reg/regUserManager!myData.action" class="link">进入用户中心</a>&nbsp;&nbsp;<a href="${path}/reg/login!loginOut.action" class="link">[退出]</a>
            </div>
            <%--<div class="i">
                <span class="tit">您的余额：</span><span class="txt">0</span>&nbsp;&nbsp;
                <a href="#" class="link">[我要充值]</a>
            </div>
            <div class="i">
                <span class="tit">成功挂号次数：</span><span class="txt">20</span>
            </div>
            --%><div class="i">
                <a class="wrap_weibo" href="http://weibo.com/elian0757" target="_bank"><img src="http://style.elian.cc/main/reg/img/weibo.jpg" width="195" height="26"/>官方微博加关注</a>
            </div>
        </div>
        </c:if>
        <c:if test="${empty _user}">
        <script language="javascript" type="text/javascript" src="${path}/js/manage/login.js"></script>
        <script type="text/javascript">$(document).ready(function(){checkCookie();});</script>
        <form action="${path}/reg/login!login.action" method="post" name="loginUser" onsubmit="return checkLogin();">
	        <div class="bd clearfix">
	            <div class="role-hd">
	             <c:if test="${!empty errors['loginmsg']}">
                    <b class="reg_tips reg_error_tips" id="captcha_m">
                       ${errors['loginmsg'][0]}
                    </b>
                 </c:if>
                 <c:if test="${empty errors['loginmsg']}">
                    <b class="reg_tips" id="captcha_m">
                       请输入用户名及密码进行登录
                    </b>
                 </c:if>
	            </div>
	            <div class="i">
	                <label class="tit">用&nbsp;户&nbsp;名：</label>
	                <input class="ipt" type="text" id="account" name="user.account" id="account" value="" maxlength="16" tabindex="1"/>
	                <script language="javascript">document.getElementById('account').focus();</script>
	                <label class="lbl" for="remName">
	                    <input id="remName" class="ipc" type="checkbox" checked="checked"/>
	                     记住我一周
                    </label>
	            </div>
	            <div class="i">
	                <label class="tit">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
	                <input class="ipt" type="password" name="user.password" id="password" value="" maxlength="20" tabindex="2"/>
	                <a href="#" class="find-pw-link" tabindex="4">找回密码?</a>
	            </div>
	            <div class="i">
	                <label class="tit">验&nbsp;证&nbsp;码：${path}</label>
                    <input class="ipt vcode" type="text" name="captcha" id="captcha" value="" maxlength="4" tabindex="3" onkeyup="keyLogin()" />
                    <a href="javascript:void(0);" onclick="document.getElementById('valCode').src='${path}/page/common/image.jsp?random='+ Math.random()">
                    <img src="${path}/page/common/image.jsp" width="54" height="22" id="valCode"/></a>
	            </div>
	            <div class="i login-bar">
	                <input class="ips ui_login" type="submit" id="submit" value="登录"/>
	                <input class="ips ui_reset_btn" type="reset" value="重填"/>
	                <a class="link_btn" href="${path}/reg/registered.jsp">注册</a>
	            </div>
	         </div>
         </form>
        </c:if>
    </div>