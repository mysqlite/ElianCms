<!doctype html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>医联网注册页</title>
		<meta name="Keywords" content="医联网注册页" />
		<meta name="Description" content="医联网注册页" />
		<link rel="stylesheet" href="http://style.elian.cc/public/gobal-login/login.css" type="text/css" media="screen" />
		<link rel="shortcut icon" type="image/x-icon" href="http://www.医联网注册页.com/favicon.ico" />
	</head>
	<body>
		<div class="guahao_login_area diglog_reg">
			<div class="ui_hd">
				<h3 class="tit">
					请填写注册信息
				</h3>
			</div>
			<form id="mallRegisterForm" action="${path}/mall/mallRegister!register.action" method="post">
				<div class="mod_bd">
					<div class="i">
						<span class="tit">账号：</span>
						<input class="ipt" id="account" name="account" type="text" />
						<script language="javascript">document.getElementById('account').focus();</script>
					</div>
					<div class="i">
						<span class="tit">密码：</span>
						<input class="ipt" id="password" name="password" type="password" />
					</div>
					<div class="i">
						<span class="tit">确认密码：</span>
						<input class="ipt" id="confirmPassword" name="confirmPassword" type="password" />
					</div>
					<div class="i">
						<span class="tit">用户名称：</span>
						<input class="ipt" id="userName" name="userName" type="text" />
					</div>
					<div class="i tips">
						<span id="fieldErrors" class="red"></span>
					</div>
					<div class="wrap_login_btn">
						<input type="submit" class="submit_btn" id="submitForm" value="提交注册" />
					</div>
					<div class="i reg_bar">
						<a class="link" href="http://cms.elian.cc/front/regHosp!regHospital.action" target="_blank">医院注册</a>
						| <a class="link" href="http://cms.elian.cc/front/regCompany!reg.action" target="_blank">企业注册</a>
						| <a class="link" href="http://cms.elian.cc/front/regSubstation!regSubstation.action" target="_blank">分站注册</a>
						| <a class="link" href="http://www.191580.com/reg/registered.jsp" target="_blank">挂号注册</a>
					</div>
				</div>
			</form>
		</div>
	</body>
	<script language="javascript" type="text/javascript" src="${path}/js/jquery.js"></script>
	<script src="${path}/plugin/jqueryUI/js/jquery-ui-edit.js"></script>
	<script src="${path}/plugin/jqueryUI/js/xd.js"></script>
	<script type='text/javascript' >
		var send = function(userName) {
			var json = {
				'type':"register",
				'userName' : userName
			};
			XD.sendMessage(parent, json);	//区别在这里，第一个参数
		};
	</script>
	<script type="text/javascript">
		function checkRegister() {
			var userName = $('#userName').attr("value");
			var account = $('#account').attr("value");
			var password = $('#password').attr("value");
			var confirmPassword = $('#confirmPassword').attr("value");
			var isVerifyOk = true;
			if (account == null || account.trim() == '') {
				$("#fieldErrors").html('账号不能为空！');
				isVerifyOk =  false;
			}
			else if (password == null || password.trim() == '') {
				$("#fieldErrors").html('密码不能为空！');
				isVerifyOk =  false;
			}
			else if (confirmPassword == null || confirmPassword.trim() == '') {
				$("#fieldErrors").html('密码不能为空！');
				isVerifyOk =  false;
			}
			else if (password.trim() != confirmPassword.trim()) {
				$("#fieldErrors").html('两次输入的密码不一致！');
				isVerifyOk =  false;
			}
			else if (userName == null || userName.trim() == '') {
				$("#fieldErrors").html('用户名称不能为空！');
				isVerifyOk =  false;
			}
			return isVerifyOk;
		}
		
		$(document).ready(function() {
			$('#mallRegisterForm').bind('submit', function(){
				if(checkRegister()){
					ajaxSubmit(this, function(data){
						if(data.STATUS == 1){
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