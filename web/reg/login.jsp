<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.elian.cms.syst.util.SysXmlUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    //设置页面不缓存
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<jsp:include page="include/sub_header.jsp"></jsp:include>
<jsp:include page="include/nav.jsp"></jsp:include>
<script language="javascript" type="text/javascript" src="${path}/js/manage/login.js"></script>
<form name="reg" id="logins" action="${path}/reg/logins!login.action" method="post" name="loginUser" onkeyup="keyLogin()" onsubmit="return checkLogin();">
<script type="text/javascript">$(document).ready(function(){checkCookie();});</script>
<div class="section">
    <div class="guahao_login_area">
        <div class="wrap_link">
            <div class="i reg_link"><span class="ico1">还没有帐号？马上</span><a href="${path}/reg/registered.jsp">免费注册帐号</a></div>
            <div class="i"><span class="ico2">返回</span>
                <a href="<%=SysXmlUtils.getString("mainSitedomain")+SysXmlUtils.getString("mainDomain")%>">医联网首页</a>！</div>
            <div class="i"><span class="ico3">不想挂号了？</span><a href="${!empty _forward_page?_forward_page:'javascript:void(0);'}">继续浏览</a>！</div>
            <div class="i"><span class="ico4">忘记密码了？小事儿，马上</span><a href="#">找回密码</a></div>
        </div>
        <div class="login_bar">
	        <div class="i">
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
                <span class="tit">登录账户：</span>
                <input class="ipt" type="text" name="user.account" id="account"/>
            </div>
            <script language="javascript">document.getElementById('account').focus();</script>
            <div class="i">
                <span class="tit">登录密码：</span>
                <input class="ipt" type="password" name="user.password" id="password"/>
            </div>
            <div class="i veti_code">
                <span class="tit">验证码：</span>
                <input class="ipt vcode" type="text" name="captcha" id="captcha" value="" maxlength="4" tabindex="3"/>
                <a href="javascript:void(0);" onclick="document.getElementById('valCode').src='${path}/page/common/image.jsp?random='+ Math.random()">
                <img src="${path}/page/common/image.jsp" width="54" height="22" id="valCode"/>
                </a>
                <div class="wrap_login_btn">
                    <input type="submit" id="submit" class="submit_btn" value="提交注册"/>
                </div>
            </div>
            <div class="i login_option">
            <label class="lbl" for="remName">
                        <input id="remName" class="ipc" type="checkbox" checked="checked"/>
                         记住我一周
             </label>
             </div>
        </div>
    </div>
</div>
</form>
<jsp:include page="include/footer.jsp"></jsp:include>
<script src="http://script.elian.cc/main/reg/script/base.js"></script>