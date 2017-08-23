<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
	<head>
		<title>用户后台首页</title>
		<meta content="text/html; charset=utf-8" http-equiv=content-type>
		<link rel="stylesheet" href="${path}/css/manage/base.css" type="text/css" />
		<link rel="stylesheet" href="${path}/css/manage/admin.css" type="text/css" />
		<script src="${path}/js/jquery.js"></script>
		<script language="javascript" type="text/javascript" src="${path}/js/manage/editCommon.js"></script>
	</head>
	<body>
		<div class="main-top-hd clearfix">
			<h3 class="cur">
				用户后台首页
			</h3>
		</div>
		<div class="main-action-bar outline-bar" id="dateses"></div>
		<div class=body>
			<div class="outline-wrap">
				<h3 class="main-tit-bar">
					<span class="ico ico1">用户状态</span>
				</h3>
				<ul class="setting-list">
					<li>
						<div class="wrap-item">
							<label class="lbl-ipt-tit">
								站点：
							</label>
							<span class="txt">${_site.siteName}</span>
						</div>
						<div class="wrap-item">
							<label class="lbl-ipt-tit">
								用户类型：
							</label>
							<span class="txt"> ${_user.userType.typeName}</span>
						</div>
						<div class="wrap-item">
							<label class="lbl-ipt-tit">
								角色名称：
							</label>
							<c:forEach var="role" items="${_roleList}" varStatus="e">
								<span class="txt">${role.roleName}</span>
							</c:forEach>
						</div>
						<div class="wrap-item">
							<label class="lbl-ipt-tit">
								登陆账号：
							</label>
							<span class="txt"> ${_user.account}<c:if
									test="${!empty _user.realName}">[<span
										style="color: green;">${_user.realName}</span>]</c:if> </span>
						</div>
						<div class="wrap-item">
							<label class="lbl-ipt-tit">
								会员等级：
							</label>
							<span class="txt">${_site.grade.gradeName}</span>
						</div>
					</li>
				</ul>
				<h3 class="main-tit-bar">
                    <span class="ico ico3">登录统计</span>
                </h3>
                <ul class="setting-list">
                    <li>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit"> 上次登录时间：</label>
                            <span class="txt"><fmt:formatDate value="${_user.loginTime}" pattern="yyyy年MM月dd日 HH时mm分ss秒" /></span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">上次登录IP：</label>
                            <span class="txt"> ${_user.loginIp}</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">注册时间：</label>
                            <span class="txt"><fmt:formatDate value="${_user.registerTime}" pattern="yyyy年MM月dd日 HH时mm分ss秒" /></span>
                        </div>
                    </li>
                    <li>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">本次登录时间：</label>
                            <span class="txt"><fmt:formatDate value="${_user.lastLoginTime}" pattern="yyyy年MM月dd日 HH时mm分ss秒" /></span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">本次登录IP：</label>
                            <span class="txt"> ${_user.lastLoginIp}</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">注册IP：</label>
                            <span class="txt">${_user.registerIp}</span>
                        </div>
                   </li>
                   <li>
                       <div class="wrap-item">
                          <label class="lbl-ipt-tit">登录次数：</label>
                          <span class="txt"> ${_user.loginCount}&nbsp;&nbsp;次</span>
                       </div>
                   </li>
                </ul>
                <h3 class="main-tit-bar">
                    <span class="ico ico3">流量统计</span>
                </h3>
                <ul class="setting-list">
                    <li>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit"><span class="txt">今日&nbsp;&nbsp;&nbsp;&nbsp;</span></label>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">浏览量(PV)：</label><span id="pv" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">访客数(UV)：</label><span id="uv" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">独立IP访客数:</label><span id="ip" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">百度搜索：</label><span id="baidu" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">google搜索：</label><span id="google" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">360搜索:</label><span id="so360" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">腾讯soso:</label><span id="soso" class="txt">...</span>
                        </div>
                    </li>
                </ul>
                <ul class="setting-list">
                    <li>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit"><span class="txt">昨日&nbsp;&nbsp;&nbsp;&nbsp;</span></label>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">浏览量(PV)：</label><span id="pv1" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">访客数(UV)：</label><span id="uv1" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">独立IP访客数:</label><span id="ip1" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">百度搜索：</label><span id="baidu1" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">google搜索：</label><span id="google1" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">360搜索:</label><span id="so3601" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">腾讯soso:</label><span id="soso1" class="txt">...</span>
                        </div>
                    </li>
                </ul>
                <ul class="setting-list">
                    <li>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit"><span class="txt">共计&nbsp;&nbsp;&nbsp;&nbsp;</span></label>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">浏览量(PV)：</label><span id="pv2" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">访客数(UV)：</label><span id="uv2" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">独立IP访客数:</label><span id="ip2" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">百度搜索：</label><span id="baidu2" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">google搜索：</label><span id="google2" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">360搜索:</label><span id="so3602" class="txt">...</span>
                        </div>
                        <div class="wrap-item">
                            <label class="lbl-ipt-tit">腾讯soso:</label><span id="soso2" class="txt">...</span>
                        </div>
                    </li>
                </ul>
                <script type="text/javascript">
                     var baseUrl='${path}';
					function todays(){
					    var urls=baseUrl+"/admin/siteFlows!today.action";
					    getJson(urls,function(data){
					        commHtmls("",data);
					    })
					}
					
					function yesterdays(){
					    var urls=baseUrl+"/admin/siteFlows!yesterday.action";
					    getJson(urls,function(data){
					        commHtmls("1",data);
					    })
					}
					
					function statisticals(){
					    var urls=baseUrl+"/admin/siteFlows!statistical.action";
					    getJson(urls,function(data){
					        commHtmls("2",data);
					    })
					}
					
					function commHtmls(id,data){
					    G("pv"+id).innerHTML=data.pv;
					    G("uv"+id).innerHTML=data.uv;
					    G("ip"+id).innerHTML=data.ip;
					    G("baidu"+id).innerHTML=data.baidu;
					    G("google"+id).innerHTML=data.google;
					    G("soso"+id).innerHTML=data.soso;
					    G("so360"+id).innerHTML=data.so360;
					}
					todays();
					yesterdays();
					statisticals();
					</script>
				<h3 class="main-tit-bar">
					<span class="ico ico2">常用快捷操作</span>
				</h3>
				<h3 class="main-tit-bar">
					<span>医联网动态新闻</span>
				</h3>
			</div>
		</div>
	</body>
	<script type="text/javascript">
function datese() {
	var dateTime = new Date();
	var hh = dateTime.getHours();
	var mm = dateTime.getMinutes();
	var ss = dateTime.getSeconds();
	var yy = dateTime.getFullYear();
	var MM = dateTime.getMonth() + 1;
	var dd = dateTime.getDate();
	var week = dateTime.getDay();
	var days = [ "日", "一 ", "二 ", "三 ", "四 ", "五 ", "六 " ];
	document.getElementById("dateses").innerHTML = yy + "年" + MM + "月" + dd
			+ "日 " + "星期" + days[week] + hh + "时" + mm + "分" + ss + "秒";
	setTimeout("datese();",1000);
}
datese();
</script>
</html>