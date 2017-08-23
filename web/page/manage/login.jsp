<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@page import="com.elian.cms.syst.util.ReadWriteXMLUtils"%>
<%@page import="com.elian.cms.syst.util.SysXmlUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="zh-CN">
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	//设置页面不缓存
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=9" />
		<title>医联网-CMS后台管理系统</title>
		<meta name="Keywords" content="CMS,CMS后台,管理系统,医院CMS,企业CMS,医联网,挂号管理系统" />
		<link rel="shortcut icon" type="image/x-icon"  href="<%=SysXmlUtils.getString("siteImgIcon")%>"/>
		<link rel="stylesheet" href="${path}/css/manage/login.css" type="text/css" media="screen" />
		<link href="<%=SysXmlUtils.getString("favicon")%>" type="image/x-icon" rel="shortcut icon">
		<script language="javascript" type="text/javascript" src="${path}/js/jquery.js"></script>
		<script language="javascript" type="text/javascript" src="${path}/js/manage/login.js"></script>
		<script type="text/javascript">
		  //alert(document.cookie);
			$(document).ready(function(){checkCookie();});
			function into() {
				if (top.location != self.location) {
					top.location = self.location;
				}
			}
			into();
		</script>
	</head>
	<body onkeydown="keyLogin()">
		<div class="header">
			<h1>
				<a href="<%=SysXmlUtils.getString("mainSitedomain")+SysXmlUtils.getString("mainDomain")%>"> <img alt="医联网" class="logo" src="<%=SysXmlUtils.getString("adminLoginlogoImg")%>"
						width="182" height="100" />医联网 </a>
			</h1>
		</div>
		<div class="section login_bg">
			<div class="con">
				<div class="wrap_loginArea">
					<div class="loginArea">
						<h3>
							CMS后台管理-请登录后进行操作
						</h3>
						<form action="${path}/admin/login!login.action" method="post">
							<input type="hidden" name="loginNum" value="${loginNum}"/>
							<div class="i">
								<span class="tit">用户名：</span>
								<input class="ipt" id="account" name="user.account" type="text" />
								<script language="javascript">document.getElementById('account').focus();</script>
							</div>
							<div class="i">
								<span class="tit">密码：</span>
								<input class="ipt" id="password" name="user.password" type="password" />
							</div>
							<c:if test="${loginNum>=3}">
								<span class="tit">验证码:</span>
								<input id="captcha" name="captcha" class="ipt" style="width: 100px" />
								<img src="<%=request.getContextPath()%>/page/common/image.jsp"
									border="0" onclick="this.src='<%=request.getContextPath()%>/page/common/image.jsp?random='+ Math.random()" />
							</c:if>
							<span style="color:red;">
								<s:fielderror>
								    <s:param>user</s:param>
							    </s:fielderror>
								<s:fielderror>
								    <s:param>user.account</s:param>
							    </s:fielderror>
								<s:fielderror>
								    <s:param>user.password</s:param>
							    </s:fielderror>
								<s:fielderror>
								    <s:param>captcha</s:param>
							    </s:fielderror>
							</span>
							<div class="i login_option">
								<label class="lbl" for="remName">
									<input id="remName" class="ipc" type="checkbox" checked="checked"/>
									记住我一周
								</label>
								<label class="lbl" for="autoLogin">
									<input id="autoLogin" class="ipc" type="checkbox" />
									自动登录
								</label>
							</div>
							<div class="i login">
								<input class="imgBtn" type="submit" id="submit" 
									onclick="return checkLogin();" value="登录" />
								<a href="#" class="link_btn link_txt">忘记密码?</a>
							</div>
						</form>
						<div class="i last">
							<span class="link_txt"><b>还不是会员？免费注册</b>
							<a href="${path}/front/regHosp!regHospital.action">医院CMS</a>
							<a href="${path}/front/regCompany!reg.action">企业CMS</a>
							<a href="${path}/front/regSubstation!regSubstation.action">分站CMS</a>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="footer" id="bottom">
			<div class="bottom_nav">
				<%--<a href="#">关于我们</a> |
				<a href="#">网站地图</a> |
				<a href="#">友情链接</a> |
				<a href="#">营销中心</a> |
				<a href="#">法律声明</a> |
				<a href="#">隐私声明</a> |
				<a href="#">联系我们</a>
			--%></div>
			<%--<p>
				粤ICP备10054714号 互联网药品信息服务资格证书 证件编号：（粤）-非经营性-2010-017
			</p>
			--%><p class="copyright">
				Copyright © 2009-2012
				<a href="<%=SysXmlUtils.getString("mainSitedomain")+SysXmlUtils.getString("mainDomain")%>">医联网</a> 版权所有&nbsp;&nbsp;
				<%--<c:if test="${empty mainSite}"><a href="${path}/admin/regSubstation!regSubstation.action?mainSubs=true">主站注册</a></c:if>
			--%></p>
			<%--<p>
				本站信息仅供参考不能作为诊断及医疗的依据，如有转载或引用文章涉及版权问题请速与我们联系。
			</p>
		--%></div>
<script type="text/javascript">
core={};
/**获取对象*/
core.G=function(id){return document.getElementById(id);};
core.XMLHttp = {//建立XMLHttpRequest对象池
    xmlHttpCache : [],
    createXmlHttp : function(){
        var xmlhttp = null;
        if (window.XMLHttpRequest){xmlhttp = new XMLHttpRequest();} 
        else if (window.ActiveXObject){xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");}
        return xmlhttp;
    },
    getXmlHttp : function(){
        var xmlhttp = null;
        if (window.XMLHttpRequest) {xmlhttp = new XMLHttpRequest();} 
        else if (window.ActiveXObject){xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");}
        return xmlhttp;
    }
}
core.ajax=function(url,f,paramData){
    var xmlhttp=core.XMLHttp.getXmlHttp();
    if(xmlhttp!=null){
        xmlhttp.onreadystatechange=function(){
            if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
                f(xmlhttp.responseText);
            }
        }
        //防止缓存
        if (url.lastIndexOf("?") != -1)
            url += "&rt=" + (new Date()).getTime();
        else
            url += "?rt=" + (new Date()).getTime();
        xmlhttp.open("GET", url, true);
        xmlhttp.send();
    }
}
</script>

	</body>
</html>