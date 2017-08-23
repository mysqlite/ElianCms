<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="zh-CN">
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	//设置页面不缓存
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	response.setHeader("P3P","CP=CAO PSA OUR");
%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>医联网登录页</title>
		<meta name="Keywords" content="医联网登录页" />
		<meta name="Description" content="医联网登录页" />
		<link rel="stylesheet" href="http://style.elian.cc/public/gobal-login/login.css" type="text/css">		<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico" />
		<script language="javascript" type="text/javascript" src="${path}/js/jquery.js"></script>
		<script language="javascript" type="text/javascript" src="${path}/js/manage/login.js"></script>
	</head>
	<body onkeydown="keyLogin()">
		<div class="guahao_login_area diglog_login">
			<div class="ui_hd">
				<h3 class="tit">
					请登录
				</h3>
			</div>
			<form id="mallLoginForm" action="${path}/mall/mallLogin!loginJson.action" method="post">
				<div class="mod_bd">
					<div class="i">
						<span class="tit">用户名：</span>
						<input class="ipt" id="account" name="user.account" type="text" />
						<script language="javascript">document.getElementById('account').focus();</script>
					</div>
					<div class="i">
						<span class="tit">密码：</span>
						<input class="ipt" id="password" name="user.password" type="password" />
					</div>
					<div class="i login_option">
						<label class="lbl" for="remName">
							<input id="remName" class="ipc" type="checkbox" checked="checked" />
							记住我一周
						</label>
					</div>
					<div class="i tips">
						<span id="fieldErrors" class="red"></span>
					</div>
					<div class="wrap_login_btn">
						<input type="submit" class="submit_btn"  id="submitForm" value="提交注册" />
					</div>
				</div>
			</form>
		</div>
	</body>
	<script src="${path}/plugin/jqueryUI/js/jquery-ui-edit.js"></script>
	<script src="${path}/plugin/jqueryUI/js/xd.js"></script>
	<script type='text/javascript' >
		function checkLogin() {
			var account = $('#account').attr("value");
			var password = $('#password').attr("value");
			if (account == null || account.trim() == '') {
				$("#fieldErrors").html('账号不能为空！');
				return false;
			}
			if (password == null || password.trim() == '') {
				$("#fieldErrors").html('密码不能为空！');
				return false;
			}
			var remName = document.getElementById("remName");
			if (remName.checked) {
				addCookie("account", account.value + "$" + password.value, 24 * 7);
			}
			return true;
		}
		
		var send = function(userName) {
			var json = {
				'type':"login",
				'userName' : userName
			};
			XD.sendMessage(parent, json);	//区别在这里，第一个参数
		};
		
		$(document).ready(function() {
			$('#mallLoginForm').bind('submit', function(){
				if(checkLogin()){
					ajaxSubmit(this, function(data){
						if(data.STATUS == 1){
							addCookie("_user", data.MSG, 0);
							send(data.MSG);
						}
						else if(data.STATUS == 0){
							$("#fieldErrors").html(data.MSG);
						}
			        });
				}
				return false;
			});
		});
	</script>
</html>